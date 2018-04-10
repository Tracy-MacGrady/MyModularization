package com.toughen.libs.http;

import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by lijianjian on 2018/4/9.
 */

public interface ResponseDataDispatchInterface<T> {
    void onSuccess(Headers headers, T responseData);

    void parseResponseData(Response response);
}
