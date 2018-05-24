package com.zx.toughen.http;

import com.zx.toughen.entity.httpresponceentity.UserLoginResponceEntity;

import java.util.HashMap;

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
}
