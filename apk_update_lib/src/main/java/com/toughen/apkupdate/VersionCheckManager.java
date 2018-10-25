package com.toughen.apkupdate;

import com.toughen.apkupdate.dialog.BaseVersionDialog;
import com.toughen.apkupdate.dialog.BaseVersionProgressDialog;
import com.toughen.apkupdate.entity.VersionInfo;
import com.toughen.apkupdate.listener.VersionDialogDownloadListener;

public class VersionCheckManager {
    private static VersionCheckManager manager;
    private DownLoadApkManager downLoadApkManager;
    private BaseVersionProgressDialog progressDialog;
    private BaseVersionDialog versionDialog;

    private VersionCheckManager() {
    }

    public static VersionCheckManager getInstance() {
        if (manager == null) synchronized (VersionCheckManager.class) {
            if (manager == null) manager = new VersionCheckManager();
        }
        return manager;
    }

    /**
     * 比较当前版本与服务器版本是否相等
     *
     * @param nowVersionName 当前版本名称
     * @param versionName    服务器最新版本名称
     * @return 返回-1表示当前版本高于服务器版本   返回0表示版本相同  返回1表示当前版本小于服务器版本
     */
    public int compareVersion(String nowVersionName, String versionName) {
        if (nowVersionName.equalsIgnoreCase(versionName)) return 0;
        else {
            return 1;
        }
    }


    /**
     * 比较当前版本与服务器版本是否相等
     *
     * @param nowVersionCode
     * @param versionCode
     * @return 返回-1表示当前版本高于服务器版本   返回0表示版本相同  返回1表示当前版本小于服务器版本
     */
    public int compareVersion(int nowVersionCode, int versionCode) {
        if (nowVersionCode == versionCode) return 0;
        else {
            if (nowVersionCode > versionCode) return -1;
            else return 1;
        }
    }

    public void setVersionDialog(BaseVersionDialog dialog) {
        this.versionDialog = dialog;
    }

    public void setProgressDialog(BaseVersionProgressDialog dialog) {
        this.progressDialog = dialog;
    }

    /**
     * 检查更新版本
     *
     * @param versionInfo 更新版本信息
     */
    public void checkVersion(VersionInfo versionInfo) {
        if (versionInfo == null || !versionInfo.isAutoUpdate() || versionDialog == null) return;
        if (versionInfo.isForceUpdate()) {
            versionDialog.show();
        } else if (versionInfo.isName()) {
            if (compareVersion(versionInfo.getLocalVersionName(), versionInfo.getNewVersionName()) == 1) {
                versionDialog.setDownloadListener(new MyVersionDialogDownloadListener(versionInfo));
                versionDialog.show();
            }
        } else {
            if (compareVersion(versionInfo.getLocalVersionCode(), versionInfo.getNewVersionCode()) == 1) {
                versionDialog.setDownloadListener(new MyVersionDialogDownloadListener(versionInfo));
                versionDialog.show();
            }
        }
    }

    private class MyVersionDialogDownloadListener implements VersionDialogDownloadListener {
        private VersionInfo versionInfo;

        public MyVersionDialogDownloadListener(VersionInfo versionInfo) {
            this.versionInfo = versionInfo;
        }

        @Override
        public void onClickDownloadApk() {
            if (versionDialog != null) versionDialog.dismiss();
            if (progressDialog != null) progressDialog.show();
            downLoadApkManager = new DownLoadApkManager(progressDialog.getDownloadStatusListener(), versionInfo);
            downLoadApkManager.getFileLength();
        }
    }
}
