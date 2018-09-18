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
public abstract class ResponseNormalCallBack<T> extends ResponseDataDispatchIml<T> {
}
