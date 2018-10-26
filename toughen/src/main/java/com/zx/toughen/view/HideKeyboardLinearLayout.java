package com.zx.toughen.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.toughen.libs.tools.AppUtils;

public class HideKeyboardLinearLayout extends LinearLayout implements View.OnClickListener {
    public HideKeyboardLinearLayout(Context context) {
        super(context);
        init();
    }


    public HideKeyboardLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HideKeyboardLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context context = getContext();
        if (context instanceof Activity) {
            AppUtils.getInstance().hideKeyboard((Activity) context);
        }
    }
}
