package com.toughen.libs.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toughen.libs.R;

public class MyEmptyView extends LinearLayout {
    private TextView textView;
    private ImageView imageView;

    public MyEmptyView(Context context) {
        super(context);
        initView();
    }

    public MyEmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        View.inflate(getContext(), R.layout.layout_empty_view, this);
        textView = findViewById(R.id.tip_textview);
        imageView = findViewById(R.id.tip_imgview);
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }

    public void setImageViewSrc(int res) {
        imageView.setImageResource(res);
    }
}
