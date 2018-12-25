package com.toughen.apkupdate.thread;

import android.os.Handler;
import android.os.Message;

import com.toughen.apkupdate.Constant;
import com.toughen.apkupdate.entity.VersionInfo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadApkThread extends Thread {

    private VersionInfo versionInfo;
    private Handler handler;

    public DownLoadApkThread(VersionInfo versionInfo, Handler handler) {
        this.versionInfo = versionInfo;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        loadFile(versionInfo.getApkUrl());
    }

    private void loadFile(String apkUrl) {
        try {
            URL url = new URL(apkUrl);
            ConnectionInfo info = getReallyConnection((HttpURLConnection) url.openConnection());
            if (info.getCode() == 206 || info.getCode() == 200) {
                InputStream is = info.getConnection().getInputStream();
                if (is != null) {
                    File file = new File(versionInfo.getSaveFilePath(), versionInfo.getSaveFileName());
                    if (!file.exists()) {
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    int total = 0;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        total += len;
                        //获取当前下载量
                        Message msg = Message.obtain();
                        msg.obj = total;
                        msg.what = Constant.DOWNLOAD_FILE_PROGRESS_HANDLER_WHAT;
                        handler.sendMessage(msg);
                    }
                    fos.close();
                    bis.close();
                    is.close();
                    Message msg = Message.obtain();
                    msg.what = Constant.DOWNLOAD_FILE_FINISH_HANDLER_WHAT;
                    handler.sendMessage(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConnectionInfo getReallyConnection(HttpURLConnection conn) throws IOException {
        ConnectionInfo connectionInfo = new ConnectionInfo();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setConnectTimeout(5000);
        //设置为长连接
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
        conn.setReadTimeout(20000);  //设置读取流的等待时间,必须设置该参数
        connectionInfo.setCode(conn.getResponseCode());
        connectionInfo.setConnection(conn);
        if (connectionInfo.getCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
            String path = conn.getHeaderField("Location");
            conn.disconnect();
            conn = (HttpURLConnection) new URL(path).openConnection();
            return getReallyConnection(conn);
        }
        return connectionInfo;
    }

    private class ConnectionInfo {
        private HttpURLConnection connection;
        private int code;

        public HttpURLConnection getConnection() {
            return connection;
        }

        public void setConnection(HttpURLConnection connection) {
            this.connection = connection;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
