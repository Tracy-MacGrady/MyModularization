package com.zx.toughen.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import com.zx.toughen.R;
import com.zx.toughen.activity.MemorandumActivity;

/**
 * Created by 李健健 on 2017/2/27.
 */
public class MainMineUI extends FrameLayout implements View.OnClickListener {
    public MainMineUI(Context context) {
        super(context);
        View.inflate(context, R.layout.layout_main_mine, this);
        setListener();
    }

    private void setListener() {
        findViewById(R.id.user_home_view).setOnClickListener(this);
        findViewById(R.id.userinfo_view).setOnClickListener(this);
        findViewById(R.id.my_follow_view).setOnClickListener(this);
        findViewById(R.id.memorandum_view).setOnClickListener(this);
        findViewById(R.id.feedback_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userinfo_view:

                break;
            case R.id.user_home_view:

                break;
            case R.id.my_follow_view:

                break;
            case R.id.memorandum_view:
                getContext().startActivity(new Intent(getContext(), MemorandumActivity.class));
                break;
            case R.id.feedback_view:

                break;
        }
    }
}
