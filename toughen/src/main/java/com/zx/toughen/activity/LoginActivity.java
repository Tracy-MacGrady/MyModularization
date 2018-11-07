package com.zx.toughen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.toughen.libs.tools.LogUtils;
import com.zx.toughen.base.BaseAppCompatActivity;
import com.toughen.libs.tools.AppUtils;
import com.toughen.libs.tools.ToastUtils;
import com.zx.toughen.R;
import com.zx.toughen.entity.httpresponceentity.UserLoginResponceEntity;
import com.zx.toughen.http.HttpRequestTool;
import com.zx.toughen.http.ResponseHasCookieCallBack;
import com.zx.toughen.userauth.UserAuth;

import java.util.List;
import java.util.Map;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private View allLayout;
    private View login_login_view;
    private EditText userPhoneView, passWordView;
    private ImageView showPasswordView;
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
        showPasswordView = findViewById(R.id.show_passwordview);

    }

    private View.OnFocusChangeListener edittextViewFoucusChangelistener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.login_nickname_view:
                    ((TextInputLayout) findViewById(R.id.login_nickname_layout)).setHint(hasFocus || !TextUtils.isEmpty(userPhoneView.getText()) ? "手机号" : getString(R.string.login_phone_hint));
                    break;
                case R.id.login_password_view:
                    ((TextInputLayout) findViewById(R.id.login_password_layout)).setHint(hasFocus || !TextUtils.isEmpty(passWordView.getText()) ? "密码" : getString(R.string.login_password_hint));
                    break;
            }
        }
    };

    @Override
    public void setListener() {
        findViewById(R.id.activity_login).setOnClickListener(this);
        login_login_view.setOnClickListener(this);
        userPhoneView.setOnFocusChangeListener(edittextViewFoucusChangelistener);
        passWordView.setOnFocusChangeListener(edittextViewFoucusChangelistener);
        findViewById(R.id.login_forget_view).setOnClickListener(this);
        findViewById(R.id.login_register_view).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_view:
                if (checkEdittextValue()) loginMethod();
                break;
            case R.id.login_forget_view:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_register_view:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.show_passwordview:
                if (showPasswordView.isSelected()) {
                    passWordView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    passWordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                showPasswordView.setSelected(!showPasswordView.isSelected());

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
        showProgressDialog();
        HttpRequestTool.getInstance().userLogin(this, userphone, password, new ResponseHasCookieCallBack<UserLoginResponceEntity>() {
            @Override
            public void onSuccess(Map<String, List<String>> headers, UserLoginResponceEntity responseData) {
                super.onSuccess(headers, responseData);
                dissmissProgressDialog();
                LogUtils.e("====" + headers);
                UserAuth.login(responseData.getUserinfo());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(String failureMsg) {
                dissmissProgressDialog();
                ToastUtils.showShort(LoginActivity.this, failureMsg);
            }
        });
    }

}
