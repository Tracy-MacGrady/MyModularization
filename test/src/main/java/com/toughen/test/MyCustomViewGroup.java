package com.toughen.test;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MyCustomViewGroup extends ViewGroup {
    public MyCustomViewGroup(Context context) {
        super(context);
    }

    public MyCustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("bbbb", "this is group view height" + getHeight());
        for (int i = 0, size = getChildCount(); i < size; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            int mLeft = (int) (value + params.leftMargin + getPaddingLeft());
            int mTop = (int) (value + params.topMargin + getPaddingTop());
            if (params.height == MarginLayoutParams.MATCH_PARENT) {
                params.height = getHeight() - params.topMargin - params.bottomMargin - getPaddingTop() - getPaddingBottom();
            }
            if (params.width == MarginLayoutParams.MATCH_PARENT) {
                params.width = getWidth() - params.leftMargin - params.rightMargin - getPaddingLeft() - getPaddingRight();
            }
            Log.e("aaaaaaa==", "height " + params.height);
            Log.e("aaaaaaa==", "childView.getPaddingLeft() " + childView.getPaddingLeft());
            Log.e("aaaaaaa==", "left " + childView.getLeft());
            Log.e("aaaaaaa==", "left margin " + params.leftMargin);
            childView.layout(mLeft, mTop, mLeft + params.width, mTop + params.height);
        }
    }

    private float value = 0;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}
