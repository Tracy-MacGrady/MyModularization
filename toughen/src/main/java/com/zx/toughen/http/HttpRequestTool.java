package com.zx.toughen.http;

import com.toughen.libs.http.OkHttpManager;
import com.toughen.libs.http.ResponseDataDispatchIml;

import java.util.HashMap;

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

    public void userLogin(String userPhone, String password, ResponseDataDispatchIml<?> dataDispatchIml) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userphone", userPhone);
        params.put("password", password);
        OkHttpManager.getInstance().getRequest(ConstantURL.USER_LOGIN, params, null, dataDispatchIml);
    }

}
