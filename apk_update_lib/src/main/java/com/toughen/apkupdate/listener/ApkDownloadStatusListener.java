package com.toughen.apkupdate.listener;

import com.toughen.apkupdate.entity.VersionInfo;

public interface ApkDownloadStatusListener {
    void onGetFileLength(int fileLength);

    void downloadProgress(int progress);

    void onDownloadFinished(VersionInfo versionInfo);
}
