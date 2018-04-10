package com.toughen.libs.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 屏幕分辨率相关utils
 * Created by lijianjian on 2018/4/2.
 */

public class DensityUtils {
    private static volatile DensityUtils instance;

    private DensityUtils() {
    }

    public static DensityUtils getInstance() {
        if (instance == null) {
            synchronized (DensityUtils.class) {
                if (instance == null) instance = new DensityUtils();
            }
        }
        return instance;
    }

    public DisplayMetrics getScreenDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕宽度PX
     */
    public float getScreenWidthPX(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度PX
     */
    public float getScreenHeightPX(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕尺寸
     */
    public double getScreenInches(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        int dens = displayMetrics.densityDpi;
        double wi = (double) width / (double) dens;
        double hi = (double) height / (double) dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);
        return screenInches;
    }

    /**
     * DIP 转 PX
     */
    public float dp2px(Context context, int dpValue) {
        float returnValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dpValue, context.getResources().getDisplayMetrics());
        return returnValue;
    }

    /**
     * DIP 转 PX
     */
    public float px2dp(Context context, int pxValue) {
        float returnValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, context.getResources().getDisplayMetrics());
        return returnValue;
    }

    /**
     * SP 转 PX
     */
    public float sp2px(Context context, int spValue) {
        float returnValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, spValue, context.getResources().getDisplayMetrics());
        return returnValue;
    }

    /**
     * PX 转SP
     */
    public float px2sp(Context context, int pxValue) {
        float returnValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pxValue, context.getResources().getDisplayMetrics());
        return returnValue;
    }
}
