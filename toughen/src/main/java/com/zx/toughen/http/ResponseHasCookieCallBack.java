package com.zx.toughen.http;

import com.toughen.libs.http.ResponseDataDispatchIml;
import com.zx.toughen.userauth.AuthCookie;

import java.util.List;
import java.util.Map;

/**
 * header中包含cookie信息的回调
 *
 * @param <T>
 */
public abstract class ResponseHasCookieCallBack<T> extends ResponseDataDispatchIml<T> {

    @Override
    public void onSuccess(Map<String, List<String>> headers, T responseData) {
        AuthCookie.getInstance().setCookieList(headers.get("set-cookie"));
    }
}
