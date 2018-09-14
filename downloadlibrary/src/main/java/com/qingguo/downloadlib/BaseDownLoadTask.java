package com.qingguo.downloadlib;

import com.qingguo.downloadlib.entity.DownLoadTaskInfoEntity;
import com.qingguo.downloadlib.interfaces.DownLoadTaskInterface;
import com.qingguo.downloadlib.listener.DownloadStatusListener;

public abstract class BaseDownLoadTask implements DownLoadTaskInterface {
    protected DownloadStatusListener<DownLoadTaskInfoEntity> listener;
    protected DownLoadTaskInfoEntity taskEntity;

    public BaseDownLoadTask(String downloadUrl, String savePath, String saveName) {
        taskEntity = new DownLoadTaskInfoEntity();
        taskEntity.setDownloadPath(downloadUrl);
        taskEntity.setFileSavePath(savePath);
        taskEntity.setSaveName(saveName);
    }

    public void setListener(DownloadStatusListener listener) {
        this.listener = listener;
    }
}
