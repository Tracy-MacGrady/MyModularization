package com.zx.toughen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.toughen.libs.libtools.FastJsonUtil;
import com.zx.toughen.base.BaseActivity;
import com.toughen.libs.tools.SPUtils;
import com.zx.toughen.R;
import com.zx.toughen.constant.IntentConstant;

import java.util.ArrayList;
import java.util.List;

import com.zx.toughen.adapter.MemorandumAdapter;
import com.zx.toughen.constant.Constant;
import com.zx.toughen.entity.MemorandumEntity;
import com.zx.toughen.listenerinterface.MyTitleBarViewListenerInterface;
import com.zx.toughen.view.MyTitleBarView;

public class MemorandumActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private MyTitleBarView titleBarView;
    private ListView listView;
    private MemorandumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorandum);
        initView();
        setListener();
        initValue();
    }

    @Override
    public void initView() {
        titleBarView = findViewById(R.id.titlebar_view);
        listView = findViewById(R.id.listview);
        adapter = new MemorandumAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        titleBarView.setListener(titleBarViewListener);
        listView.setOnItemClickListener(this);
    }
    private MyTitleBarViewListenerInterface titleBarViewListener=new MyTitleBarViewListenerInterface() {
        @Override
        public void clickLeft() {
            finish();
        }

        @Override
        public void clickTitle() {

        }

        @Override
        public void clickRight() {

        }
    };

    public void initValue() {
        List<MemorandumEntity> list1 = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            list1.add(new MemorandumEntity("title" + i, "time" + System.currentTimeMillis(), "sdddddddddddfff", i % 2 == 0));
        }
        SPUtils.getInstance().saveString(this, Constant.MEMORANDUM_SP_FILE_NAME, FastJsonUtil.Object2JsonString(list1), Constant.MEMORANDUM_SP_KEY);

        String val = SPUtils.getInstance().getString(this, Constant.MEMORANDUM_SP_FILE_NAME, Constant.MEMORANDUM_SP_KEY);
        List<MemorandumEntity> list = FastJsonUtil.JsonStr2List(val, MemorandumEntity.class);
        if (list != null && list.size() > 0) {
            adapter.setList(list);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_img_view:
                break;
            case R.id.right_textview:

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MemorandumEntity entity = adapter.getItem(position);
        if (entity == null) return;
        startActivity(new Intent(this, MemorandumInfoActivity.class).putExtra(IntentConstant.MEMORANDUM_ENTITY_KEY, entity));
    }
}
