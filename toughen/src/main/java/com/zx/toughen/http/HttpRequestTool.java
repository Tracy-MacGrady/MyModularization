package com.zx.toughen.http;

import com.zx.toughen.entity.httpresponceentity.UserLoginResponceEntity;

import java.util.HashMap;

import com.zx.toughenlib.callBack.MyRequestGetStringCallback;
import com.zx.toughenlib.tools.httputil.MyHttpRequestTool;

/**
 * Created by 李健健 on 2017/4/20.
 */

public class HttpRequestTool {
    private static volatile HttpRequestTool tool;

    public static HttpRequestTool getInstance() {
        if (tool == null) {
            synchronized (HttpRequestTool.class) {
                if (tool == null) {
                    tool = new HttpRequestTool();
                }
            }
        }
        return tool;
    }

    public void userLogin(String name, String password, MyRequestGetStringCallback<UserLoginResponceEntity> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userphone", name);
        params.put("password", password);
        MyHttpRequestTool.getInstance().startGetRequest(ConstantURL.USER_LOGIN, params, callback);
    }
}
