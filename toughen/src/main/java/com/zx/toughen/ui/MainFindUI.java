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
        findViewById(R.id.news_view).setOnClickListener(this);
        findViewById(R.id.find_man_view).setOnClickListener(this);
        findViewById(R.id.events_view).setOnClickListener(this);
        findViewById(R.id.get_help_view).setOnClickListener(this);
        findViewById(R.id.help_view).setOnClickListener(this);
        findViewById(R.id.voice_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.news_view:
                getContext().startActivity(new Intent(getContext(), NewsActivity.class));
                break;
            case R.id.find_man_view:
                getContext().startActivity(new Intent(getContext(), FindManActivity.class));
                break;
            case R.id.events_view:
                getContext().startActivity(new Intent(getContext(), EventsActivity.class));
                break;
            case R.id.get_help_view:
                getContext().startActivity(new Intent(getContext(), GetHelpActivity.class));
                break;
            case R.id.help_view:
                getContext().startActivity(new Intent(getContext(), HelpActivity.class));
                break;
            case R.id.voice_view:
//                getContext().startActivity(new Intent(getContext(), VoiceCommandActivity.class));
                break;
        }
    }
}
