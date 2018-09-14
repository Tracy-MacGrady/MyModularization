package com.qingguo.downloadlib.listener;

import com.qingguo.downloadlib.entity.DownLoadTaskInfoEntity;

public interface DownloadFinishListener {
    void downloadFinish();

    void fileNotExit(Object cause);

    void downloadProgress(DownLoadTaskInfoEntity entity);
}
