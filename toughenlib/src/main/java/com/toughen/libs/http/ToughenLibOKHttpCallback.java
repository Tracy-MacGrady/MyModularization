package com.toughen.libs.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ToughenLibOKHttpCallback implements Callback {
    private ResponseDataDispatchInterface dispatchInterface;

    public ToughenLibOKHttpCallback(ResponseDataDispatchInterface dispatchInterface) {
        this.dispatchInterface = dispatchInterface;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (dispatchInterface != null) dispatchInterface.onRequestFailure(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (dispatchInterface != null) dispatchInterface.parseResponseData(response);
    }
}
