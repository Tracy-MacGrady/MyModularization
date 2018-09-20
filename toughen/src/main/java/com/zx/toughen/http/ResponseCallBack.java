package com.zx.toughen.http;

import com.toughen.libs.http.ResponseDataDispatchIml;

import java.util.List;
import java.util.Map;

public class ResponseCallBack<T> extends ResponseDataDispatchIml<T> {

    @Override
    public void onSuccess(Map<String, List<String>> headers, T responseData) {

    }

    @Override
    public void onFailure(String failureMsg) {

    }
}
