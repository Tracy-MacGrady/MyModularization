package com.zx.toughen.entity;

import java.io.Serializable;

/**
 * Created by 李健健 on 2017/4/10.
 */
public class MemorandumEntity implements Serializable {
    private boolean isPassword;
    private String titleVal;
    private String createTime;
    private String contentValue;

    public MemorandumEntity(String titleVal, String createTime, String contentValue, boolean b) {
        this.isPassword = b;
        this.titleVal = titleVal;
        this.createTime = createTime;
        this.contentValue = contentValue;
    }

    public boolean isPassword() {
        return isPassword;
    }

    public void setPassword(boolean password) {
        isPassword = password;
    }

    public String getTitleVal() {
        return titleVal;
    }

    public void setTitleVal(String titleVal) {
        this.titleVal = titleVal;
    }

    public String getContentValue() {
        return contentValue;
    }

    public void setContentValue(String contentValue) {
        this.contentValue = contentValue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
