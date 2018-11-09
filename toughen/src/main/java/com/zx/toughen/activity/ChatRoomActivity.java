package com.zx.toughen.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.toughen.libs.libtools.FastJsonUtil;
import com.toughen.mqttutil.MqttCallBackManager;
import com.toughen.mqttutil.message.MqttMessageInterfaceIml;
import com.toughen.mqttutil.message.tool.SendMessageTool;
import com.zx.toughen.R;
import com.zx.toughen.application.MyApplication;
import com.zx.toughen.base.BaseAppCompatActivity;
import com.zx.toughen.constant.Constant;
import com.zx.toughen.entity.MessageInfo;
import com.zx.toughen.entity.MessageItemInfo;
import com.zx.toughen.entity.UserInfo;
import com.zx.toughen.view.MyTitleBarView;

public class ChatRoomActivity extends BaseAppCompatActivity implements View.OnClickListener {
    //view
    private MyTitleBarView titleBarView;
    private EditText sendValueView;
    private RecyclerView recyclerView;
    private View sendView;

    private UserInfo chatUserInfo;
    private UserInfo myselfUserinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        myselfUserinfo = MyApplication.getInstance().getUserInfo();
        MqttCallBackManager.getInstance().addMessageListener("p2p/" + myselfUserinfo.getId(), messageListener);
        initView();
        setListener();
        initValue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MqttCallBackManager.getInstance().removeMessageListener("p2p/" + myselfUserinfo.getId(), messageListener);
    }

    @Override
    public void initView() {
        titleBarView = findViewById(R.id.titlebar_view);
        sendValueView = findViewById(R.id.content_view);
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
            if (bundle != null) {
                MessageItemInfo messageItemInfo = (MessageItemInfo) bundle.getSerializable(Constant.INTENT_MESSAGE_ITEM_KEY);
                if (messageItemInfo != null) chatUserInfo = messageItemInfo.getUserInfo();
            }
            if (chatUserInfo != null)
                titleBarView.setTitleViewValue(chatUserInfo.getUsername());
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
        String sendValue = getMessageInfo();
        SendMessageTool.getInstance().sendToClient(this, sendValue, String.valueOf(MyApplication.getInstance().getUserInfo().getId()));
    }

    @NonNull
    private String getMessageInfo() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        String sendValue = sendValueView.getText().toString();
        if (userInfo != null) {
            MessageInfo msg = new MessageInfo();
            msg.setUid(String.valueOf(userInfo.getId()));
            msg.setUserName(userInfo.getUsername());
            msg.setMsgValue(sendValue);
            sendValue = FastJsonUtil.Object2JsonString(msg);
        }
        return sendValue;
    }

    private boolean checkSendContentValue() {
        return !sendValueView.getText().toString().isEmpty();
    }

    private MqttMessageInterfaceIml<MessageInfo> messageListener = new MqttMessageInterfaceIml<MessageInfo>() {
        @Override
        public void msgArrived(MessageInfo msgModelInfo) {

        }

        @Override
        public void msgSendSuccess(MessageInfo msgModelInfo) {

        }

        @Override
        public void msgSendFailure(MessageInfo msgModelInfo) {

        }
    };
}
