package com.toughen.libs.http;

import android.content.Context;

import com.toughen.libs.tools.LogUtils;
import com.toughen.libs.tools.NetworkUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by lijianjian on 2018/4/8.
 */

public class OkHttpManager {
    private static final long CONNECT_TIME_OUT = 5;//连接超时限定值
    private static final long READ_TIME_OUT = 5;//请求超时限定值
    private static volatile OkHttpManager instance;

    private OkHttpManager() {
    }

    public static OkHttpManager getInstance() {
        if (instance == null) synchronized (OkHttpManager.class) {
            if (instance == null) instance = new OkHttpManager();
        }
        return instance;
    }

    public void getRequest(Context context, String requestPath, HashMap<String, String> params, HashMap<String, String> headerMap, CookieJar cookieJar, ResponseDataDispatchInterface dispatchListener) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS).readTimeout(READ_TIME_OUT, TimeUnit.SECONDS).cookieJar(cookieJar).build();
        initGetRequest(context, requestPath, params, headerMap, dispatchListener, client);
    }

    public void getRequest(Context context, String requestPath, HashMap<String, String> params, HashMap<String, String> headerMap, ResponseDataDispatchInterface dispatchListener) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS).readTimeout(READ_TIME_OUT, TimeUnit.SECONDS).build();
        initGetRequest(context, requestPath, params, headerMap, dispatchListener, client);
    }

    private void initGetRequest(Context context, String requestPath, HashMap<String, String> params, HashMap<String, String> headerMap, ResponseDataDispatchInterface dispatchListener, OkHttpClient client) {
        if (!NetworkUtils.getInstance().checkNet(context)) {
            if (dispatchListener != null) dispatchListener.onFailure("请检查您的网络！");
            return;
        }
        Headers.Builder headerBuilder = new Headers.Builder();
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
                headerBuilder.add(key, value);
            }
        }
        LogUtils.e("REQUEST_PATH", requestPath);
        Request request = new Request.Builder().url(requestPath).headers(headerBuilder.build()).get().build();
        Call call = client.newCall(request);
        call.enqueue(new ToughenLibOKHttpCallback(dispatchListener));
    }

    public void postRequest(Context context, String requestPath, HashMap<String, String> params, HashMap<String, String> headerMap, CookieJar cookieJar, ResponseDataDispatchInterface dispatchListener) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS).readTimeout(READ_TIME_OUT, TimeUnit.SECONDS).cookieJar(cookieJar).build();
        initPostRequest(context, requestPath, params, headerMap, dispatchListener, client);
    }

    public void postRequest(Context context, String requestPath, HashMap<String, String> params, HashMap<String, String> headerMap, ResponseDataDispatchInterface dispatchListener) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS).readTimeout(READ_TIME_OUT, TimeUnit.SECONDS).build();
        initPostRequest(context, requestPath, params, headerMap, dispatchListener, client);
    }

    private void initPostRequest(Context context, String requestPath, HashMap<String, String> params, HashMap<String, String> headerMap, ResponseDataDispatchInterface dispatchListener, OkHttpClient client) {
        if (!NetworkUtils.getInstance().checkNet(context)) {
            if (dispatchListener != null) dispatchListener.onFailure("请检查您的网络！");
            return;
        }
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
        LogUtils.e("REQUEST_PATH", requestPath);
        Request request = new Request.Builder().url(requestPath).headers(builder.build()).post(bodyBuilder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new ToughenLibOKHttpCallback(dispatchListener));
    }
}
