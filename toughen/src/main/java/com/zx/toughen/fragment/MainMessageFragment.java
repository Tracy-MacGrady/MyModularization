package com.zx.toughen.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toughen.libs.interfaces.ItemClickListener;
import com.toughen.libs.view.MyLoadAnimRecyclerView;
import com.zx.toughen.R;
import com.zx.toughen.activity.ChatRoomActivity;
import com.zx.toughen.adapter.MessageListAdapter;
import com.zx.toughen.base.BaseFragment;
import com.zx.toughen.constant.Constant;
import com.zx.toughen.entity.MessageInfo;
import com.zx.toughen.entity.MessageItemInfo;
import com.zx.toughen.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李健健 on 2017/2/23.
 */

public class MainMessageFragment extends BaseFragment {
    private MyLoadAnimRecyclerView recyclerView;
    private LinearLayoutManager manager;
    private MessageListAdapter adapter;

    public static MainMessageFragment newInstance() {
        Bundle args = new Bundle();
        MainMessageFragment fragment = new MainMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_main_message_ui, container, false);
            initView();
            setListener();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasInitData) return;
        hasInitData = true;
        initData();
    }

    private void initData() {
        MessageItemInfo itemInfo = new MessageItemInfo();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(123456);
        userInfo.setUsername("lisi123456");
        itemInfo.setUserInfo(userInfo);
        MessageInfo msgInfo = new MessageInfo();
        msgInfo.setMsgValue("fflkdsajfldsa");
        msgInfo.setUserName("lisi123456");
        msgInfo.setUid(String.valueOf(123456));
        itemInfo.setMsgInfo(msgInfo);
        List<MessageItemInfo> list = new ArrayList<>();
        list.add(itemInfo);
        adapter.setList(list);
    }

    @Override
    public void initView() {
        recyclerView = view.findViewById(R.id.recyclerview);
        manager = new LinearLayoutManager(getContext());
        adapter = new MessageListAdapter(getContext());
        adapter.setListener(itemClickListener);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        recyclerView.setOnRefreshListener(refreshListener);
        recyclerView.addOnScrollListener(scrollListener);
    }

    private ItemClickListener itemClickListener = new ItemClickListener<MessageItemInfo>() {
        @Override
        public void onItemClick(MessageItemInfo info) {
            startActivity(new Intent(getContext(), ChatRoomActivity.class).putExtra(Constant.INTENT_MESSAGE_ITEM_KEY, info));

        }
    };
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onRefresh() {
            recyclerView.setRefreshing(false);
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
