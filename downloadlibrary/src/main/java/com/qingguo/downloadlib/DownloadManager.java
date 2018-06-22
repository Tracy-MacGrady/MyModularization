package com.qingguo.downloadlib;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.qingguo.downloadlib.database.DownloadDBEntity;
import com.qingguo.downloadlib.database.DownloadDBManager;
import com.qingguo.downloadlib.listener.DownloadFinishListener;

import java.util.HashMap;
import java.util.Map;

public class DownloadManager {
    private static volatile DownloadManager instance;
    private Map<String, DownLoadTask> taskMap = new HashMap();
    private boolean isRunning = false;
    private DownloadFinishListener downloadFinishListener;

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
        taskMap.clear();
        DownloadDBManager.getInstance().init(context);
    }

    public void setDownloadFinishListener(DownloadFinishListener downloadFinishListener) {
        this.downloadFinishListener = downloadFinishListener;
    }

    public void destory() {
        taskMap.clear();
        isRunning = false;
        DownloadDBManager.getInstance().destory();
    }

    private void run() {
        if (!isRunning) {
            isRunning = true;
            continueRun();
        }
    }

    private void continueRun() {
        if (!isRunning) return;
        if (taskMap.size() > 0) {
            String key0 = taskMap.keySet().iterator().next();
            DownLoadTask task = taskMap.get(key0);
            taskMap.remove(key0);
            task.setHandler(handler);
            task.startTask();
        } else {
            isRunning = false;
            if (downloadFinishListener != null) downloadFinishListener.downloadFinish();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DownloadConstant.DOWNLOAD_GET_FILELENGTH:
                    Log.e(DownloadManager.class.getSimpleName(), "this is download file length=" + msg.obj);
                    break;
                case DownloadConstant.DOWNLOAD_PROGRESS:
                    DownloadDBEntity entity = (DownloadDBEntity) msg.obj;
                    DownloadDBManager.getInstance().update(entity);
                    Log.e(DownloadManager.class.getSimpleName(), "this is download file progress length=" + entity.getDownloadLength());
                    break;
                case DownloadConstant.DOWNLOAD_FINISH:
                    continueRun();
                    break;
                case DownloadConstant.DOWNLOAD_FILE_NOT_EXIT:
                    if (downloadFinishListener != null) downloadFinishListener.fileNotExit(msg.obj);
                    continueRun();
                    break;
            }
        }
    };

    public void setDownloadTask(Map<String, DownLoadTask> taskMap1) {
        this.taskMap.putAll(taskMap1);
        if (taskMap.size() > 0 && !isRunning) run();
    }
}
