package com.toughen.libs.base;

import android.support.v7.widget.RecyclerView;

import com.toughen.libs.interfaces.ItemClickListener;

public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected ItemClickListener listener;

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }
}
