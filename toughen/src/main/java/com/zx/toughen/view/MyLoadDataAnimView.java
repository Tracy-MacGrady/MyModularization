package com.zx.toughen.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zx.toughen.listenerinterface.MyLoadDataAnimRefreshInterface;

import toughen.zx.com.R;

/**
 * Created by 李健健 on 2017/9/25.
 */

public class MyLoadDataAnimView extends LinearLayout {
    private MyLoadDataAnimRefreshInterface listener;
    private ObjectAnimator objectAnimatorX, objectAnimatorY;
    private AnimatorSet animatorSet;
    private View animView;

    public MyLoadDataAnimView(Context context, MyLoadDataAnimRefreshInterface listener1) {
        super(context);
        this.listener = listener1;
        init();
    }

    public MyLoadDataAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLoadDataAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOrientation(VERTICAL);
        this.setGravity(Gravity.CENTER);
        View.inflate(getContext(), R.layout.layout_load_data_anim_view, this);
        animView = findViewById(R.id.anim_view);
    }

    public void setListener(MyLoadDataAnimRefreshInterface listener1) {
        this.listener = listener1;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (animatorSet == null) animatorSet = new AnimatorSet();
        if (objectAnimatorX == null)
            objectAnimatorX = ObjectAnimator.ofFloat(animView, "rotateX", 1f, -1f);
        if (objectAnimatorY == null)
            objectAnimatorY = ObjectAnimator.ofFloat(animView, "rotateY", 1f, -1f);
        objectAnimatorX.setDuration(1000);
        objectAnimatorY.setDuration(1000);
        objectAnimatorX.start();
        objectAnimatorY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (objectAnimatorX != null) objectAnimatorX.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimatorX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (objectAnimatorY != null) objectAnimatorY.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (objectAnimatorX != null && objectAnimatorX.isRunning()) objectAnimatorX.cancel();
        if (objectAnimatorY != null && objectAnimatorY.isRunning()) objectAnimatorY.cancel();
        objectAnimatorX = null;
        objectAnimatorY = null;
        animView.clearAnimation();
    }
}
