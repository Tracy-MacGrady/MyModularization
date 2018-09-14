package com.qingguo.downloadlib;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.qingguo.downloadlib.database.DownloadDBEntity;
import com.qingguo.downloadlib.database.DownloadDBManager;
import com.qingguo.downloadlib.entity.DownLoadTaskInfoEntity;
import com.qingguo.downloadlib.interfaces.DownLoadTaskInterface;
import com.qingguo.downloadlib.listener.DownloadStatusListener;
import com.qingguo.downloadlib.util.GetDownLoadFileLengthUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownLoadTaskNoDB extends BaseDownLoadTask {
    private int max_thread_num = 3;

    public DownLoadTaskNoDB(String downloadUrl, String savePath, String saveName) {
        super(downloadUrl, savePath, saveName);
    }

    public DownLoadTaskNoDB(String downloadUrl, String savePath, String saveName, int max_thread_num) {
        super(downloadUrl, savePath, saveName);
        setMax_thread_num(max_thread_num);
    }

    public void stopTask() {
        for (int i = 0, size = taskEntity.getThreads().size(); i < size; i++) {
            taskEntity.getThreads().get(i).stopDownload();
        }
    }

    public void startTask() {
        taskEntity.setFileLength(0);
        taskEntity.getThreads().clear();
        createDownloadInfo();
    }

    private void startThread() {
        if (taskEntity.getFileLength() < 0) {
            if (listener != null) listener.downloadFileNotExit(taskEntity);
        } else {
            for (int i = 0, size = taskEntity.getThreads().size(); i < size; i++) {
                DownloadThread thread = taskEntity.getThreads().get(i);
                if (thread.getEntity() != null) {
                    if (!thread.getEntity().isDownloadFinished()) {
                        thread.setHandler(taskHandler);
                        thread.start();
                    } else {
                        Message msg2 = Message.obtain();
                        msg2.what = DownloadConstant.DOWNLOAD_FINISH;
                        taskHandler.sendMessage(msg2);
                    }
                }
            }
        }
    }

    private void createDownloadInfo() {
        GetDownLoadFileLengthUtil util = new GetDownLoadFileLengthUtil(getDownLoadFileLengthListener);
        util.getFileLength(taskEntity.getDownloadPath());
    }

    private GetDownLoadFileLengthUtil.GetDownLoadFileLengthListener getDownLoadFileLengthListener = new GetDownLoadFileLengthUtil.GetDownLoadFileLengthListener() {
        @Override
        public void getFinished(int length) {
            taskEntity.setFileLength(length);
            long downloadSize = length / max_thread_num;
            for (int i = 0; i < max_thread_num; i++) {
                DownloadDBEntity entity = new DownloadDBEntity();
                if (i == max_thread_num - 1) {
                    entity.setEndLocation(length);
                } else {
                    entity.setEndLocation((i + 1) * downloadSize);
                }
                entity.setFileInfo(taskEntity);
                entity.setStartLocation(i * downloadSize);
                entity.setThreadID(i);
                taskEntity.getThreads().add(new DownloadThread(entity));
            }
            startThread();
        }
    };
    protected Handler taskHandler = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DownloadConstant.DOWNLOAD_PROGRESS:
                    Bundle data = msg.getData();
                    if (data == null) return;
                    long progress = data.getLong("DOWNLOAD_PROGRESS");
                    taskEntity.setHasDownLoadLength(taskEntity.getHasDownLoadLength() + progress);
                    if (listener != null) listener.downloadProgress(taskEntity);
                    break;
                case DownloadConstant.DOWNLOAD_FINISH:
                    if (listener != null) listener.downloadFinished(taskEntity);
                    break;
                case DownloadConstant.DOWNLOAD_FILE_NOT_EXIT:
                    if (listener != null) listener.downloadFileNotExit(taskEntity);
                    break;
                case DownloadConstant.THREAD_DOWNLOAD_FINISH:
                    if (taskEntity.hasFinished()) {
                        if (listener != null) listener.downloadFinished(taskEntity);
                    }
                    break;
            }
        }
    };

    public void setMax_thread_num(int max_thread_num) {
        this.max_thread_num = max_thread_num;
    }
}
