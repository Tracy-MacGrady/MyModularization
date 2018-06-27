package com.zx.toughen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zx.toughen.R;
import com.zx.toughen.activity.EventsActivity;
import com.zx.toughen.activity.FindManActivity;
import com.zx.toughen.activity.GetHelpActivity;
import com.zx.toughen.activity.HelpActivity;
import com.zx.toughen.activity.NewsActivity;
import com.zx.toughen.adapter.FindFragmentAdapter;
import com.zx.toughen.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 李健健 on 2017/2/27.
 */
public class MainFindFragment extends BaseFragment {
    public static MainFindFragment newInstance() {

        Bundle args = new Bundle();

        MainFindFragment fragment = new MainFindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FindFragmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_main_find_ui, container, false);
            initView();
            setListener();
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void initView() {
        recyclerView = view.findViewById(R.id.recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout);
    }

    @Override
    public void setListener() {

    }
}
