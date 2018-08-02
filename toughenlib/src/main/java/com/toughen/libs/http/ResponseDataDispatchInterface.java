package com.toughen.libs.http;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lijianjian on 2018/4/9.
 */

public interface ResponseDataDispatchInterface<T> {
    void onSuccess(Map<String, List<String>> headers, T responseData);

    void onFailure(String failureMsg);

    void parseResponseData(Response response) throws IOException;
}
