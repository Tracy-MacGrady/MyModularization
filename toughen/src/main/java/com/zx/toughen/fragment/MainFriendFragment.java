package com.zx.toughen.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.toughen.R;
import com.zx.toughen.base.BaseFragment;

/**
 * Created by 李健健 on 2017/2/23.
 */

public class MainFriendFragment extends BaseFragment {

    public static MainFriendFragment newInstance() {
        Bundle args = new Bundle();
        MainFriendFragment fragment = new MainFriendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_main_message_ui, container, false);
            initView();
            setListener();
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {

    }
}
