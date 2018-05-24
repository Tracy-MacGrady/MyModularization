package com.zx.toughen.entity;

import java.io.Serializable;

/**
 * Created by 李健健 on 2017/3/10.
 */
public class MessageEntity implements Serializable {
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
