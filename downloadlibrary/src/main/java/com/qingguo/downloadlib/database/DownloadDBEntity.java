package com.qingguo.downloadlib.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.qingguo.downloadlib.entity.DownLoadTaskInfoEntity;

public class DownloadDBEntity implements Parcelable {
    private int id;
    private String fileSaveName;//文件本地存储名称
    private String fileSavePath;//文件本地存储地址
    private String downloadPath;//文件下载地址
    private int threadID;//对应的下载线程ID
    private long downloadLength;//对应单个下载线程中文件已经下载的长度
    private long endLocation;//单个线程下载文件的结束位置
    private long startLocation;//单个线程下载文件的开始位置
    private long fileLength;//文件总长度

    public DownloadDBEntity() {
    }

    protected DownloadDBEntity(Parcel in) {
        id = in.readInt();
        fileSaveName = in.readString();
        fileSavePath = in.readString();
        downloadPath = in.readString();
        threadID = in.readInt();
        downloadLength = in.readLong();
        endLocation = in.readLong();
        startLocation = in.readLong();
        fileLength = in.readLong();
    }

    public static final Creator<DownloadDBEntity> CREATOR = new Creator<DownloadDBEntity>() {
        @Override
        public DownloadDBEntity createFromParcel(Parcel in) {
            return new DownloadDBEntity(in);
        }

        @Override
        public DownloadDBEntity[] newArray(int size) {
            return new DownloadDBEntity[size];
        }
    };

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
        return downloadLength > 0 && downloadLength >= (endLocation - startLocation);
    }

    public void setFileInfo(DownLoadTaskInfoEntity taskEntity) {
        setDownloadPath(taskEntity.getDownloadPath());
        setFileSavePath(taskEntity.getFileSavePath());
        setFileSaveName(taskEntity.getSaveName());
        setFileLength(taskEntity.getFileLength());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(fileSaveName);
        dest.writeString(fileSavePath);
        dest.writeString(downloadPath);
        dest.writeInt(threadID);
        dest.writeLong(downloadLength);
        dest.writeLong(endLocation);
        dest.writeLong(startLocation);
        dest.writeLong(fileLength);
    }
}
