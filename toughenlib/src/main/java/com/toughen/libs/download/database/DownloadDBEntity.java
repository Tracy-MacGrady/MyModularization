package com.toughen.libs.download.database;

public class DownloadDBEntity {
    private int id;
    private String fileSavePath;//文件本地存储地址
    private String downloadPath;//文件下载地址
    private int threadID;//对应的下载线程ID
    private int downloadLength;//对应单个下载线程中文件已经下载的长度
    private int endLocation;//单个线程下载文件的结束位置
    private int startLocation;//单个线程下载文件的开始位置

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThreadID() {
        return threadID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
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

    public int getDownloadLength() {
        return downloadLength;
    }

    public void setDownloadLength(int downloadLength) {
        this.downloadLength = downloadLength;
    }

    public int getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(int endLocation) {
        this.endLocation = endLocation;
    }

    public int getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(int startLocation) {
        this.startLocation = startLocation;
    }

    public boolean isDownloadFinished() {
        return downloadLength > 0 && downloadLength >= endLocation - startLocation;
    }
}
