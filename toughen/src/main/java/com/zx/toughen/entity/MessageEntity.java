package com.zx.toughen.entity;

import com.hyphenate.chat.EMMessage;

import java.io.Serializable;

/**
 * Created by 李健健 on 2017/3/10.
 */
public class MessageEntity implements Serializable {
    private UserInfo userInfo;
    private EMMessage emMessage;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public EMMessage getEmMessage() {
        return emMessage;
    }

    public void setEmMessage(EMMessage emMessage) {
        this.emMessage = emMessage;
    }
}
