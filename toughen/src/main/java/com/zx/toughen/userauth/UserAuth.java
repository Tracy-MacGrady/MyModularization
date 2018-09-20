package com.zx.toughen.userauth;

import android.text.TextUtils;

import com.toughen.libs.libtools.FastJsonUtil;
import com.toughen.libs.tools.ActivityManagerUtils;
import com.toughen.libs.tools.SPUtils;
import com.zx.toughen.application.MyApplication;
import com.zx.toughen.entity.UserInfo;

import java.util.List;

public class UserAuth {
    private static final String SP_FILE_NAME = "USER_AUTH";
    private static final String USERINFO_KEY = "USER";
    private static final String COOKIE_KEY = "COOKIE";

    public static void login(UserInfo info) {
        MyApplication.getInstance().setUserInfo(info);
        SPUtils.getInstance().saveString(MyApplication.getInstance(), SP_FILE_NAME, FastJsonUtil.Object2JsonString(info), USERINFO_KEY);
        SPUtils.getInstance().saveString(MyApplication.getInstance(), SP_FILE_NAME, FastJsonUtil.Object2JsonString(AuthCookie.getInstance().getCookieList()), COOKIE_KEY);
    }

    public static void logout() {
        MyApplication.getInstance().setUserInfo(null);
        SPUtils.getInstance().removeData(MyApplication.getInstance(), SP_FILE_NAME, USERINFO_KEY);
        SPUtils.getInstance().removeData(MyApplication.getInstance(), SP_FILE_NAME, COOKIE_KEY);
        ActivityManagerUtils.getInstance().removeAllActivity();
    }

    public static void update(UserInfo info) {
        MyApplication.getInstance().setUserInfo(info);
        SPUtils.getInstance().saveString(MyApplication.getInstance(), SP_FILE_NAME, FastJsonUtil.Object2JsonString(info), USERINFO_KEY);
    }

    public static boolean authUser() {
        String cookie = SPUtils.getInstance().getString(MyApplication.getInstance(), SP_FILE_NAME, COOKIE_KEY);
        if (TextUtils.isEmpty(cookie)) return false;
        String userVal = SPUtils.getInstance().getString(MyApplication.getInstance(), SP_FILE_NAME, USERINFO_KEY);
        if (TextUtils.isEmpty(userVal)) return false;
        MyApplication.getInstance().setUserInfo(FastJsonUtil.JsonStr2Object(userVal, UserInfo.class));
        AuthCookie.getInstance().setCookieList(FastJsonUtil.JsonStr2List(cookie, String.class));
        return true;
    }
}
