package com.toughen.libs.libtools;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class ToughenGson {
    public static <T> T fromGson(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
