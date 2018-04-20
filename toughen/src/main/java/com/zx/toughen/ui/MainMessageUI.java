package com.zx.toughen.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import toughen.zx.com.R;
import com.zx.toughen.adapter.MessageListAdapter;
import com.zx.toughen.entity.MessageEntity;
import com.zx.toughen.entity.UserInfo;

/**
 * Created by 李健健 on 2017/2/23.
 */

public class MainMessageUI extends FrameLayout {
    private View noneMessageTipView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private MessageListAdapter adapter;

    public MainMessageUI(Context context) {
        super(context);
        View.inflate(getContext(), R.layout.layout_main_message_ui, this);
        initView();
        setListener();
        getData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void getData() {
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            List<String> usernames = null;
            try {
                usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            return usernames;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            if (strings != null) initData(strings);
        }
    }

    private void initData(List<String> usernames) {
        List<MessageEntity> entityList = new ArrayList<>();
        for (int i = 0; i < usernames.size(); i++) {
            MessageEntity entity = new MessageEntity();
            entity.setUserInfo(new UserInfo(usernames.get(i)));
            entityList.add(entity);
        }
        adapter.setList(entityList);
    }

    private void initView() {
        noneMessageTipView = findViewById(R.id.none_message_tip_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        manager = new LinearLayoutManager(getContext());
        adapter = new MessageListAdapter(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        recyclerView.addOnScrollListener(scrollListener);
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onRefresh() {
            swipeRefreshLayout.setRefreshing(false);
        }
    };
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
}
