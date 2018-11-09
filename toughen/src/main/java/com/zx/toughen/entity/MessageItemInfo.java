package com.zx.toughen.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageItemInfo implements Parcelable {
    private UserInfo userInfo;
    private MessageInfo msgInfo;

    public MessageItemInfo() {
    }

    protected MessageItemInfo(Parcel in) {
    }

    public static final Creator<MessageItemInfo> CREATOR = new Creator<MessageItemInfo>() {
        @Override
        public MessageItemInfo createFromParcel(Parcel in) {
            return new MessageItemInfo(in);
        }

        @Override
        public MessageItemInfo[] newArray(int size) {
            return new MessageItemInfo[size];
        }
    };

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public MessageInfo getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(MessageInfo msgInfo) {
        this.msgInfo = msgInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
