package com.zx.toughen.entity;

/**
 * Created by 李健健 on 2017/4/19.
 * 评论内容类
 */
public class CommentEntity {
    private UserInfo userInfo;
    private String time;
    private String commentValue;

    public String getCommentValue() {
        return commentValue;
    }

    public void setCommentValue(String commentValue) {
        this.commentValue = commentValue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
