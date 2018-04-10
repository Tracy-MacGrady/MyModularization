package com.toughen.libs.tools;

import android.app.Activity;

import java.util.Stack;

/**
 * Activity管理utils
 * Created by lijianjian on 2018/4/2.
 */

public class ActivityManagerUtils {
    private static volatile ActivityManagerUtils instance;
    private static Stack<Activity> activityStack = new Stack<>();

    private ActivityManagerUtils() {
    }

    public static ActivityManagerUtils getInstance() {
        if (instance == null) {
            synchronized (ActivityManagerUtils.class) {
                if (instance == null) {
                    instance = new ActivityManagerUtils();
                }
            }
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack != null) activityStack.push(activity);
    }

    public void removeActivity(Activity activity) {
        if (activityStack != null) activityStack.remove(activity);
    }

    public void removeAllActivity() {
        if (activityStack != null) {
            for (int i = 0; i < activityStack.size(); i = 0) {
                activityStack.pop().finish();
            }
        }
    }
}
