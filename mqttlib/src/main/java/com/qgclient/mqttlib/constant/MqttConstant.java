package com.qgclient.mqttlib.constant;

import android.text.TextUtils;

/**
 * Created by 李健健 on 2017/6/21.
 * Mqtt 消息规则相关
 */

public class MqttConstant {
    //mqtt相关参数
    public static String MQTT_BROKER = "tcp://mqtt-cn-0pp05ulgg0i.mqtt.aliyuncs.com:1883";
    public static String MQTT_TOPIC = "QingGuoSystem";
    public static String MQTT_GROUPID = "GID_QGDoubleTeacher";
    public static String MQTT_ACCESSKEY;//= "LTAIZ7Tin7wHeWVz";//"LTAIqEL2iCGLT8KY";
    public static String MQTT_SECRETKEY;//="r9vPWSIRBvz4iaqgvDVj0z5ChLfqax";//"Lmr4BCf5WMEbdj8ih6yljx3YMaq7OP";


    public static void init(String broker, String topic, String groupId) {
        MQTT_BROKER = broker;
        MQTT_TOPIC = topic;
        MQTT_GROUPID = groupId;
    }

    public static void init(String broker, String topic, String groupId, String accessKey, String secretKey) {
        MQTT_BROKER = broker;
        MQTT_TOPIC = topic;
        MQTT_GROUPID = groupId;
        MQTT_ACCESSKEY = accessKey;
        MQTT_SECRETKEY = secretKey;
    }

    public static boolean hasInit() {
        return TextUtils.isEmpty(MQTT_BROKER) || TextUtils.isEmpty(MQTT_TOPIC) || TextUtils.isEmpty(MQTT_GROUPID) || TextUtils.isEmpty(MQTT_ACCESSKEY) || TextUtils.isEmpty(MQTT_SECRETKEY);
    }
}
