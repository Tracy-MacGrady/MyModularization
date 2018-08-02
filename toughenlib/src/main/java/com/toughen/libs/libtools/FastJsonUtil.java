package com.toughen.libs.libtools;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class FastJsonUtil {
    public static <T> T JsonStr2Object(String jsonStr, Type type) {
        T t = JSONObject.parseObject(jsonStr, type);
        return t;
    }

    public static <T> T JsonStr2Object(String jsonStr, Class<T> tClass) {
        T t = JSONObject.parseObject(jsonStr, tClass);
        return t;
    }

    public static <T> List<T> JsonStr2List(String jsonStr, Class<T> tClass) {
        return JSONObject.parseArray(jsonStr, tClass);
    }

    public static <T> String Object2JsonString(T obj) {
        return JSONObject.toJSONString(obj);
    }
}
