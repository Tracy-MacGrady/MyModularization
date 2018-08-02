package com.toughen.libs.http;

import java.util.List;
import java.util.Map;

public class ToughenLibResponse {
    private BaseHttpResponseEntity entity;
    private Map<String, List<String>> headers;

    public BaseHttpResponseEntity getEntity() {
        return entity;
    }

    public void setEntity(BaseHttpResponseEntity entity) {
        this.entity = entity;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }
}
