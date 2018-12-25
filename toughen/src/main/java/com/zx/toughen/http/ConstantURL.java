package com.zx.toughen.http;

/**
 * Created by 李健健 on 2017/4/20.
 */

public class ConstantURL {
    //    private static final String DOMAIN = "http://toughen.51vip.biz/";
    private static final String DOMAIN = "http://16190c78z3.iask.in//";
    //用户登录
    public static final String USER_LOGIN = DOMAIN + "toughen/loginAction";
    //获取用户信息
    public static final String USER_GET_USERINFO = DOMAIN + "toughen/getUserinfoAction";
    //用户注册
    public static final String USER_REGISTER = DOMAIN + "toughen/registerAction";
    //修改用户信息
    public static final String USER_UPDATEINFO = DOMAIN + "toughen/updateAction";
    //保存账号信息
    public static final String ACCOUNT_SAVEINFO = DOMAIN + "toughen/saveAccountAction";
    //获取用户名下的账号信息
    public static final String ACCOUNT_GETALL_BYUSER = DOMAIN + "toughen/getAccountAction";
}
