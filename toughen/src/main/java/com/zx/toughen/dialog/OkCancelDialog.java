package com.zx.toughen.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.toughen.libs.tools.DensityUtils;
import com.zx.toughen.R;
import com.zx.toughen.listenerinterface.OkCancelDialogListenerInterface;

/**
 * Created by 李健健 on 2017/3/15.
 */

public class OkCancelDialog extends Dialog implements View.OnClickListener {
    private View okView, cancelView;
    private TextView titleView, messageView;
    private OkCancelDialogListenerInterface listener;

    public OkCancelDialog(Context context) {
        super(context, R.style.Dialog_is_transparent);
        setContentView(R.layout.dialog_ok_cancle_layout);
    }


    @Override
    public void setContentView(int layoutResID) {
        View view = View.inflate(getContext(), layoutResID, null);
        initView(view);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        float height = DensityUtils.getInstance().getScreenHeightPX(getContext());
        float width = DensityUtils.getInstance().getScreenWidthPX(getContext());
        if (height > width) {
            params.width = (int) (width * 0.8);
        } else {
            params.width = (int) (height * 0.8);
        }
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        super.setContentView(view);
    }

    private void initView(View view) {
        okView = view.findViewById(R.id.ok_view);
        cancelView = view.findViewById(R.id.cancel_view);
        titleView = (TextView) view.findViewById(R.id.dialog_title);
        messageView = (TextView) view.findViewById(R.id.dialog_content);
        okView.setOnClickListener(this);
        cancelView.setOnClickListener(this);
    }

    public void setTitleViewValue(String val) {
        this.titleView.setText(val);
    }

    public void setMessageViewValue(String val) {
        this.messageView.setText(val);
    }

    public void setListener(OkCancelDialogListenerInterface listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_view:
                if (listener != null) listener.ClickOk();
                break;
            case R.id.cancel_view:
                if (listener != null) listener.ClickCancel();
                dismiss();
                break;
        }
    }
}
