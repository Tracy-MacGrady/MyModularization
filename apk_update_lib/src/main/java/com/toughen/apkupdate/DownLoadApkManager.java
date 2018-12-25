package com.toughen.apkupdate;

import android.os.Handler;
import android.os.Message;

import com.toughen.apkupdate.entity.VersionInfo;
import com.toughen.apkupdate.listener.ApkDownloadStatusListener;
import com.toughen.apkupdate.thread.DownLoadApkThread;
import com.toughen.apkupdate.thread.GetDownloadAPKLengthThread;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadApkManager {
    private ApkDownloadStatusListener downloadStatusListener;
    private VersionInfo versionInfo;

    public DownLoadApkManager(ApkDownloadStatusListener downloadStatusListener, VersionInfo versionInfo) {
        this.downloadStatusListener = downloadStatusListener;
        this.versionInfo = versionInfo;
    }

    public void getFileLength() {
        GetDownloadAPKLengthThread getFileLengthThread = new GetDownloadAPKLengthThread(versionInfo.getApkUrl(), handler);
        getFileLengthThread.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.GET_FILE_LENGTH_HANDLER_WHAT:
                    int maxProgress = (int) msg.obj;
                    new DownLoadApkThread(versionInfo, handler).start();
                    if (downloadStatusListener != null)
                        downloadStatusListener.onGetFileLength(maxProgress);
                    break;
                case Constant.DOWNLOAD_FILE_PROGRESS_HANDLER_WHAT:
                    int progress = (int) msg.obj;
                    if (downloadStatusListener != null)
                        downloadStatusListener.downloadProgress(progress);
                    break;
                case Constant.DOWNLOAD_FILE_FINISH_HANDLER_WHAT:
                    if (downloadStatusListener != null) downloadStatusListener.onDownloadFinished();
                    break;
            }
        }
    };
}
