package com.toughen.apkupdate.thread;

import android.os.Handler;
import android.os.Message;

import com.toughen.apkupdate.Constant;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetDownloadAPKLengthThread extends Thread {
    private String downloadPath;
    private Handler handler;

    public GetDownloadAPKLengthThread(String downloadPath, Handler handler) {
        this.downloadPath = downloadPath;
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(downloadPath).openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
                String path = connection.getHeaderField("Location");
                connection = (HttpURLConnection) new URL(path).openConnection();
            }
            int length = connection.getContentLength();
            connection.disconnect();
            if (handler != null) {
                Message msg = Message.obtain();
                msg.obj = length;
                msg.what = Constant.GET_FILE_LENGTH_HANDLER_WHAT;
                handler.sendMessage(msg);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
