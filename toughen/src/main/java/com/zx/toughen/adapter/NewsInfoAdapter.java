package com.zx.toughen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.toughen.R;
import com.zx.toughen.entity.CommentEntity;
import com.zx.toughen.entity.NewsEntity;

import java.util.List;


/**
 * Created by 李健健 on 2017/4/19.
 */
public class NewsInfoAdapter extends RecyclerView.Adapter {
    private final static int TYPE_NEWS = 0, TYPE_COMMENT = 1;
    private Context context;
    private NewsEntity entity;
    private List<CommentEntity> list;

    public NewsInfoAdapter(Context context) {
        this.context = context;
    }

    public void setEntity(NewsEntity entity) {
        this.entity = entity;
        this.notifyItemChanged(0);
    }

    public void setList(List<CommentEntity> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void setVlaue(NewsEntity entity, List<CommentEntity> list) {
        this.entity = entity;
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_NEWS;
        return TYPE_COMMENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_NEWS) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_comment_top, parent, false);
            return new NewsViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_comment, parent, false);
            return new CommentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((NewsViewHolder) holder).initData(entity);
        } else {
            ((CommentViewHolder) holder).initData(list.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 1 : list.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        public NewsViewHolder(View itemView) {
            super(itemView);
        }

        public void initData(NewsEntity entity) {
            if (entity == null) return;
        }
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        public CommentViewHolder(View itemView) {
            super(itemView);
        }

        public void initData(CommentEntity entity) {
            if (entity == null) return;
        }
    }
}

