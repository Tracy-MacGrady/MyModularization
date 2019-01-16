package com.toughen.mqttutil.message.tool;


import com.toughen.mqttutil.MqttClientManager;
import com.toughen.mqttutil.constant.MqttConstant;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李健健 on 2017/4/27.
 */

public class GetMessageTool {
    private static GetMessageTool instance = new GetMessageTool();
    private List<String> subscribeTopicList = new ArrayList<>();

    private GetMessageTool() {
    }

    public static GetMessageTool getInstance() {
        return instance;
    }

    public void subscribeTopic(int qos, String... secondTopics) {
        try {
            for (int i = 0; i < secondTopics.length; i++) {
                if (!subscribeTopicList.contains(secondTopics[i]))
                    subscribeTopicList.add(secondTopics[i]);
                String topicFilter = MqttConstant.MQTT_TOPIC + "/" + secondTopics[i];
                MqttAsyncClient client = MqttClientManager.getInstance().getMqttClient();
                if (client != null && client.isConnected()) {
                    client.subscribe(topicFilter, qos);
                }
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void unsubscribeTopic(String... topics) {
        try {
            if (subscribeTopicList.size() <= 0) return;
            for (int i = 0; i < topics.length; i++) {
                subscribeTopicList.remove(topics[i]);
            }
            MqttAsyncClient client = MqttClientManager.getInstance().getMqttClient();
            if (client != null && client.isConnected()) {
                client.unsubscribe(topics);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAllTopic() {
        subscribeTopicList.clear();
    }
}
