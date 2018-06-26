package com.qingguo.downloadlib.database;

public class DownloadDBEntity {
    private int id;
    private String fileSaveName;//文件本地存储名称
    private String fileSavePath;//文件本地存储地址
    private String downloadPath;//文件下载地址
    private int threadID;//对应的下载线程ID
    private long downloadLength;//对应单个下载线程中文件已经下载的长度
    private long endLocation;//单个线程下载文件的结束位置
    private long startLocation;//单个线程下载文件的开始位置
    private long fileLength;//文件总长度

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

    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName;
    }

    public String getFileSavePath() {
        return fileSavePath;
    }

    public void setFileSavePath(String fileSavePath) {
        this.fileSavePath = fileSavePath;
    }

    public long getDownloadLength() {
        return downloadLength;
    }

    public void setDownloadLength(long downloadLength) {
        this.downloadLength = downloadLength;
    }

    public long getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(long endLocation) {
        this.endLocation = endLocation;
    }

    public long getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(long startLocation) {
        this.startLocation = startLocation;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public boolean isDownloadFinished() {
        return downloadLength > 0 && downloadLength >= fileLength;
    }

}
