package com.toughen.libs.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Set;

/**
 * SharedPreferences 工具类
 * Created by lijianjian on 2018/4/3.
 */

public class SPUtils {
    private static SPUtils instance;
    private static String spFileName = "SP_UTIL_FILE";

    public static SPUtils getInstance() {
        if (instance == null) synchronized (SPUtils.class) {
            if (instance == null) instance = new SPUtils();
        }
        return instance;
    }

    private SPUtils() {
    }

    public void saveString(Context context, String mySPFileName, String saveValue, String keyValue) {
        SharedPreferences.Editor editor = getSPEditor(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        editor.putString(keyValue, saveValue);
        editor.apply();
    }

    public void saveInteger(Context context, String mySPFileName, int saveValue, String keyValue) {
        SharedPreferences.Editor editor = getSPEditor(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        editor.putInt(keyValue, saveValue);
        editor.apply();
    }

    public void saveFloat(Context context, String mySPFileName, float saveValue, String keyValue) {
        SharedPreferences.Editor editor = getSPEditor(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        editor.putFloat(keyValue, saveValue);
        editor.apply();
    }

    public void saveBoolean(Context context, String mySPFileName, boolean saveValue, String keyValue) {
        SharedPreferences.Editor editor = getSPEditor(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        editor.putBoolean(keyValue, saveValue);
        editor.apply();
    }

    public void saveLong(Context context, String mySPFileName, long saveValue, String keyValue) {
        SharedPreferences.Editor editor = getSPEditor(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        editor.putLong(keyValue, saveValue);
        editor.apply();
    }

    public void saveSet(Context context, String mySPFileName, Set<String> saveValue, String keyValue) {
        SharedPreferences.Editor editor = getSPEditor(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        editor.putStringSet(keyValue, saveValue);
        editor.apply();
    }

    public String getString(Context context, String mySPFileName, String keyValue) {
        SharedPreferences sp = getSharedPreferences(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        return sp.getString(keyValue, null);
    }

    public Set<String> getStringSet(Context context, String mySPFileName, String keyValue) {
        SharedPreferences sp = getSharedPreferences(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        return sp.getStringSet(keyValue, null);
    }

    public int getInt(Context context, String mySPFileName, String keyValue) {
        SharedPreferences sp = getSharedPreferences(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        return sp.getInt(keyValue, -1);
    }

    public float getFloat(Context context, String mySPFileName, String keyValue) {
        SharedPreferences sp = getSharedPreferences(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        return sp.getFloat(keyValue, -1f);
    }

    public long getLong(Context context, String mySPFileName, String keyValue) {
        SharedPreferences sp = getSharedPreferences(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        return sp.getLong(keyValue, -1);
    }

    public boolean getBoolean(Context context, String mySPFileName, String keyValue) {
        SharedPreferences sp = getSharedPreferences(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        return sp.getBoolean(keyValue, false);
    }

    private SharedPreferences.Editor getSPEditor(Context context, String spFileName) {
        SharedPreferences sp = getSharedPreferences(context, spFileName);
        return sp.edit();
    }

    private SharedPreferences getSharedPreferences(Context context, String spFileName) {
        return context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
    }

    public boolean removeData(Context context, String mySPFileName, String keyValue) {
        SharedPreferences.Editor editor = getSPEditor(context, TextUtils.isEmpty(mySPFileName) ? spFileName : mySPFileName);
        editor.remove(keyValue);
        return editor.commit();
    }

    public boolean removeData(Context context, String keyValue) {
        return removeData(context, "", keyValue);
    }
}
