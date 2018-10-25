package com.toughen.apkupdate.entity;

public class VersionInfo {
    private String localVersionName;//本地版本名称
    private int localVersionCode;//本地版本号
    private String newVersionName;//最新的版本名称
    private int newVersionCode;//最新的版本号
    private String apkUrl;//apk 文件下载地址
    private boolean autoUpdate;//是否开启自动更新
    private boolean forceUpdate;//是否强制更新
    private boolean isName = true;//是否根据版本名称来检测版本更新
    private String saveFilePath;
    private String saveFileName;

    public VersionInfo(String localVersionName, String newVersionName, String apkUrl, boolean autoUpdate, boolean forceUpdate) {
        this.localVersionName = localVersionName;
        this.newVersionName = newVersionName;
        this.apkUrl = apkUrl;
        this.autoUpdate = autoUpdate;
        this.forceUpdate = forceUpdate;
        this.isName = true;
    }

    public VersionInfo(int localVersionCode, int newVersionCode, String apkUrl, boolean autoUpdate, boolean forceUpdate) {
        this.localVersionCode = localVersionCode;
        this.newVersionCode = newVersionCode;
        this.apkUrl = apkUrl;
        this.autoUpdate = autoUpdate;
        this.forceUpdate = forceUpdate;
        this.isName = false;
    }

    public String getLocalVersionName() {
        return localVersionName;
    }

    public void setLocalVersionName(String localVersionName) {
        this.localVersionName = localVersionName;
    }

    public int getLocalVersionCode() {
        return localVersionCode;
    }

    public void setLocalVersionCode(int localVersionCode) {
        this.localVersionCode = localVersionCode;
    }

    public String getNewVersionName() {
        return newVersionName;
    }

    public void setNewVersionName(String newVersionName) {
        this.newVersionName = newVersionName;
    }

    public int getNewVersionCode() {
        return newVersionCode;
    }

    public void setNewVersionCode(int newVersionCode) {
        this.newVersionCode = newVersionCode;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public boolean isName() {
        return isName;
    }

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }
}
