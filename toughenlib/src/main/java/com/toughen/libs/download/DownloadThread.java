package com.toughen.libs.download;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.toughen.libs.download.database.DownloadDBEntity;
import com.toughen.libs.download.database.DownloadDBManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadThread extends Thread {

    private static final String TAG = "DownloadThread";
    private static final int TIME_OUT = 50000;
    private Handler handler;
    private DownloadDBEntity entity;

    public DownloadThread(DownloadDBEntity entity) {
        this.entity = entity;
    }

    @Override
    public void run() {
        try {
            if (entity.isDownloadFinished()) {
                Log.e(TAG, "下载已经完成");
                DownloadDBManager.getInstance().delete(entity);
                return;
            }
            Log.d(TAG, "线程_" + entity.getThreadID() + "_正在下载【" + "开始位置 : " + entity.getStartLocation() + "，结束位置：" + entity.getEndLocation() + "】");
            String downloadFileName;
            URL url = new URL(entity.getDownloadPath());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //在头里面请求下载开始位置和结束位置
            conn.setRequestProperty("Range", "bytes=" + (entity.getStartLocation() + entity.getDownloadLength()) + "-" + entity.getEndLocation());
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setConnectTimeout(TIME_OUT);
            //设置为长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            conn.setReadTimeout(20000);  //设置读取流的等待时间,必须设置该参数
            int lastIndex = entity.getDownloadPath().lastIndexOf("/");
            downloadFileName = entity.getDownloadPath().substring(lastIndex);
            if (conn.getResponseCode() == 302) {
                String newDownloadPath = conn.getHeaderField("Location");
                lastIndex = newDownloadPath.lastIndexOf("/");
                downloadFileName = newDownloadPath.substring(lastIndex);
                URL newUrl = new URL(newDownloadPath);
                conn = (HttpURLConnection) newUrl.openConnection();
                //在头里面请求下载开始位置和结束位置
                conn.setRequestProperty("Range", "bytes=" + (entity.getStartLocation() + entity.getDownloadLength()) + "-" + entity.getEndLocation());
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setConnectTimeout(TIME_OUT);
                //设置为长连接
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
                conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
                conn.setReadTimeout(20000);  //设置读取流的等待时间,必须设置该参数
            }
            InputStream is = conn.getInputStream();
            //创建可设置位置的文件
            RandomAccessFile file = new RandomAccessFile(new File(entity.getFileSavePath(), downloadFileName), "rwd");
            //设置每条线程写入文件的位置
            file.seek(entity.getStartLocation() + entity.getDownloadLength());
            byte[] buffer = new byte[1024];
            //当前子线程的下载位置
            long currentLocation = entity.getStartLocation() + entity.getDownloadLength();
            while (currentLocation < entity.getEndLocation() && !this.isInterrupted()) {
                int len = is.read(buffer, 0, 1024);
                if (len >= 0) {
                    file.write(buffer, 0, len);
                    currentLocation += len;
                    if (currentLocation >= entity.getEndLocation()) {
                        entity.setDownloadLength(entity.getEndLocation() - entity.getStartLocation());
                        DownloadDBManager.getInstance().delete(entity);
                    } else {
                        entity.setDownloadLength(entity.getDownloadLength() + len);
                        DownloadDBManager.getInstance().update(entity);
                    }
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = len;
                    handler.sendMessage(msg);
                }
            }
            DownloadDBManager.getInstance().delete(entity);
            file.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DownloadDBEntity getEntity() {
        return entity;
    }

    public void stopDownload() {
        this.interrupt();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
