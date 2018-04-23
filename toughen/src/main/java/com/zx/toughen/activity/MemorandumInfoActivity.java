package com.zx.toughen.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.toughen.base.BaseActivity;
import com.zx.toughen.R;
import com.zx.toughen.listenerinterface.MemorandumAuthListenerInterface;

import java.io.Serializable;

import com.zx.toughen.constant.IntentConstant;
import com.zx.toughen.dialog.MemorandumAuthDialog;
import com.zx.toughen.entity.MemorandumEntity;


/**
 * Created by 李健健 on 2017/4/12.
 */
public class MemorandumInfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleView;
    private ImageView returnHomeView;
    private TextView memorandumTitleView, memorandumCreateTimeView, memorandumContentView;
    private MemorandumEntity entity;
    private MemorandumAuthDialog authDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorandum_info);
        initView();
        setListener();
        initValue();
    }

    @Override
    public void initView() {
        initTitleView();
        memorandumTitleView = (TextView) findViewById(R.id.memorandum_title_view);
        memorandumCreateTimeView = (TextView) findViewById(R.id.memorandum_createtime_view);
        memorandumContentView = (TextView) findViewById(R.id.memorandum_content_view);
    }

    private void initTitleView() {
        titleView = (TextView) findViewById(R.id.title_textview);
        titleView.setText(getString(R.string.memorandum_info_title));
        returnHomeView = (ImageView) findViewById(R.id.left_img_view);
        returnHomeView.setImageResource(R.drawable.selector_return_home);
    }

    @Override
    public void setListener() {
        returnHomeView.setOnClickListener(this);
    }

    public void initValue() {
        getIntentValue();
        if (entity == null) return;
        if (entity.isPassword()) {
            if (authDialog == null) {
                authDialog = new MemorandumAuthDialog(this);
                authDialog.setMemorandumListener(memorandumListener);
            }
            authDialog.show();
        } else {
            memorandumContentView.setText(entity.getContentValue());
        }
        memorandumTitleView.setText(entity.getTitleVal());
        memorandumCreateTimeView.setText(entity.getCreateTime());
    }

    private MemorandumAuthListenerInterface memorandumListener = new MemorandumAuthListenerInterface() {
        @Override
        public void cancelAuth() {

        }

        @Override
        public void authRight() {
            memorandumContentView.setText(entity.getContentValue());
        }
    };

    private void getIntentValue() {
        Serializable serializable = getIntent().getSerializableExtra(IntentConstant.MEMORANDUM_ENTITY_KEY);
        if (serializable == null) return;
        entity = (MemorandumEntity) serializable;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_img_view:
                finish();
                break;
        }
    }
}
