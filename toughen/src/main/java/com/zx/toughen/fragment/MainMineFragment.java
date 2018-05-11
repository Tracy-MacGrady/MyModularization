package com.zx.toughen.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.toughen.R;
import com.zx.toughen.base.BaseFragment;

/**
 * Created by 李健健 on 2017/2/27.
 */
public class MainMineFragment extends BaseFragment {
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

    }
}
