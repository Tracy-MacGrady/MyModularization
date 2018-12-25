package com.zx.toughen.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.toughen.libs.http.ResponseDataDispatchIml;
import com.toughen.libs.tools.ToastUtils;
import com.toughen.mqttutil.MqttCallBackManager;
import com.toughen.mqttutil.MqttClientManager;
import com.toughen.mqttutil.constant.MqttConstant;
import com.toughen.mqttutil.message.MqttMessageInterfaceIml;
import com.zx.toughen.R;
import com.zx.toughen.application.MyApplication;
import com.zx.toughen.base.BaseAppCompatActivity;
import com.zx.toughen.constant.IntentConstant;
import com.zx.toughen.entity.httpresponceentity.UserLoginResponceEntity;
import com.zx.toughen.fragment.MainFindFragment;
import com.zx.toughen.fragment.MainMessageFragment;
import com.zx.toughen.fragment.MainMineFragment;
import com.zx.toughen.http.HttpRequestTool;
import com.zx.toughen.userauth.UserAuth;

import java.util.List;
import java.util.Map;


public class MainActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private FrameLayout contentLayout;
    private LinearLayout messageView, findView, mineView;
    private MainMessageFragment mainMessageFragment;
    private MainFindFragment mainFindFragment;
    private MainMineFragment mainMineFragment;
    private FragmentManager fragmentManager;
    //Data
    private long lastPressedBack = 0;//返回键上次点击的时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        initIntentData();
        initView();
        setListener();
    }

    private MqttMessageInterfaceIml<String> messageListener = new MqttMessageInterfaceIml<String>() {
        @Override
        public void msgArrived(String msgModelInfo) {
            Log.e("message", "message arrived");
        }

        @Override
        public void msgSendSuccess(String msgModelInfo) {
            Log.e("message", "message send success");
        }

        @Override
        public void msgSendFailure(String msgModelInfo) {
            Log.e("message", "message send failure");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MqttCallBackManager.getInstance().removeMessageListener("", messageListener);
    }

    @Override
    public void initView() {
        contentLayout = findViewById(R.id.content_layout);
        messageView = findViewById(R.id.message_view);
        findView = findViewById(R.id.find_view);
        mineView = findViewById(R.id.mine_view);
        clickFindView();
    }

    @Override
    public void setListener() {
        messageView.setOnClickListener(this);
        findView.setOnClickListener(this);
        mineView.setOnClickListener(this);
    }

    private void initIntentData() {
        if (getIntent().getBooleanExtra(IntentConstant.MAIN_ISFROM_SPLASH_KEY, false)) {
            initUserinfo();
        }
    }

    /**
     * 获取用户信息
     */
    private void initUserinfo() {
        HttpRequestTool.getInstance().getUserinfo(this, new ResponseDataDispatchIml<UserLoginResponceEntity>() {
            @Override
            public void onSuccess(Map<String, List<String>> headers, UserLoginResponceEntity responseData) {
                UserAuth.update(responseData.getUserinfo());
                MyApplication.getInstance().initMqtt(String.valueOf(responseData.getUserinfo().getId()));
                MqttCallBackManager.getInstance().addMessageListener(String.format(MqttConstant.MQTT_TOPIC), messageListener);
                MqttClientManager.getInstance().startMqttClientConnect();
            }

            @Override
            public void onFailure(String failureMsg) {

            }
        });
    }


    private void clickFindView() {
        onClick(findView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_view:
                if (messageView.isSelected()) return;
                setBottomViewSelected(v);
                initSwitchUI(0);
                break;
            case R.id.find_view:
                if (findView.isSelected()) return;
                setBottomViewSelected(v);
                initSwitchUI(1);
                break;
            case R.id.mine_view:
                if (mineView.isSelected()) return;
                setBottomViewSelected(v);
                initSwitchUI(2);
                break;
        }
    }

    /**
     * 底部导航按钮点击后相应内容的更换
     */
    private void initSwitchUI(int caseNum) {
        contentLayout.removeAllViews();
        switch (caseNum) {
            case 0:
                if (mainMessageFragment == null)
                    mainMessageFragment = MainMessageFragment.newInstance();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_layout, mainMessageFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                if (mainFindFragment == null) mainFindFragment = MainFindFragment.newInstance();
                FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                fragmentTransaction1.replace(R.id.content_layout, mainFindFragment);
                fragmentTransaction1.commit();
                break;
            case 2:
                if (mainMineFragment == null) mainMineFragment = MainMineFragment.newInstance();
                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                fragmentTransaction2.replace(R.id.content_layout, mainMineFragment);
                fragmentTransaction2.commit();
                break;
        }
    }

    /**
     * 切换底部导航按钮的选中状态
     */
    private void setBottomViewSelected(View v) {
        messageView.setSelected(messageView == v);
        findView.setSelected(findView == v);
        mineView.setSelected(mineView == v);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPressedBack > 1500) {
            lastPressedBack = currentTime;
            ToastUtils.showShort(this, getString(R.string.click_again_to_back));
            return;
        }
        super.onBackPressed();
    }
}
