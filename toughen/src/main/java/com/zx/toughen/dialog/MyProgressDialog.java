package com.zx.toughen.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.toughen.libs.tools.DensityUtils;
import com.zx.toughen.R;

/**
 * Created by 李健健 on 2017/3/20.
 */

public class MyProgressDialog extends Dialog {
    private TextView tipContentView;

    public MyProgressDialog(Context context) {
        super(context, R.style.Dialog_is_not_transparent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress_layout);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = View.inflate(getContext(), layoutResID, null);
        tipContentView = view.findViewById(R.id.tip_content_view);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        float height = DensityUtils.getInstance().getScreenHeightPX(getContext());
        float width = DensityUtils.getInstance().getScreenWidthPX(getContext());
        if (height > width) {
            params.width = (int) (width * 0.6);
        } else {
            params.width = (int) (height * 0.6);
        }
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        super.setContentView(view, params);
    }

    public void setContentValue(String contentValue) {
        if (tipContentView == null) return;
        tipContentView.setText(contentValue);
    }
}
