package com.qingguo.downloadlib.util;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.qingguo.downloadlib.DownloadThread;
import com.qingguo.downloadlib.database.DownloadDBEntity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDownLoadFileLengthUtil {
    private GetDownLoadFileLengthListener listener;

    public GetDownLoadFileLengthUtil(GetDownLoadFileLengthListener listener) {
        this.listener = listener;
    }

    public void getFileLength(String downLoadPath) {
        new Thread(new GetDownLoadFileLengthRunnable(downLoadPath)).start();
    }

    class GetDownLoadFileLengthRunnable implements Runnable {
        private String downLoadPath;

        public GetDownLoadFileLengthRunnable(String downLoadPath) {
            this.downLoadPath = downLoadPath;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(downLoadPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                if (connection.getResponseCode() == 302) {
                    String headerLocation = connection.getHeaderField("Location");
                    connection.disconnect();
                    if (!TextUtils.isEmpty(downLoadPath)) {
                        url = new URL(headerLocation);
                        connection = (HttpURLConnection) url.openConnection();
                    }
                }
                int fileLength = connection.getContentLength();
                connection.disconnect();
                Message msg = Message.obtain();
                msg.what = 100;
                msg.obj = fileLength;
                getFileLenhandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Handler getFileLenhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                if (listener != null) listener.getFinished((int) msg.obj);
            }
        }
    };

    public interface GetDownLoadFileLengthListener {
        void getFinished(int length);
    }
}
