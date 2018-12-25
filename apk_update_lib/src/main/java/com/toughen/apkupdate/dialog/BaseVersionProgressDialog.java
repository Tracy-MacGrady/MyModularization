package com.toughen.apkupdate.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.toughen.apkupdate.listener.ApkDownloadStatusListener;

public abstract class BaseVersionProgressDialog extends Dialog implements ApkDownloadStatusListener {

    public BaseVersionProgressDialog(@NonNull Context context) {
        super(context);
    }

    public BaseVersionProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onBackPressed() {

    }

}
