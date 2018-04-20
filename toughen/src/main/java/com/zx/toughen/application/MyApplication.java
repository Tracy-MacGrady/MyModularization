package com.zx.toughen.application;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.zx.toughen.entity.UserInfo;
import com.zx.toughen.utils.emchat.EMChatUtil;

import toughen.zx.com.BuildConfig;

import com.zx.toughen.constant.Constant;

import com.zx.toughenlib.base.BaseApplication;
import com.zx.toughenlib.tools.LogUtil;

/**
 * Created by 李健健 on 2017/2/23.
 */

public class MyApplication extends BaseApplication {
    private static MyApplication application;
    private UserInfo userInfo;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String appName = getAppName(android.os.Process.myPid());
        if (appName != null && appName.equalsIgnoreCase(this.getPackageName())) {
            application = this;
            //设置是否输出日志
            LogUtil.setLogEnable(BuildConfig.DEBUG);
            //设置友盟统计的场景
            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
            //初始化腾讯bugly
            CrashReport.initCrashReport(this.getApplicationContext(), Constant.BUGLY_APPID, BuildConfig.DEBUG);
            //初始化环信即时通讯的sdk
            EMChatUtil.getInstance().init(this);
        }
    }

    @GlideModule
    private final class MyGlideModule extends AppGlideModule {
        public MyGlideModule() {
            super();
        }

        @Override
        public boolean isManifestParsingEnabled() {
            return super.isManifestParsingEnabled();
        }

        @Override
        public void applyOptions(Context context, GlideBuilder builder) {
            super.applyOptions(context, builder);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
//        GlideApp.with/
        Glide.with(this).load("").into(new ImageView(this));
        Glide.get(this).clearDiskCache();
        Glide.get(this).clearMemory();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
