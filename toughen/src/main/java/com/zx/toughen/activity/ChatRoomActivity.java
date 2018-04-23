package com.zx.toughen.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.zx.toughen.constant.Constant;
import com.zx.toughen.entity.MessageEntity;
import com.zx.toughen.base.BaseActivity;
import com.zx.toughen.R;
import com.zx.toughen.view.MyTitleBarView;

public class ChatRoomActivity extends BaseActivity implements View.OnClickListener {
    //view
    private MyTitleBarView titleBarView;
    private EditText sendValueView;
    private View sendView;
    //data
    private MessageEntity messageEntity;
    private String sendValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        setListener();
        initValue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {
        titleBarView = (MyTitleBarView) findViewById(R.id.titlebar_view);
        sendValueView = (EditText) findViewById(R.id.content_view);
        sendView = findViewById(R.id.send_view);
    }

    @Override
    public void setListener() {
        sendView.setOnClickListener(this);
    }

    public void initValue() {
        getIntentValue();
    }

    private void getIntentValue() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null)
                messageEntity = (MessageEntity) bundle.getSerializable(Constant.INTENT_MESSAGE_ITEM_KEY);
            if (messageEntity != null && messageEntity.getUserInfo() != null)
                titleBarView.setTitleViewValue(messageEntity.getUserInfo().getUsername());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_view:
                if (checkSendContentValue()) sendChatMessage();
                break;
        }
    }

    private void sendChatMessage() {

    }

    private boolean checkSendContentValue() {
        return true;
    }

}
