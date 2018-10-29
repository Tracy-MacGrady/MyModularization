package com.zx.toughen.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zx.toughen.R;
import com.zx.toughen.base.BaseAppCompatActivity;
import com.zx.toughen.listenerinterface.MyTitleBarViewListenerInterface;
import com.zx.toughen.view.MyTitleBarView;

public class RegisterActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private MyTitleBarView titleBarView;
    private TextView vCoceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setListener();
        initValue();
    }

    @Override
    public void initView() {
        titleBarView = findViewById(R.id.titlebar_view);
        vCoceView = findViewById(R.id.get_vcode_view);
    }

    @Override
    public void setListener() {
        titleBarView.setListener(titleListener);
        vCoceView.setOnClickListener(this);
        findViewById(R.id.register_view).setOnClickListener(this);
    }

    public void initValue() {

    }

    /**
     * 标题栏的监听事件
     */
    private MyTitleBarViewListenerInterface titleListener = new MyTitleBarViewListenerInterface() {
        @Override
        public void clickLeft() {
            finish();
        }

        @Override
        public void clickTitle() {

        }

        @Override
        public void clickRight() {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_view:
                registerMethod();
                break;
            case R.id.get_vcode_view:

                break;
        }
    }

    private void registerMethod() {
    }
}
