package com.toughen.libs.download;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.toughen.libs.download.database.DownloadDBManager;

public class DownloadManager {
    private static volatile DownloadManager instance;

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) instance = new DownloadManager();
            }
        }
        return instance;
    }

    public void init(Context context) {
        DownloadDBManager.getInstance().init(context);
    }

    public void destory() {
        DownloadDBManager.getInstance().destory();
    }

    public void startDownload(String downloadURL, String savePath) {
        DownLoadTask task = new DownLoadTask(downloadURL, savePath, handler);
        task.start();
    }

    private Handler handler = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.e(DownloadManager.class.getSimpleName(), "this is download file length=" + msg.obj);
                    break;
                case 1:
                    Log.e(DownloadManager.class.getSimpleName(), "this is download file progress length=" + msg.obj);
                    break;
            }
        }
    };
}
