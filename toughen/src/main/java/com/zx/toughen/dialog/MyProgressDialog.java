package com.zx.toughen.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import toughen.zx.com.R;

import com.zx.toughenlib.base.BaseProgressDialog;
import com.zx.toughenlib.tools.ScreenTool;
import com.zx.toughenlib.tools.LogUtil;

/**
 * Created by 李健健 on 2017/3/20.
 */

public class MyProgressDialog extends BaseProgressDialog {
    private TextView tipContentView;

    public MyProgressDialog(Context context) {
        super(context, R.style.dialog_is_not_transparent);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_progress_layout);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = View.inflate(getContext(), layoutResID, null);
        tipContentView = (TextView) view.findViewById(R.id.tip_content_view);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        int height = ScreenTool.getScreenHeight(getContext());
        int width = ScreenTool.getScreenWidth(getContext());
        if (height > width) {
            params.width = (int) (width * 0.6);
        } else {
            params.width = (int) (height * 0.6);
        }
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        super.setContentView(view, params);
    }

    @Override
    public void setContentValue(String contentValue) {
        if (tipContentView == null) return;
        tipContentView.setText(contentValue);
    }
}
