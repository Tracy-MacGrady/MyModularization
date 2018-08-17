package com.zx.toughen.userauth;

import java.util.List;

public class AuthCookie {
    private static volatile AuthCookie instance;

    public static AuthCookie getInstance() {
        if (instance == null) synchronized (AuthCookie.class) {
            if (instance == null) instance = new AuthCookie();
        }
        return instance;
    }

    private List<String> cookieList;

    public List<String> getCookieList() {
        return cookieList;
    }

    public void setCookieList(List<String> cookieList) {
        this.cookieList = cookieList;
    }
}
