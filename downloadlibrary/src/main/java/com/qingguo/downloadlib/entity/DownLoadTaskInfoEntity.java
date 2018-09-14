package com.qingguo.downloadlib.entity;

import com.qingguo.downloadlib.DownloadThread;

import java.util.ArrayList;
import java.util.List;

public class DownLoadTaskInfoEntity {
    private List<DownloadThread> threads = new ArrayList<>();
    private String downloadPath;
    private String fileSavePath;
    private String saveName;
    private long fileLength;
    private long hasDownLoadLength;

    public List<DownloadThread> getThreads() {
        return threads;
    }

    public void setThreads(List<DownloadThread> threads) {
        this.threads = threads;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getFileSavePath() {
        return fileSavePath;
    }

    public void setFileSavePath(String fileSavePath) {
        this.fileSavePath = fileSavePath;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public long getHasDownLoadLength() {
        return hasDownLoadLength;
    }

    public void setHasDownLoadLength(long hasDownLoadLength) {
        this.hasDownLoadLength = hasDownLoadLength;
    }

    public boolean hasFinished() {
        return hasDownLoadLength >= fileLength;
    }
}
