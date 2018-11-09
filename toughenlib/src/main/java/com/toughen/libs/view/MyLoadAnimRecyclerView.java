package com.toughen.libs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.toughen.libs.R;

public class MyLoadAnimRecyclerView extends FrameLayout {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MyLoadDataAnimView loadDataAnimView;
    private MyEmptyView emptyView;
    private boolean canShowLoadAnim = true, canShowEmpty = true;

    public MyLoadAnimRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLoadAnimRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyLoadAnimRecyclerView);
        loadDataAnimView.setViewBgColor(array.getColor(R.styleable.MyLoadAnimRecyclerView_anim_bg, context.getResources().getColor(R.color.text_color)));
        loadDataAnimView.setTextColor(array.getColor(R.styleable.MyLoadAnimRecyclerView_anim_text_color, context.getResources().getColor(R.color.text_color)));
        canShowLoadAnim = array.getBoolean(R.styleable.MyLoadAnimRecyclerView_anim_can_show, false);
        emptyView.setTextColor(array.getColor(R.styleable.MyLoadAnimRecyclerView_empty_text_color, context.getResources().getColor(R.color.text_color)));
        emptyView.setImageViewSrc(array.getResourceId(R.styleable.MyLoadAnimRecyclerView_empty_img_src, R.drawable.img_empty));
        canShowEmpty = array.getBoolean(R.styleable.MyLoadAnimRecyclerView_empty_can_show, true);
        array.recycle();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.loadanim_recyclerview, this);
        recyclerView = findViewById(R.id.my_recyclerview);
        refreshLayout = findViewById(R.id.swiperefreshlayout);
        loadDataAnimView = findViewById(R.id.load_anim_view);
        emptyView = findViewById(R.id.empty_view);
        emptyView.setOnClickListener(emptyViewClickListener);
    }

    private OnClickListener emptyViewClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            showLoadAnim();
        }
    };

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        refreshLayout.setOnRefreshListener(listener);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        recyclerView.addOnScrollListener(listener);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        recyclerView.setLayoutManager(manager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void showLoadAnim() {
        loadDataAnimView.setVisibility(canShowLoadAnim ? VISIBLE : GONE);
        refreshLayout.setVisibility(GONE);
        emptyView.setVisibility(GONE);
    }

    public void showEmptyView() {
        if (emptyView.getVisibility() == VISIBLE) return;
        emptyView.setVisibility(canShowEmpty ? VISIBLE : GONE);
        refreshLayout.setVisibility(GONE);
        loadDataAnimView.setVisibility(GONE);
    }

    public void showRecyclerView() {
        if (refreshLayout.getVisibility() == VISIBLE) return;
        refreshLayout.setVisibility(VISIBLE);
        emptyView.setVisibility(GONE);
        loadDataAnimView.setVisibility(GONE);
    }

    public void setRefreshing(boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
    }
}
