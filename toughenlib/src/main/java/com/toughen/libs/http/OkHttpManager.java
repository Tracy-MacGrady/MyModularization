package com.toughen.libs.http;

import java.util.HashMap;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by lijianjian on 2018/4/8.
 */

public class OkHttpManager {
    private static volatile OkHttpManager instance;

    private OkHttpManager() {
    }

    public static OkHttpManager getInstance() {
        if (instance == null) synchronized (OkHttpManager.class) {
            if (instance == null) instance = new OkHttpManager();
        }
        return instance;
    }

    public void getRequest(String requestPath, HashMap<String, String> params, HashMap<String, String> headerMap, ResponseDataDispatchInterface dispatchListener) {
        OkHttpClient client = new OkHttpClient();
        Headers.Builder builder = new Headers.Builder();
        if (params != null && params.size() > 0) {
            StringBuffer paramsStr = new StringBuffer();
            Iterator<String> iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = params.get(key);
                paramsStr.append("&");
                paramsStr.append(key);
                paramsStr.append("=");
                paramsStr.append(value);
            }
            requestPath = requestPath + "?" + paramsStr.toString().trim().substring(1);
        }
        if (headerMap != null && headerMap.size() > 0) {
            Iterator<String> headerIterator = headerMap.keySet().iterator();
            while (headerIterator.hasNext()) {
                String key = headerIterator.next();
                String value = headerMap.get(key);
                builder.add(key, value);
            }
        }
        Request request = new Request.Builder().url(requestPath).headers(builder.build()).get().build();
        Call call = client.newCall(request);
        call.enqueue(new ToughenLibOKHttpCallback(dispatchListener));
    }

    public void postRequest(String requestPath, HashMap<String, String> params, HashMap<String, String> headerMap, ResponseDataDispatchInterface dispatchListener) {
        OkHttpClient client = new OkHttpClient();
        Headers.Builder builder = new Headers.Builder();
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            Iterator<String> iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = params.get(key);
                bodyBuilder.add(key, value);
            }
        }
        if (headerMap != null && headerMap.size() > 0) {
            Iterator<String> headerIterator = headerMap.keySet().iterator();
            while (headerIterator.hasNext()) {
                String key = headerIterator.next();
                String value = headerMap.get(key);
                builder.add(key, value);
            }
        }
        Request request = new Request.Builder().url(requestPath).headers(builder.build()).post(bodyBuilder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new ToughenLibOKHttpCallback(dispatchListener));
    }
}
