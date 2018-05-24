package com.zx.toughen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zx.toughen.R;
import com.zx.toughen.entity.MemorandumEntity;

import java.util.List;

/**
 * Created by 李健健 on 2017/4/10.
 */
public class MemorandumAdapter extends BaseAdapter {
    private Context context;
    private List<MemorandumEntity> list;
    private ViewHolder viewHolder;

    public MemorandumAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public MemorandumEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_memorandum, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.initData(list.get(position));
        return convertView;
    }

    public void setList(List<MemorandumEntity> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView titleView;
        private TextView dateView;

        public ViewHolder(View view) {
            this.titleView = (TextView) view.findViewById(R.id.title_view);
            this.dateView = (TextView) view.findViewById(R.id.date_view);
            view.setTag(this);
        }

        public void initData(MemorandumEntity entity) {
            if (entity == null) return;
            this.titleView.setText(entity.getTitleVal());
            this.dateView.setText(entity.getCreateTime());
        }
    }
}
