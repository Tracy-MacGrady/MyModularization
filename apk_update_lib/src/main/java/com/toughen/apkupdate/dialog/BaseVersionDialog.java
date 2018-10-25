package com.toughen.apkupdate.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.toughen.apkupdate.listener.VersionDialogDownloadListener;

public abstract class BaseVersionDialog extends Dialog {
    private boolean canCloseDialog = true;
    private VersionDialogDownloadListener downloadListener;

    public BaseVersionDialog(@NonNull Context context) {
        super(context);
    }

    public BaseVersionDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onBackPressed() {
        if (canCloseDialog) super.onBackPressed();
    }

    public void setCanCloseDialog(boolean canCloseDialog) {
        this.canCloseDialog = canCloseDialog;
        setCancelable(canCloseDialog);
        setCanceledOnTouchOutside(canCloseDialog);
    }

    public void setDownloadListener(VersionDialogDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

}
