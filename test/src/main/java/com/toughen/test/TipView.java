package com.toughen.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class TipView extends LinearLayout {
    private List<MyView> list;

    public TipView(Context context) {
        super(context);
        init(context);
    }


    public TipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new MyView(context));
        }

        for (int i = 0, size = list.size(); i < size; i++) {
            addView(list.get(i));
        }
    }

}
