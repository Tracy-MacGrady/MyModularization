package com.zx.toughen.entity;

import java.io.Serializable;

/**
 * Created by 李健健 on 2017/3/10.
 */
public class UserInfo implements Serializable {

    /**
     * id : 1
     * useraddress : beijingshi
     * useremail : sdfadsf@qq.com
     * username : lisi
     * userphone : 15612345678
     */

    private int id;
    private String useraddress;
    private String useremail;
    private String username;
    private String userphone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }
}
