package com.toughen.apkupdate.listener;

public interface ApkDownloadStatusListener {
    void onGetFileLength(int fileLength);

    void downloadProgress(int progress);

    void onDownloadFinished();
}
