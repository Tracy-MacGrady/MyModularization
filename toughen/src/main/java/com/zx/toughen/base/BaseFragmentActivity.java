package com.zx.toughen.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.toughen.libs.tools.ActivityManagerUtils;
import com.toughen.libs.tools.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.zx.toughen.dialog.MyProgressDialog;

/**
 * Created by lijianjian on 2018/4/2.
 */

public abstract class BaseFragmentActivity extends FragmentActivity {
    private MyProgressDialog progressDialog;
    protected FragmentManager fragmentManager;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        ActivityManagerUtils.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dissmissProgressDialog();
        progressDialog = null;
        ToastUtils.destory();
        ActivityManagerUtils.getInstance().removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName());
        MobclickAgent.onPause(this);
    }

    public void showProgressDialog() {
        if (progressDialog == null) progressDialog = new MyProgressDialog(this);
        progressDialog.show();
    }

    public void showProgressDialog(String showProgressValue) {
        if (progressDialog == null) progressDialog = new MyProgressDialog(this);
        progressDialog.setContentValue(showProgressValue);
        progressDialog.show();
    }

    public void dissmissProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }
}
