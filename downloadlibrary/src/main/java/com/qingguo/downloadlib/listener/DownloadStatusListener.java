package com.qingguo.downloadlib.listener;

public interface DownloadStatusListener<T> {
    void downloadProgress(T entity);

    void downloadFinished(T entity);

    void downloadFileNotExit(T entity);
}
