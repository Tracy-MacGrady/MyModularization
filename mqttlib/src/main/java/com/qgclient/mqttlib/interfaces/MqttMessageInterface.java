package com.qgclient.mqttlib.interfaces;

/**
 * Created by 李健健 on 2017/6/22.
 */

public interface MqttMessageInterface<T> {
    void parseArrivedMsg(String msgData);

    void msgArrived(T msgData);

    void msgSendSuccess();

    void msgSendFailure();
}
