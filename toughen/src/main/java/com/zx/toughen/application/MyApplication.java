package com.zx.toughen.application;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.toughen.libs.tools.AppUtils;
import com.toughen.libs.tools.LogUtils;
import com.toughen.mqttutil.MqttCallBackManager;
import com.toughen.mqttutil.MqttClientManager;
import com.toughen.mqttutil.constant.MqttConstant;
import com.toughen.mqttutil.interfaces.MqttClientConnectStatusListener;
import com.toughen.mqttutil.message.tool.GetMessageTool;
import com.umeng.analytics.MobclickAgent;
import com.zx.toughen.BuildConfig;
import com.zx.toughen.entity.MyMqttParamsEntity;
import com.zx.toughen.entity.UserInfo;
import com.zx.toughen.constant.Constant;

/**
 * Created by 李健健 on 2017/2/23.
 */

public class MyApplication extends MultiDexApplication {
    private static MyApplication application;
    private UserInfo userInfo;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String appName = AppUtils.getInstance().getNowProcessName(this, android.os.Process.myPid());
        if (appName != null && appName.equalsIgnoreCase(this.getPackageName())) {
            application = this;
            //设置是否输出日志
            LogUtils.setCanShowLog(BuildConfig.DEBUG);
            //设置友盟统计的场景
            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
            //初始化腾讯bugly
            CrashReport.initCrashReport(this.getApplicationContext(), Constant.BUGLY_APPID, BuildConfig.DEBUG);
        }
    }

    public void initMqtt(String clientID) {
        MqttClientManager.getInstance().init(getApplicationContext(), clientID, new MyMqttParamsEntity());
        MqttCallBackManager.getInstance().addConnectStatusListener(connectListener);
    }

    private MqttClientConnectStatusListener connectListener = new MqttClientConnectStatusListener() {
        @Override
        public void mqttClientConnectSuccess() {
            Log.e("fffff", "mqttClientConnectSuccess");
            GetMessageTool.subscribeTopic(2, MqttConstant.MQTT_TOPIC);
        }

        @Override
        public void mqttClientRepeatSuccess() {
            Log.e("fffff", "mqttClientRepeatSuccess");
            GetMessageTool.unsubscribeTopic(MqttConstant.MQTT_TOPIC);
            GetMessageTool.subscribeTopic(2, MqttConstant.MQTT_TOPIC);
        }

        @Override
        public void mqttClientConnectFailure() {
            Log.e("fffff", "mqttClientConnectFailure");
        }

        @Override
        public void mqttClientDisConnectSuccess() {
            Log.e("fffff", "mqttClientDisConnectSuccess");
        }

        @Override
        public void mqttClientDisConnectFailure() {
            Log.e("fffff", "mqttClientDisConnectFailure");
        }
    };

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        MqttClientManager.getInstance().disconnectMqttClientConnect();
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
