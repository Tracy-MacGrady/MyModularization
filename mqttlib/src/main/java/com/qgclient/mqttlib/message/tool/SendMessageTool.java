package com.qgclient.mqttlib.message.tool;

import android.content.Context;
import android.util.Log;

import com.qgclient.mqttlib.MqttCallBackManager;
import com.qgclient.mqttlib.MqttClientUtil;
import com.qgclient.mqttlib.constant.MqttConstant;
import com.qgclient.mqttlib.enums.MqttMessageSendStatusEnum;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Date;
import java.util.concurrent.RecursiveTask;

/**
 * Created by 李健健 on 2017/4/27.
 */

public class SendMessageTool {
    private static volatile SendMessageTool instance;

    private SendMessageTool() {
    }

    public static SendMessageTool getInstance() {
        if (instance == null) synchronized (SendMessageTool.class) {
            if (instance == null) instance = new SendMessageTool();
        }
        return instance;
    }

    public void sendToTopic(Context context, String messageValue, String... topics) {
        try {
            MqttMessage message = new MqttMessage(messageValue.getBytes());
            message.setQos(2);
            Log.e("sendToTopic", " pushed at " + new Date() + " " + messageValue);
            /**
             *消息发送到某个主题Topic，所有订阅这个Topic的设备都能收到这个消息。
             * 遵循MQTT的发布订阅规范，Topic也可以是多级Topic。此处设置了发送到二级topic
             */
            MqttAsyncClient client = MqttClientUtil.getInstance().getMqttClient();
            if (client != null) {
                if (!client.isConnected()) {
                    MqttClientUtil.getInstance().restartMqttClientConnect();
                }
                for (int i = 0; i < topics.length; i++) {
                    if (context != null) {
                        client.publish(topics[i], message, context, new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                try {
                                    String msg = new String(asyncActionToken.getResponse().getPayload());
                                    Log.e("sendToTopic", "msg send success msg data is===" + msg);
                                    MqttCallBackManager.getInstance().callbackMessage(msg, MqttMessageSendStatusEnum.STATUS_SEND_SUCCESS);
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                MqttCallBackManager.getInstance().callbackMessage(null, MqttMessageSendStatusEnum.STATUS_SEND_FAILURE);
                            }
                        });
                    } else client.publish(topics[i], message);
                }
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendToClient(Context context, String messageValue, String clientId) throws MqttException {
        String consumerClientId = MqttConstant.MQTT_GROUPID + "@@@ClientID_" + clientId;
        MqttMessage message = new MqttMessage(messageValue.getBytes());
        message.setQos(2);
        Log.e("sendToClient", " pushed at " + new Date() + " " + messageValue);
        /**
         * 如果发送P2P消息，二级Topic必须是“p2p”,三级topic是目标的ClientID
         * 此处设置的三级topic需要是接收方的ClientID
         */
        MqttAsyncClient client = MqttClientUtil.getInstance().getMqttClient();
        if (client != null) {
            if (!client.isConnected()) {
                MqttClientUtil.getInstance().restartMqttClientConnect();
            }
            String topic = MqttConstant.MQTT_TOPIC + "/p2p/" + consumerClientId;
            if (context != null) {
                client.publish(topic, message, context, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        try {
                            String msg = new String(asyncActionToken.getResponse().getPayload());
                            Log.e("sendToTopic", "msg send success msg data is===" + msg);
                            MqttCallBackManager.getInstance().callbackMessage(msg, MqttMessageSendStatusEnum.STATUS_SEND_SUCCESS);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        MqttCallBackManager.getInstance().callbackMessage(null, MqttMessageSendStatusEnum.STATUS_SEND_FAILURE);
                    }
                });
            } else client.publish(topic, message);
        }
    }
}
