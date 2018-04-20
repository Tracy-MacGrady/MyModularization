package com.toughen.libs.base;

import android.app.Activity;

/**
 * Created by lijianjian on 2018/4/2.
 */

public abstract class BaseActivity extends Activity {
    /**
     * 该方法需再次在BaseActivity子类中调用
     * <p/>
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 该方法需在BaseActivity子类中调用
     * <p/>
     * 设置相关控件的监听事件
     */
    public abstract void setListener();

}
