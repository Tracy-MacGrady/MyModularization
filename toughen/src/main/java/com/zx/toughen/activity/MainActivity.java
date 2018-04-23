package com.zx.toughen.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zx.toughen.base.BaseActivity;
import com.toughen.libs.tools.ToastUtils;
import com.zx.toughen.R;
import com.zx.toughen.ui.MainFindUI;
import com.zx.toughen.ui.MainMessageUI;
import com.zx.toughen.ui.MainMineUI;
import com.zx.toughen.view.MyTitleBarView;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    //View
    private MyTitleBarView titleBarView;
    private FrameLayout contentLayout;
    private LinearLayout messageView, findView, mineView;
    private MainMessageUI messageUI;
    private MainFindUI findUI;
    private MainMineUI mineUI;

    //Data
    private long lastPressedBack = 0;//返回键上次点击的时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {
        titleBarView = (MyTitleBarView) findViewById(R.id.titlebar_view);
        contentLayout = (FrameLayout) findViewById(R.id.content_layout);
        messageView = (LinearLayout) findViewById(R.id.message_view);
        findView = (LinearLayout) findViewById(R.id.find_view);
        mineView = (LinearLayout) findViewById(R.id.mine_view);
        clickMessageView();
    }

    @Override
    public void setListener() {
        messageView.setOnClickListener(this);
        findView.setOnClickListener(this);
        mineView.setOnClickListener(this);
    }


    private void clickMessageView() {
        onClick(messageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_view:
                setBottomViewSelected(v);
                initSwitchUI(0);
                break;
            case R.id.find_view:
                setBottomViewSelected(v);
                initSwitchUI(1);
                break;
            case R.id.mine_view:
                setBottomViewSelected(v);
                initSwitchUI(2);
                break;
        }
    }

    /**
     * 底部导航按钮点击后相应内容的更换
     */
    private void initSwitchUI(int caseNum) {
        String titleName = "";
        contentLayout.removeAllViews();
        switch (caseNum) {
            case 0:
                titleName = getString(R.string.message);
                if (messageUI == null) messageUI = new MainMessageUI(this);
                contentLayout.addView(messageUI);
                break;
            case 1:
                titleName = getString(R.string.find);
                if (findUI == null) findUI = new MainFindUI(this);
                contentLayout.addView(findUI);
                break;
            case 2:
                titleName = getString(R.string.mine);
                if (mineUI == null) mineUI = new MainMineUI(this);
                contentLayout.addView(mineUI);
                break;
        }
        titleBarView.setTitleViewValue(titleName);
    }

    /**
     * 切换底部导航按钮的选中状态
     */
    private void setBottomViewSelected(View v) {
        messageView.getChildAt(0).setSelected(messageView == v);
        messageView.getChildAt(1).setSelected(messageView == v);
        findView.getChildAt(0).setSelected(findView == v);
        findView.getChildAt(1).setSelected(findView == v);
        mineView.getChildAt(0).setSelected(mineView == v);
        mineView.getChildAt(1).setSelected(mineView == v);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPressedBack > 1500) {
            lastPressedBack = currentTime;
            ToastUtils.showShort(this, getString(R.string.click_again_to_back));
            return;
        }
        super.onBackPressed();
    }
}
