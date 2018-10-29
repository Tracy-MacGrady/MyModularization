package com.zx.toughen.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


import com.zx.toughen.base.BaseAppCompatActivity;
import com.zx.toughen.R;
import com.zx.toughen.adapter.NewsAdapter;
import com.zx.toughen.entity.NewsEntity;
import com.zx.toughen.listenerinterface.MyLoadDataAnimRefreshInterface;
import com.zx.toughen.view.MyLoadDataAnimView;

/**
 * Created by 李健健 on 2017/4/18.
 */
public class NewsActivity extends BaseAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, MyLoadDataAnimRefreshInterface {
    private ImageView returnHomeView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        addContentView(new MyLoadDataAnimView(this, this), params);
        initView();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initView() {
        initTitleView();
        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        recyclerView = findViewById(R.id.recyclerview);
        manager = new LinearLayoutManager(this);
        adapter = new NewsAdapter(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initTitleView() {
        returnHomeView = findViewById(R.id.left_img_view);
        returnHomeView.setVisibility(View.VISIBLE);
        returnHomeView.setImageResource(R.drawable.selector_return_home);
        returnHomeView.setOnClickListener(this);
    }

    @Override
    public void setListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void initValue() {
        List<NewsEntity> list = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            list.add(new NewsEntity());
        }
        adapter.setList(list);
//        MyHttpRequestTool.getInstance().startRequest("", null, new MyRequestGetStringCallback<NewsEntity>() {
//            @Override
//            public void onSucess(NewsEntity data) {
//
//            }
//
//            @Override
//            public void onFailed() {
//
//            }
//        });
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_img_view:
                finish();
                break;
        }
    }

    @Override
    public void onLoadDataAgainRefresh() {

    }
}
