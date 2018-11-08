package com.zx.toughen.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toughen.mqttutil.MqttCallBackManager;
import com.toughen.mqttutil.MqttClientManager;
import com.toughen.mqttutil.constant.MqttConstantParamsEntity;
import com.toughen.mqttutil.interfaces.MqttClientConnectStatusInterface;
import com.zx.toughen.R;
import com.zx.toughen.base.BaseFragment;

/**
 * Created by 李健健 on 2017/2/23.
 */

public class MainMessageFragment extends BaseFragment implements MqttClientConnectStatusInterface {

    public static MainMessageFragment newInstance() {
        Bundle args = new Bundle();
        MainMessageFragment fragment = new MainMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_main_message_ui, container, false);
            initView();
            setListener();
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void initView() {
        MqttConstantParamsEntity entity = new MqttConstantParamsEntity();
        entity.setMqttBroker("tcp://172.16.20.206:61613");
        entity.setMqttTopic("toughen");
        entity.setUsername("admin");
        entity.setPassword("password");
        entity.setMqttGroupid("group1");
        MqttClientManager.getInstance().init(getActivity().getApplicationContext(), "aaaaaa", entity);
        MqttCallBackManager.getInstance().addConnectStatusListener(this);
        MqttClientManager.getInstance().startMqttClientConnect();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void mqttClientConnectSuccess() {
        Log.e("fffffffffff", "mqttClientConnectSuccess");
    }

    @Override
    public void mqttClientRepeatSuccess() {
        Log.e("fffffffffff", "mqttClientRepeatSuccess");

    }

    @Override
    public void mqttClientConnectFailure() {
        Log.e("fffffffffff", "mqttClientConnectFailure");

    }

    @Override
    public void mqttClientDisConnectSuccess() {

        Log.e("fffffffffff", "mqttClientDisConnectSuccess");
    }

    @Override
    public void mqttClientDisConnectFailure() {

        Log.e("fffffffffff", "mqttClientDisConnectFailure");
    }
}
