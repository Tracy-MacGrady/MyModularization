package com.toughen.libs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.toughen.libs.R;

public class MyBannerView extends FrameLayout {
    public MyBannerView(Context context) {
        super(context);
    }

    public MyBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
//        View.inflate(getContext(),R.layout.dddd);
    }
}
