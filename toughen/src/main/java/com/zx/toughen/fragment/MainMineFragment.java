package com.zx.toughen.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.toughen.R;
import com.zx.toughen.activity.MemorandumActivity;
import com.zx.toughen.base.BaseFragment;

/**
 * Created by 李健健 on 2017/2/27.
 */
public class MainMineFragment extends BaseFragment implements View.OnClickListener {
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
            initView();
            setListener();
        }
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {
        view.findViewById(R.id.user_home_view).setOnClickListener(this);
        view.findViewById(R.id.userinfo_view).setOnClickListener(this);
        view.findViewById(R.id.my_follow_view).setOnClickListener(this);
        view.findViewById(R.id.memorandum_view).setOnClickListener(this);
        view.findViewById(R.id.feedback_view).setOnClickListener(this);
        view.findViewById(R.id.setting_view).setOnClickListener(this);
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
            case R.id.setting_view:

                break;
        }
    }
}
