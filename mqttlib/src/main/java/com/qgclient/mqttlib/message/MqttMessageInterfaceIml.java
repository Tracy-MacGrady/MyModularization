package com.qgclient.mqttlib.message;

import com.google.gson.Gson;
import com.qgclient.mqttlib.interfaces.MqttMessageInterface;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/12/26.
 */

public abstract class MqttMessageInterfaceIml<T> implements MqttMessageInterface<T> {

    @Override
    public void parseArrivedMsg(String msgData) {
        T t = new Gson().fromJson(msgData, getType());
        msgArrived(t);
    }

    /**
     * 获取当前的类型
     *
     * @return
     */
    private Type getType() {
        Type type = String.class;
        Type mySuperClass = this.getClass().getGenericSuperclass();
        if (mySuperClass instanceof ParameterizedType)
            type = ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
        return type;
    }
}
