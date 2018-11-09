package com.zx.toughen.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.toughen.libs.tools.ToastUtils;
import com.zx.toughen.dialog.MyProgressDialog;

/**
 * Created by lijianjian on 2018/4/2.
 */

public abstract class BaseFragment extends Fragment {
    private MyProgressDialog progressDialog;
    protected View view;
    private boolean isInitView = true;
    protected boolean hasInitData = false;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isInitView) {
            isInitView = false;
            initView();
            setListener();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        ToastUtils.destory();
    }

    public void showProgressDialog() {
        if (progressDialog == null) progressDialog = new MyProgressDialog(getContext());
        progressDialog.show();
    }

    public void showProgressDialog(String showProgressValue) {
        if (progressDialog == null) progressDialog = new MyProgressDialog(getContext());
        progressDialog.setContentValue(showProgressValue);
        progressDialog.show();
    }

    public void dissmissProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }
}
