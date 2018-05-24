package com.zx.toughen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.toughen.libs.libtools.ToughenGson;
import com.toughen.libs.libtools.gson.ToughenTypeToken;
import com.zx.toughen.base.BaseActivity;
import com.toughen.libs.tools.SPUtils;
import com.zx.toughen.R;
import com.zx.toughen.constant.IntentConstant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.zx.toughen.adapter.MemorandumAdapter;
import com.zx.toughen.constant.Constant;
import com.zx.toughen.entity.MemorandumEntity;

public class MemorandumActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView titleView, rightView;
    private ImageView returnHomeView;
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
        initTitleView();
        listView = (ListView) findViewById(R.id.listview);
        adapter = new MemorandumAdapter(this);
        listView.setAdapter(adapter);
    }

    private void initTitleView() {
        titleView = (TextView) findViewById(R.id.title_textview);
        rightView = (TextView) findViewById(R.id.right_textview);
        returnHomeView = (ImageView) findViewById(R.id.left_img_view);
        returnHomeView.setImageResource(R.drawable.selector_return_home);
        titleView.setText(getString(R.string.title_memorandum_list));
        rightView.setText("添加");
        rightView.setVisibility(View.VISIBLE);
        rightView.setOnClickListener(this);
        returnHomeView.setOnClickListener(this);
    }

    @Override
    public void setListener() {
        listView.setOnItemClickListener(this);
    }

    public void initValue() {
        List<MemorandumEntity> list1 = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            list1.add(new MemorandumEntity("title" + i, "time" + System.currentTimeMillis(), "sdddddddddddfff", i % 2 == 0));
        }
        SPUtils.getInstance().saveString(this, Constant.MEMORANDUM_SP_FILE_NAME, ToughenGson.toJson(list1), Constant.MEMORANDUM_SP_KEY);

        String val = SPUtils.getInstance().getString(this, Constant.MEMORANDUM_SP_FILE_NAME, Constant.MEMORANDUM_SP_KEY);
        Type type = new ToughenTypeToken<List<MemorandumEntity>>() {
        }.getType();
        List<MemorandumEntity> list = ToughenGson.fromGson(val, type);
        if (list != null && list.size() > 0) {
            adapter.setList(list);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_img_view:
                finish();
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
