package com.zx.toughen.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.toughen.R;
import com.zx.toughen.listenerinterface.MyTitleBarViewListenerInterface;

/**
 * Created by 李健健 on 2017/7/31.
 */

public class MyTitleBarView extends RelativeLayout implements View.OnClickListener {
    private TextView titleView, leftTextView, rightTextView;
    private ImageView leftImageView, rightImageView;
    //
    private MyTitleBarViewListenerInterface listener;

    public MyTitleBarView(Context context) {
        super(context);
        init(context);
    }

    public MyTitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        TypedArray arrays = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBarView, defStyleAttr, 0);
        String titleValue = arrays.getString(R.styleable.MyTitleBarView_title_name);
        if (TextUtils.isEmpty(titleValue)) {
            int resId = arrays.getResourceId(R.styleable.MyTitleBarView_title_name, 0);
            if (resId != 0) titleValue = context.getString(resId);
        }
        titleView.setText(titleValue);
        if (arrays.getBoolean(R.styleable.MyTitleBarView_show_left_text, false)) {
            String leftTextVal = arrays.getString(R.styleable.MyTitleBarView_left_text_val);
            if (TextUtils.isEmpty(leftTextVal)) {
                int resId = arrays.getResourceId(R.styleable.MyTitleBarView_left_text_val, 0);
                if (resId != 0) leftTextVal = context.getString(resId);
            }
            setLeftTextView(leftTextVal);
        }
        if (arrays.getBoolean(R.styleable.MyTitleBarView_show_right_text, false)) {
            String rightTextVal = arrays.getString(R.styleable.MyTitleBarView_right_text_val);
            if (TextUtils.isEmpty(rightTextVal)) {
                int resId = arrays.getResourceId(R.styleable.MyTitleBarView_right_text_val, 0);
                if (resId != 0) rightTextVal = context.getString(resId);
            }
            setRightTextView(rightTextVal);
        }
        if (arrays.getBoolean(R.styleable.MyTitleBarView_show_left_img, false)) {
            int resId = arrays.getResourceId(R.styleable.MyTitleBarView_left_img_res, 0);
            if (resId != 0) setLeftImageView(resId);
        }
        if (arrays.getBoolean(R.styleable.MyTitleBarView_show_right_img, false)) {
            int resId = arrays.getResourceId(R.styleable.MyTitleBarView_right_img_res, 0);
            if (resId != 0) setRightImageView(resId);
        }
        arrays.recycle();
    }

    private void init(Context context) {
        View.inflate(context, R.layout.layout_main_titlebar, this);
        titleView = findViewById(R.id.title_textview);
        leftTextView = findViewById(R.id.left_textview);
        rightTextView = findViewById(R.id.right_textview);
        leftImageView = findViewById(R.id.left_img_view);
        rightImageView = findViewById(R.id.right_img_view);
        titleView.setOnClickListener(this);
        leftTextView.setOnClickListener(this);
        rightTextView.setOnClickListener(this);
        leftImageView.setOnClickListener(this);
        rightImageView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_textview:
                if (listener != null) listener.clickTitle();
                break;
            case R.id.left_textview:
                if (listener != null) listener.clickLeft();
                break;
            case R.id.right_textview:
                if (listener != null) listener.clickRight();
                break;
            case R.id.left_img_view:
                if (listener != null) listener.clickLeft();
                break;
            case R.id.right_img_view:
                if (listener != null) listener.clickRight();
                break;
        }
    }

    public void setListener(MyTitleBarViewListenerInterface listener) {
        this.listener = listener;
    }

    public void setTitleViewValue(String titleViewValue) {
        this.titleView.setText(titleViewValue);
    }

    public void setLeftTextView(String value) {
        this.leftTextView.setText(value);
        this.leftTextView.setVisibility(VISIBLE);
        this.leftImageView.setVisibility(GONE);
    }

    public void setRightTextView(String value) {
        this.rightTextView.setText(value);
        this.rightTextView.setVisibility(VISIBLE);
        this.rightImageView.setVisibility(GONE);
    }

    public void setLeftImageView(int imgRes) {
        this.leftImageView.setImageResource(imgRes);
        this.leftImageView.setVisibility(VISIBLE);
        this.leftTextView.setVisibility(GONE);
    }

    public void setRightImageView(int imgRes) {
        this.rightImageView.setImageResource(imgRes);
        this.rightImageView.setVisibility(VISIBLE);
        this.rightTextView.setVisibility(GONE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
