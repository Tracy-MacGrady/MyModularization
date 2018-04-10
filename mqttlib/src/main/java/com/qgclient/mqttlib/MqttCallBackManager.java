package com.qgclient.mqttlib;

import com.qgclient.mqttlib.enums.MqttConnectStatusEnum;
import com.qgclient.mqttlib.enums.MqttMessageSendStatusEnum;
import com.qgclient.mqttlib.interfaces.MqttClientConnectStatusInterface;
import com.qgclient.mqttlib.interfaces.MqttMessageInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijianjian on 2018/4/9.
 * MQTT 相关回调管理类（连接状态、消息状态）
 */

public class MqttCallBackManager {
    private List<MqttClientConnectStatusInterface> connectStatusListeners;
    private List<MqttMessageInterface> messageListeners;
    private static volatile MqttCallBackManager instance;

    private MqttCallBackManager() {
    }

    public static MqttCallBackManager getInstance() {
        if (instance == null) synchronized (MqttCallBackManager.class) {
            if (instance == null) instance = new MqttCallBackManager();
        }
        return instance;
    }

    public synchronized void addConnectStatusListener(MqttClientConnectStatusInterface listener) {
        if (connectStatusListeners == null) connectStatusListeners = new ArrayList<>();
        connectStatusListeners.add(listener);
    }

    public synchronized void removeConnectStatusListener(MqttClientConnectStatusInterface listener) {
        if (connectStatusListeners == null) return;
        connectStatusListeners.remove(listener);
    }

    public synchronized void addMessageListener(MqttMessageInterface listener) {
        if (messageListeners == null) messageListeners = new ArrayList<>();
        messageListeners.add(listener);
    }

    public synchronized void removeMessageListener(MqttMessageInterface listener) {
        if (messageListeners == null) return;
        messageListeners.remove(listener);
    }

    public synchronized void callbackMessage(String msgData, MqttMessageSendStatusEnum msgStatusEnum) {
        if (messageListeners == null || messageListeners.size() == 0) return;
        for (int i = 0; i < messageListeners.size(); i++) {
            switch (msgStatusEnum) {
                case STATUS_MSG_ARRIVED:
                    messageListeners.get(i).parseArrivedMsg(msgData);
                    break;
                case STATUS_SEND_SUCCESS:
                    messageListeners.get(i).msgSendSuccess();
                    break;
                case STATUS_SEND_FAILURE:
                    messageListeners.get(i).msgSendFailure();
                    break;
            }
        }
    }

    public synchronized void callbackConnectStatus(MqttConnectStatusEnum status) {
        if (connectStatusListeners == null || connectStatusListeners.size() == 0) return;
        for (int i = 0; i < connectStatusListeners.size(); i++)
            switch (status) {
                case STATUS_CONNECT_SUCCESS:
                    connectStatusListeners.get(i).mqttClientConnectSuccess();
                    break;
                case STATUS_RECONNECT_SUCCESS:
                    connectStatusListeners.get(i).mqttClientRepeatSuccess();
                    break;
                case STATUS_DISCONNECT_SUCCESS:
                    connectStatusListeners.get(i).mqttClientDisConnectSuccess();
                    break;
                case STATUS_CONNECT_FAILURE:
                    connectStatusListeners.get(i).mqttClientConnectFailure();
                    break;
                case STATUS_RECONNECT_FAILURE:
                    break;
                case STATUS_DISCONNECT_FAILURE:
                    connectStatusListeners.get(i).mqttClientDisConnectFailure();
                    break;
            }
    }

    public void removeAllConnectStatusListenr() {
        if (connectStatusListeners != null) connectStatusListeners.clear();
    }
}
