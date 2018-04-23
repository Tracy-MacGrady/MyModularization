package com.zx.toughen.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.toughen.base.BaseActivity;
import com.zx.toughen.R;
import com.zx.toughen.entity.CommentEntity;
import com.zx.toughen.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;

import com.zx.toughen.adapter.NewsInfoAdapter;


/**
 * Created by 李健健 on 2017/4/19.
 */

public class NewsInfoActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private TextView titleView;
    private ImageView returnView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private NewsInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        initView();
        setListener();
        initValue();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initView() {
        initTitleView();
        this.swipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swiperefreshlayout);
        this.recyclerView = (RecyclerView) this.findViewById(R.id.recyclerview);
        this.manager = new LinearLayoutManager(this);
        this.adapter = new NewsInfoAdapter(this);
        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setAdapter(this.adapter);
    }

    private void initTitleView() {
        this.titleView = (TextView) this.findViewById(R.id.title_textview);
        this.titleView.setText(getString(R.string.news_info));
        this.returnView = (ImageView) this.findViewById(R.id.left_img_view);
        this.returnView.setImageResource(R.drawable.selector_return_home);
    }

    @Override
    public void setListener() {
        this.returnView.setOnClickListener(this);
        this.swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void initValue() {
        List<CommentEntity> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new CommentEntity());
        }
        this.adapter.setVlaue(new NewsEntity(), list);
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
    public void onRefresh() {
        this.swipeRefreshLayout.setRefreshing(false);
    }
}
