package com.zx.toughen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.toughen.libs.base.BaseRecyclerViewAdapter;
import com.zx.toughen.R;
import com.zx.toughen.activity.ChatRoomActivity;
import com.zx.toughen.constant.Constant;
import com.zx.toughen.entity.MessageItemInfo;
import com.zx.toughen.entity.MessageListEntity;

/**
 * Created by 李健健 on 2017/3/10.
 */
public class MessageListAdapter extends BaseRecyclerViewAdapter<MessageListAdapter.MyViewHolder> {
    private Context context;
    private List<MessageItemInfo> list;

    public void setList(List<MessageItemInfo> list1) {
        this.list = list1;
        this.notifyDataSetChanged();
    }

    public MessageListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.initValue(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        private ImageView avatarView;
        private TextView nickNameView, contentView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view.findViewById(R.id.message_item_view);
            avatarView = view.findViewById(R.id.message_item_avatar_view);
            nickNameView = view.findViewById(R.id.message_item_nickname_view);
            contentView = view.findViewById(R.id.message_item_content_view);
            itemView.setOnClickListener(this);
        }

        public void initValue(MessageItemInfo messageItemInfo) {
            itemView.setTag(messageItemInfo);
            nickNameView.setText(messageItemInfo.getUserInfo().getUsername());
        }

        @Override
        public void onClick(View v) {
            if (v.getTag() != null)
                try {
                    MessageItemInfo entity = (MessageItemInfo) v.getTag();
                    if (entity != null&&listener!=null) {
                        listener.onItemClick(entity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
