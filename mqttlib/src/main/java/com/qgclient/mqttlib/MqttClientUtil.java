package com.qgclient.mqttlib;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.qgclient.mqttlib.connectstatus.MqttConnectionStatusReceiver;
import com.qgclient.mqttlib.connectstatus.MqttConnectionStatusService;
import com.qgclient.mqttlib.enums.MqttConnectStatusEnum;
import com.qgclient.mqttlib.enums.MqttMessageSendStatusEnum;
import com.qgclient.mqttlib.getconnect.GetMqttClientConnect;
import com.qgclient.mqttlib.message.tool.GetMessageTool;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Administrator on 2017/12/26.
 */

public class MqttClientUtil {
    private Context context;
    private String clientId;
    private boolean canRepeatConnect = false;
    private MqttCallbackExtended mqttCallback;
    private MqttConnectionStatusReceiver myMqttConnReceiver;
    private static MqttClientUtil instance;

    private MqttClientUtil() {
    }

    public static MqttClientUtil getInstance() {
        if (instance == null) synchronized (MqttClientUtil.class) {
            if (instance == null) instance = new MqttClientUtil();
        }
        return instance;
    }

    /**
     * 初始化
     */
    public synchronized void init(Context context, String clientId) {
        this.context = context;
        this.clientId = clientId;
        initMqttCallBack();
        initMqttConnReceiver();
        GetMqttClientConnect.getInstance().init(clientId, mqttCallback, mqttStatusHandler, context);
    }

    /**
     * 是否处于连接状态
     */
    public synchronized boolean isOnLine() {
        if (getMqttClient() == null) return false;
        return getMqttClient().isConnected();
    }

    /**
     * 连接MQTT client
     */
    public synchronized void startMqttClientConnect() {
        GetMqttClientConnect.getInstance().startConnect();
    }

    /**
     * 重连MQTT client
     */
    public synchronized void restartMqttClientConnect() {
        if (canRepeatConnect) {
            GetMqttClientConnect.getInstance().setConnectNum(0);
            GetMqttClientConnect.getInstance().startConnect();
        }
    }

    /**
     * 断开MQTT client 连接
     */
    public synchronized void disconnectMqttClientConnect() {
        GetMqttClientConnect.getInstance().disConnect();
    }

    ;

    public synchronized void clearMqttListener() {
        MqttCallBackManager.getInstance().removeAllConnectStatusListenr();
        if (context != null && myMqttConnReceiver != null)
            LocalBroadcastManager.getInstance(context).unregisterReceiver(myMqttConnReceiver);
        mqttCallback = null;
        canRepeatConnect = false;
        myMqttConnReceiver = null;
    }

    public MqttAsyncClient getMqttClient() {
        return GetMqttClientConnect.getInstance().getMqttClient();
    }

    public String getClientId() {
        return clientId;
    }

    /**
     * 监听MQTT 连接状态的广播
     */
    private void initMqttConnReceiver() {
        if (myMqttConnReceiver == null) myMqttConnReceiver = new MqttConnectionStatusReceiver() {
            @Override
            public void toRepeatConnect() {
                restartMqttClientConnect();
            }
        };
    }

    /**
     * 实例化MQTT client的回调
     */
    private void initMqttCallBack() {
        if (mqttCallback == null) mqttCallback = new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                //TODO 连接成功
                Log.e("MqttClientUtil", "connectComplete ======" + reconnect);
                if (reconnect || canRepeatConnect) {
                    MqttCallBackManager.getInstance().callbackConnectStatus(MqttConnectStatusEnum.STATUS_RECONNECT_SUCCESS);
                } else {
                    canRepeatConnect = true;
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(MqttConnectionStatusService.REPEAT_ACTION);
                    LocalBroadcastManager.getInstance(context).registerReceiver(myMqttConnReceiver, filter);
                    MqttCallBackManager.getInstance().callbackConnectStatus(MqttConnectStatusEnum.STATUS_CONNECT_SUCCESS);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                //TODO 连接已经断开
                Log.e("MqttClientUtil", "connection lost======");
                GetMessageTool.clearAllTopic();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                //TODO 消息到达
                Log.e("MqttClientUtil", "msg arrived===" + message);
                Message msg = Message.obtain();
                msg.obj = new String(message.getPayload());
                msg.what = 1001;
                mqttHandler.sendMessage(msg);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                try {
                    Log.e("MqttClientUtil", "msg delivery===" + token.getMessage());
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Handler mqttStatusHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100://todo 连接 mqtt连接 成功
                    MqttCallBackManager.getInstance().callbackConnectStatus(MqttConnectStatusEnum.STATUS_CONNECT_SUCCESS);
                    break;
                case 102://todo 重连 mqtt连接 成功
                    MqttCallBackManager.getInstance().callbackConnectStatus(MqttConnectStatusEnum.STATUS_RECONNECT_SUCCESS);
                    break;
                case 101://todo 连接 mqtt连接 失败
                    MqttCallBackManager.getInstance().callbackConnectStatus(MqttConnectStatusEnum.STATUS_CONNECT_FAILURE);
                    break;
                case 200://todo 断开 mqtt连接 成功
                    MqttCallBackManager.getInstance().callbackConnectStatus(MqttConnectStatusEnum.STATUS_DISCONNECT_SUCCESS);
                    clearMqttListener();
                    break;
                case 201://todo 断开 mqtt连接 失败
                    MqttCallBackManager.getInstance().callbackConnectStatus(MqttConnectStatusEnum.STATUS_DISCONNECT_FAILURE);
                    clearMqttListener();
                    break;
            }
        }
    };
    /**
     * 消息分发
     */
    private Handler mqttHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (msg) {
                //TODO 消息到达后的操作
                String messageVal = (String) msg.obj;
                MqttCallBackManager.getInstance().callbackMessage(messageVal, MqttMessageSendStatusEnum.STATUS_MSG_ARRIVED);
            }
        }
    };
}
