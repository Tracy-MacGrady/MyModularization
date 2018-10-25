package com.toughen.httplib;

import java.io.InputStream;

public interface IHttpResponseCallback {
    void onSuccess(InputStream inputStream);

    void onFailure();
}
