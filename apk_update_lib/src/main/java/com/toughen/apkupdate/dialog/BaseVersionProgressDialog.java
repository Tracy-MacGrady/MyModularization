package com.toughen.apkupdate.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.toughen.apkupdate.entity.VersionInfo;
import com.toughen.apkupdate.listener.ApkDownloadStatusListener;

import java.io.File;

public abstract class BaseVersionProgressDialog extends Dialog implements ApkDownloadStatusListener {
    private String APPLICATION_ID;

    public BaseVersionProgressDialog(@NonNull Context context, String APPLICATION_ID) {
        this(context, 0);
        this.APPLICATION_ID = APPLICATION_ID;
    }

    public BaseVersionProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onBackPressed() {

    }
}
