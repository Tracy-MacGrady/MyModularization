package com.zx.toughen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.toughen.entity.NewsEntity;

import java.util.List;

import toughen.zx.com.R;
import com.zx.toughen.activity.NewsInfoActivity;

/**
 * Created by 李健健 on 2017/4/18.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private Context context;
    private List<NewsEntity> list;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<NewsEntity> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.initData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view.findViewById(R.id.item_view);
            itemView.setOnClickListener(this);
        }

        public void initData(NewsEntity entity) {
            if (entity == null) return;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_view:
                    context.startActivity(new Intent(context, NewsInfoActivity.class));
                    break;
            }
        }
    }
}
