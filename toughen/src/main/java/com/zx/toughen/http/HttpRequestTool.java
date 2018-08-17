package com.zx.toughen.http;

import com.toughen.libs.http.OkHttpCookieJar;
import com.toughen.libs.http.OkHttpManager;
import com.toughen.libs.http.ResponseDataDispatchIml;
import com.toughen.libs.libtools.FastJsonUtil;
import com.zx.toughen.userauth.AuthCookie;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 李健健 on 2017/4/20.
 */

public class HttpRequestTool {
    private static volatile HttpRequestTool tool;

    public static HttpRequestTool getInstance() {
        if (tool == null) {
            synchronized (HttpRequestTool.class) {
                if (tool == null) tool = new HttpRequestTool();
            }
        }
        return tool;
    }

    /**
     * 用户登录接口
     */
    public void userLogin(String userPhone, String password, ResponseDataDispatchIml<?> dataDispatchIml) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userphone", userPhone);
        params.put("password", password);
        OkHttpManager.getInstance().getRequest(ConstantURL.USER_LOGIN, params, null, dataDispatchIml);
    }

    /**
     * 获取用户信息
     */
    public void getUserinfo(ResponseDataDispatchIml<?> dataDispatchIml) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "name=value;dddd=lisi");
        headers.put("aaaaa", "fffffffff");
        OkHttpManager.getInstance().getRequest(ConstantURL.USER_GET_USERINFO, null, headers, dataDispatchIml);
    }
}
