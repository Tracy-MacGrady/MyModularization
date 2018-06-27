package com.qingguo.downloadlib;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.qingguo.downloadlib.database.DownloadDBEntity;
import com.qingguo.downloadlib.database.DownloadDBManager;
import com.qingguo.downloadlib.listener.DownloadStatusListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownLoadTask {
    private int max_thread_num = 3;
    private List<DownloadThread> threads = new ArrayList<>();
    private String downloadPath;
    private String fileSavePath;
    private String saveName;
    private long fileLength;
    private long hasDownLoadLength;
    private DownloadStatusListener listener;

    public DownLoadTask(String downloadUrl, String savePath, String saveName) {
        this.downloadPath = downloadUrl;
        this.fileSavePath = savePath;
        this.saveName = saveName;
    }

    public void stopTask() {
        for (int i = 0, size = threads.size(); i < size; i++) {
            threads.get(i).stopDownload();
        }
    }

    public void startTask() {
        fileLength = 0;
        threads.clear();
        List<DownloadDBEntity> dbEntities = DownloadDBManager.getInstance().selectList(downloadPath, saveName);
        if (dbEntities == null || dbEntities.size() == 0) {
            createDownloadInfo();
        } else {
            for (int i = 0, size = dbEntities.size(); i < size; i++) {
                DownloadDBEntity entity = dbEntities.get(i);
                threads.add(new DownloadThread(entity));
                fileLength = entity.getFileLength();
            }
            startThread();
        }
    }

    private void startThread() {
        if (fileLength < 0) {
            if (listener != null) listener.downloadFileNotExit();
        } else {
            for (int i = 0, size = threads.size(); i < size; i++) {
                DownloadThread thread = threads.get(i);
                if (thread.getEntity() != null) {
                    if (!thread.getEntity().isDownloadFinished()) {
                        thread.setHandler(taskHandler);
                        thread.start();
                    } else {
                        DownloadDBManager.getInstance().delete(thread.getEntity());
                        Message msg2 = Message.obtain();
                        msg2.what = DownloadConstant.DOWNLOAD_FINISH;
                        taskHandler.sendMessage(msg2);
                    }
                }
            }
        }
    }

    private void createDownloadInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(downloadPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    if (connection.getResponseCode() == 302) {
                        String headerLocation = connection.getHeaderField("Location");
                        connection.disconnect();
                        if (!TextUtils.isEmpty(downloadPath)) {
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
        }).start();
    }

    private Handler getFileLenhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                fileLength = (int) msg.obj;
                long downloadSize = fileLength / max_thread_num;
                for (int i = 0; i < max_thread_num; i++) {
                    DownloadDBEntity entity = new DownloadDBEntity();
                    if (i == max_thread_num - 1) {
                        entity.setEndLocation(fileLength);
                    } else {
                        entity.setEndLocation((i + 1) * downloadSize);
                    }
                    entity.setFileSaveName(saveName);
                    entity.setDownloadPath(downloadPath);
                    entity.setStartLocation(i * downloadSize);
                    entity.setFileSavePath(fileSavePath);
                    entity.setThreadID(i);
                    entity.setFileLength(fileLength);
                    DownloadDBManager.getInstance().insert(entity);
                    threads.add(new DownloadThread(entity));
                }
                startThread();
            }
        }
    };
    private Handler taskHandler = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DownloadConstant.DOWNLOAD_PROGRESS:
                    DownloadDBEntity entity = (DownloadDBEntity) msg.obj;
                    DownloadDBManager.getInstance().updateDownloadlength(entity);
                    Log.e(DownloadManager.class.getSimpleName(), "DOWNLOAD_PROGRESS==== " + entity.getDownloadLength());
                    break;
                case DownloadConstant.DOWNLOAD_FINISH:
                    if (listener != null) listener.downloadFinished();
                    break;
                case DownloadConstant.DOWNLOAD_FILE_NOT_EXIT:
                    if (listener != null) listener.downloadFileNotExit();
                    break;
                case DownloadConstant.THREAD_DOWNLOAD_FINISH:
                    DownloadDBEntity entity2 = (DownloadDBEntity) msg.obj;
                    hasDownLoadLength += entity2.getEndLocation() - entity2.getStartLocation();
                    DownloadDBManager.getInstance().delete(entity2);
                    if (hasDownLoadLength >= fileLength) {
                        if (listener != null) listener.downloadFinished();
                    }
                    break;
            }
        }
    };

    public void setListener(DownloadStatusListener listener) {
        this.listener = listener;
    }
}
