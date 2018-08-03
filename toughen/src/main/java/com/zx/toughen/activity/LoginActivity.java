package com.zx.toughen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.toughen.libs.http.ResponseDataDispatchIml;
import com.toughen.libs.tools.LogUtils;
import com.zx.toughen.base.BaseActivity;
import com.toughen.libs.tools.AppUtils;
import com.toughen.libs.tools.ToastUtils;
import com.zx.toughen.R;
import com.zx.toughen.entity.httpresponceentity.UserLoginResponceEntity;
import com.zx.toughen.http.HttpRequestTool;

import java.util.List;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private View allLayout;
    private View login_login_view;
    private EditText userPhoneView, passWordView;
    //edittext text value
    private String userphone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {
        login_login_view = findViewById(R.id.login_login_view);
        allLayout = findViewById(R.id.activity_login);
        userPhoneView = findViewById(R.id.login_nickname_view);
        passWordView = findViewById(R.id.login_password_view);
    }

    @Override
    public void setListener() {
        findViewById(R.id.activity_login).setOnClickListener(this);
        login_login_view.setOnClickListener(this);
        findViewById(R.id.login_forget_view).setOnClickListener(this);
        findViewById(R.id.login_register_view).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_view:
                if (checkEdittextValue())
                    loginMethod();
                break;
            case R.id.login_forget_view:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_register_view:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.activity_login:
                AppUtils.getInstance().hideKeyboard(this);
                break;
        }
    }

    private boolean checkEdittextValue() {
        userphone = userPhoneView.getText().toString();
        password = passWordView.getText().toString();
        if (TextUtils.isEmpty(userphone)) {
            ToastUtils.showShort(this, getString(R.string.toast_empty_username));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort(this, getString(R.string.toast_empty_password));
            return false;
        }
        return true;
    }

    private void loginMethod() {
        HttpRequestTool.getInstance().userLogin(userphone, password, new ResponseDataDispatchIml<UserLoginResponceEntity>() {
            @Override
            public void onSuccess(Map<String, List<String>> headers, UserLoginResponceEntity responseData) {
                LogUtils.e("====" + headers);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(String failureMsg) {
                ToastUtils.showShort(LoginActivity.this, failureMsg);
            }
        });
    }

}
