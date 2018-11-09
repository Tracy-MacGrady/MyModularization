package com.toughen.libs.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toughen.libs.R;

/**
 * Created by 李健健 on 2017/9/25.
 */

public class MyLoadDataAnimView extends LinearLayout {
    private ObjectAnimator objectAnimatorX, objectAnimatorY;
    private AnimatorSet animatorSet;
    private View animView;
    private TextView textView;

    public MyLoadDataAnimView(Context context) {
        super(context);
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
        textView = findViewById(R.id.my_anim_textview);
        initAnim();
    }

    private void initAnim() {
        animatorSet = new AnimatorSet();
        objectAnimatorX = ObjectAnimator.ofFloat(animView, "scaleX", 1f, -1f);
        objectAnimatorY = ObjectAnimator.ofFloat(animView, "scaleY", 1f, -1f);
        animatorSet.play(objectAnimatorX).after(objectAnimatorY);
        animatorSet.setDuration(1500);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isShown() && !animatorSet.isRunning()) animatorSet.start();
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
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            if (!animatorSet.isRunning()) animatorSet.start();
        } else animatorSet.cancel();
    }

    public void setViewBgColor(int viewBgColor) {
        this.animView.setBackgroundColor(viewBgColor);
    }

    public void setTextColor(int color) {
//        this.textView.setTextColor(color);
    }
}
