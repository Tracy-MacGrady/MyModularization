package com.zx.toughen.fragment;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toughen.libs.tools.AppUtils;
import com.zx.toughen.R;
import com.zx.toughen.adapter.NewsAdapter;
import com.zx.toughen.base.BaseFragment;
import com.zx.toughen.view.MySearchEditTextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 李健健 on 2017/2/27.
 */
public class MainFindFragment extends BaseFragment implements MySearchEditTextView.OnSearchListener {
    public static MainFindFragment newInstance() {

        Bundle args = new Bundle();

        MainFindFragment fragment = new MainFindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private MySearchEditTextView searchEdit;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsAdapter adapter;

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
    public void onDetach() {
        super.onDetach();
        if (swipeRefreshLayout != null) swipeRefreshLayout.requestFocus();
    }

    @Override
    public void initView() {
        searchEdit = view.findViewById(R.id.search_edittext);
        recyclerView = view.findViewById(R.id.recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout);
        adapter = new NewsAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        searchEdit.setSearchListener(this);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        recyclerView.addOnScrollListener(recyclerViewScrollListener);
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

        }
    };
    private RecyclerView.OnScrollListener recyclerViewScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    @Override
    public void toSearch(String searchTipValue) {
        Log.e("bbbbbbbb" + System.currentTimeMillis(), "dffadfsfadsf" + searchTipValue);
        AppUtils.getInstance().hideKeyboard(getActivity(), searchEdit);
        swipeRefreshLayout.requestFocus();
    }
}
