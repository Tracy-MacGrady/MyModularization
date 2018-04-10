package com.toughen.libs.http;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by lijianjian on 2018/4/9.
 */

public abstract class ResponseDataDispatchIml<T> implements ResponseDataDispatchInterface<T> {

    @Override
    public void parseResponseData(Response response) {
        T t = null;
        if (response.message() != null)
            t = new Gson().fromJson(response.message().toString(), getType());
        onSuccess(response.headers(), t);
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
