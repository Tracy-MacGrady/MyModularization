package com.toughen.libs.download;

import android.text.TextUtils;

import com.toughen.libs.download.database.DownloadDBEntity;
import com.toughen.libs.download.database.DownloadDBManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownLoadTask extends Thread {
    private int max_thread_num = 3;
    private List<DownloadThread> threads = new ArrayList<>();
    private String downloadPath;
    private String fileSavePath;

    public DownLoadTask(String downloadUrl, String savePath) {
        this.downloadPath = downloadUrl;
        this.fileSavePath = savePath;
    }

    @Override
    public void run() {
        startTask();
    }

    private void startTask() {
        List<DownloadDBEntity> dbEntities = DownloadDBManager.getInstance().selectList(downloadPath);
        if (dbEntities == null || dbEntities.size() == 0) {
            createDownloadInfo(downloadPath, fileSavePath);
        } else {
            threads.clear();
            for (int i = 0, size = dbEntities.size(); i < size; i++) {
                threads.add(new DownloadThread(dbEntities.get(i)));
            }
        }
        for (int i = 0, size = threads.size(); i < size; i++) {
            DownloadThread thread = threads.get(i);
            if (thread.getEntity() != null) {
                if (!thread.getEntity().isDownloadFinished()) {
                    thread.start();
                } else DownloadDBManager.getInstance().delete(thread.getEntity());
            }
        }
    }

    private void createDownloadInfo(String downloadPath, String fileSavePath) {
        try {
            URL url = new URL(downloadPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 302) {
                String headerLocation = connection.getHeaderField("Location");
                if (!TextUtils.isEmpty(downloadPath)) {
                    url = new URL(headerLocation);
                    connection = (HttpURLConnection) url.openConnection();
                }
            }
            int fileLength = connection.getContentLength();
            int downloadSize = fileLength / max_thread_num;
            threads.clear();
            for (int i = 0; i < max_thread_num; i++) {
                DownloadDBEntity entity = new DownloadDBEntity();
                if (i == max_thread_num - 1) {
                    entity.setEndLocation(fileLength);
                } else {
                    entity.setEndLocation((i + 1) * downloadSize);
                }
                entity.setDownloadLength(0);
                entity.setDownloadPath(downloadPath);
                entity.setStartLocation(i * downloadSize);
                entity.setFileSavePath(fileSavePath);
                entity.setThreadID(i);
                DownloadDBManager.getInstance().insert(entity);
                threads.add(new DownloadThread(entity));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
