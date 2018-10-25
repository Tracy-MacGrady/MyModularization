package com.toughen.httplib;

public interface IHttpRequest {
    void setRequestURL(String url);

    void setRequestMethod(RequestMethod method);

    void setRequestParamsData(byte[] bytes);

    void setResponseCallback(IHttpResponseCallback callback);

    enum RequestMethod {
        GET, POST;
    }
}
