package com.zx.toughen.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zx.toughen.R;
import com.zx.toughen.activity.FindManActivity;
import com.zx.toughen.activity.HelpActivity;
import com.zx.toughen.activity.NewsActivity;

import java.util.ArrayList;
import java.util.List;


import com.zx.toughen.activity.EventsActivity;
import com.zx.toughen.activity.GetHelpActivity;


/**
 * Created by 李健健 on 2017/2/27.
 */
public class MainFindUI extends FrameLayout implements View.OnClickListener {

    public MainFindUI(Context context) {
        super(context);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.layout_main_find_ui, this);
        initView();
    }

    private void initView() {
//        bannerView = (MyBannerView) findViewById(R.id.banner_view);
        List<View> list = new ArrayList<>();
        int i = 0;
        while (i < 5) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.morning_beijing);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            list.add(imageView);
            i++;
        }
//        bannerView.setViewList(list);
        setListener();
    }

    private void setListener() {
    }

    @Override
    public void onClick(View v) {
    }
}
