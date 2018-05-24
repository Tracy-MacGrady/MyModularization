package com.toughen.libs.download;

import android.content.Context;

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
        DownLoadTask task = new DownLoadTask(downloadURL, savePath);
        task.start();
    }
}
