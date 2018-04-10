package com.toughen.libs.tools;

import android.util.Log;

/**
 * 调试日志工具类
 * Created by lijianjian on 2018/4/3.
 */

public class LogUtils {
    public static String TAG = "DebugUtils===";
    public static boolean CAN_SHOW_LOG = true;

    public static void v(String log) {
        if (CAN_SHOW_LOG) Log.v(TAG, log);
    }

    public static void v(String tag, String log) {
        if (CAN_SHOW_LOG) Log.v(tag, log);
    }

    public static void d(String log) {
        if (CAN_SHOW_LOG) Log.d(TAG, log);
    }

    public static void d(String tag, String log) {
        if (CAN_SHOW_LOG) Log.d(tag, log);
    }

    public static void i(String log) {
        if (CAN_SHOW_LOG) Log.i(TAG, log);
    }

    public static void i(String tag, String log) {
        if (CAN_SHOW_LOG) Log.i(tag, log);
    }

    public static void w(String log) {
        if (CAN_SHOW_LOG) Log.w(TAG, log);
    }

    public static void w(String tag, String log) {
        if (CAN_SHOW_LOG) Log.w(tag, log);
    }

    public static void e(String log) {
        if (CAN_SHOW_LOG) Log.e(TAG, log);
    }

    public static void e(String tag, String log) {
        if (CAN_SHOW_LOG) Log.e(tag, log);
    }
}
