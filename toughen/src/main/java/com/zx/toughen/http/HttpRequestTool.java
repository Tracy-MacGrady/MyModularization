package com.zx.toughen.http;

import com.toughen.libs.http.OkHttpCookieJar;
import com.toughen.libs.http.OkHttpManager;
import com.toughen.libs.http.ResponseDataDispatchIml;
import com.toughen.libs.libtools.FastJsonUtil;
import com.toughen.libs.tools.AppUtils;
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

    private HashMap<String, String> initHeader(boolean hasCookie) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("FROM", "mobile_android");
        headers.put("User-Agent", AppUtils.getInstance().getUAString());
        headers.put("Referer", "");
        if (hasCookie) headers.put("Cookie", initCookieStrValue());
        return headers;
    }

    private String initCookieStrValue() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, size = AuthCookie.getInstance().getCookieList().size(); i < size; i++) {
            stringBuffer.append(AuthCookie.getInstance().getCookieList().get(i));
            stringBuffer.append(i == size - 1 ? "" : ";");
        }
        return stringBuffer.toString();
    }

    /**
     * 用户登录接口
     */
    public void userLogin(String userPhone, String password, ResponseDataDispatchIml<?> dataDispatchIml) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userphone", userPhone);
        params.put("password", password);
        OkHttpManager.getInstance().getRequest(ConstantURL.USER_LOGIN, params, initHeader(false), dataDispatchIml);
    }

    /**
     * 获取用户信息
     */
    public void getUserinfo(ResponseDataDispatchIml<?> dataDispatchIml) {
        HashMap<String, String> headers = initHeader(true);
        OkHttpManager.getInstance().getRequest(ConstantURL.USER_GET_USERINFO, null, headers, dataDispatchIml);
    }


}
