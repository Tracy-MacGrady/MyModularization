package com.zx.toughen.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toughen.libs.tools.SPUtils;
import com.zx.toughen.R;
import com.zx.toughen.activity.LoginActivity;
import com.zx.toughen.activity.MemorandumActivity;
import com.zx.toughen.application.MyApplication;
import com.zx.toughen.base.BaseFragment;
import com.zx.toughen.entity.UserInfo;
import com.zx.toughen.userauth.UserAuth;

/**
 * Created by 李健健 on 2017/2/27.
 */
public class MainMineFragment extends BaseFragment implements View.OnClickListener {
    private ImageView avatarView;
    private UserInfo userInfo;

    public static MainMineFragment newInstance() {

        Bundle args = new Bundle();

        MainMineFragment fragment = new MainMineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_main_mine, container, false);
            Log.e("aaa", "this is onCreateView in if");
        }
        Log.e("aaa", "this is onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("ddd", "this is onViewCreated");
        initData();
    }

    private void initData() {
    }

    @Override
    public void initView() {
//        avatarView = view.findViewById(R.id.avatar_view);
    }

    @Override
    public void setListener() {
        view.findViewById(R.id.user_home_view).setOnClickListener(this);
        view.findViewById(R.id.userinfo_view).setOnClickListener(this);
        view.findViewById(R.id.my_follow_view).setOnClickListener(this);
        view.findViewById(R.id.memorandum_view).setOnClickListener(this);
        view.findViewById(R.id.feedback_view).setOnClickListener(this);
        view.findViewById(R.id.logout_view).setOnClickListener(this);
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
            case R.id.logout_view:
                UserAuth.logout();
                startActivity(new Intent(this.getContext(), LoginActivity.class));
                break;
        }
    }
}
