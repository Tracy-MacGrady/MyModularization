package com.qingguo.downloadlib;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.qingguo.downloadlib.database.DownloadDBEntity;
import com.qingguo.downloadlib.database.DownloadDBManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class DownloadThread extends Thread {

    private static final String TAG = "DownloadThread";
    private static final int TIME_OUT = 5000;
    private Handler handler;
    private DownloadDBEntity entity;

    public DownloadThread(DownloadDBEntity entity) {
        this.entity = entity;
    }

    @Override
    public void run() {
        if (entity.isDownloadFinished()) {
            Log.e(TAG, "下载已经完成");
            DownloadDBManager.getInstance().delete(entity);
            Message msg = Message.obtain();
            msg.what = DownloadConstant.DOWNLOAD_FINISH;
            handler.sendMessage(msg);
            return;
        }
        Log.d(TAG, "线程_" + entity.getThreadID() + "_正在下载【" + "开始位置 : " + entity.getStartLocation() + "，结束位置：" + entity.getEndLocation() + "】");
        loadFile(entity.getDownloadPath());
    }

    private void loadFile(String downloadUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(downloadUrl);
            conn = (HttpURLConnection) url.openConnection();
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
            int responseCode = conn.getResponseCode();
            if (responseCode == 206) {
                InputStream is = conn.getInputStream();
                if (is != null) {
                    //创建可设置位置的文件
                    RandomAccessFile file = new RandomAccessFile(new File(entity.getFileSavePath(), entity.getFileSaveName()), "rwd");
                    //设置每条线程写入文件的位置
                    file.seek(entity.getStartLocation() + entity.getDownloadLength());
                    //
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[10240];
                    int readLen;
                    long lastTime = System.currentTimeMillis();
                    while ((readLen = bis.read(buffer)) != -1 && !this.isInterrupted()) {
                        file.write(buffer, 0, readLen);
                        long timeSpace = System.currentTimeMillis() - lastTime;
                        if (timeSpace > 700) {
                            lastTime = System.currentTimeMillis();
                            Log.e("time==", "TIME===" + timeSpace);
                            Message msg = Message.obtain();
                            msg.what = DownloadConstant.DOWNLOAD_PROGRESS;
                            Bundle bundle = new Bundle();
                            bundle.putLong("readLen", readLen);
                            bundle.putString("DownloadPath", entity.getDownloadPath());
                            bundle.putString("FileSaveName", entity.getFileSaveName());
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }
                    file.close();
                    bis.close();
                    is.close();
                }
            } else if (responseCode == 302) {
                String headerLocation = conn.getHeaderField("Location");
                conn.disconnect();
                loadFile(headerLocation);
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Message msg = Message.obtain();
            msg.what = DownloadConstant.THREAD_DOWNLOAD_FINISH;
            msg.obj = entity;
            handler.sendMessage(msg);
            if (conn != null) conn.disconnect();
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
