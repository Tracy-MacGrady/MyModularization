package com.toughen.libs.http;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    public void get(URL url) {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
