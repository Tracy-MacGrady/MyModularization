package com.zx.toughen.entity;

import com.toughen.mqttutil.constant.MqttConstantParamsEntity;

public class MyMqttParamsEntity extends MqttConstantParamsEntity {
    public MyMqttParamsEntity() {
        this.setMqttGroupid("lisi");
        this.setMqttBroker("tcp://16190c78z3.iask.in:59147");
        this.setMqttTopic("toughen");
        this.setUsername("admin");
        this.setPassword("password");
    }
}
