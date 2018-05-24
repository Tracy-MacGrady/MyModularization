package com.toughen.libs.download;

import android.os.Handler;
import android.os.Message;
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
    private Handler handler;
    private int fileLength;

    public DownLoadTask(String downloadUrl, String savePath, Handler handler) {
        this.downloadPath = downloadUrl;
        this.fileSavePath = savePath;
        this.handler = handler;
    }

    @Override
    public void run() {
        startTask();
    }

    private void startTask() {
        fileLength = 0;
        threads.clear();
        List<DownloadDBEntity> dbEntities = DownloadDBManager.getInstance().selectList(downloadPath);
        if (dbEntities == null || dbEntities.size() == 0) {
            createDownloadInfo(downloadPath, fileSavePath);
        } else {
            for (int i = 0, size = dbEntities.size(); i < size; i++) {
                DownloadDBEntity entity = dbEntities.get(i);
                threads.add(new DownloadThread(entity));
                fileLength += entity.getThreadSaveFileLength();
            }
        }
        Message msg = Message.obtain();
        msg.what = 0;
        msg.obj = fileLength;
        handler.sendMessage(msg);
        for (int i = 0, size = threads.size(); i < size; i++) {
            DownloadThread thread = threads.get(i);
            if (thread.getEntity() != null) {
                if (!thread.getEntity().isDownloadFinished()) {
                    thread.setHandler(handler);
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
            fileLength = connection.getContentLength();
            int downloadSize = fileLength / max_thread_num;
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
