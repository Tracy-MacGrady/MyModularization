package com.qingguo.downloadlib.listener;

public interface DownloadFinishListener {
    void downloadFinish();

    void fileNotExit(Object cause);
}
