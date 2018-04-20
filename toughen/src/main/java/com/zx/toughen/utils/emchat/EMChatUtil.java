package com.zx.toughen.utils.emchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.util.NetUtils;
import com.zx.toughen.activity.LoginActivity;

import toughenlib.zx.com.BuildConfig;
import com.zx.toughenlib.base.ActivityManager;

/**
 * Created by 李健健 on 2017/2/23.
 */

public class EMChatUtil {
    private static EMChatUtil eMchatUtil = new EMChatUtil();

    public static EMChatUtil getInstance() {
        return eMchatUtil;
    }

    private EMChatUtil() {
    }

    public void init(Context context) {
        EMOptions options = new EMOptions();
        options.setAutoLogin(false);//是否设置为自动登录
        options.setAcceptInvitationAlways(false);//请求添加好友的时候是否需要同意
        EMClient.getInstance().init(context, options);
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG);//是否为dubug模式
    }

    private EMConnectionListener myConnectionListener = new EMConnectionListener() {
        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(int i) {
            handler.sendEmptyMessage(i);
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int i = msg.what;
            Activity activity = ActivityManager.getInstance().getCurrentActivity();
            if (NetUtils.hasNetwork(activity)) {
                if (i == EMError.USER_REMOVED) {
                    Toast.makeText(activity, "您的账号已经被下线", Toast.LENGTH_SHORT).show();
                } else if (i == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    Toast.makeText(activity, "您的账号在异地登陆", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "连接失败,请重新登陆", Toast.LENGTH_SHORT).show();
                }
                activity.startActivity(new Intent(activity, LoginActivity.class));
                ActivityManager.getInstance().finishAllActivity();
            } else {
                Toast.makeText(activity, "请检查您的网络", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void loginEMchat(String username, String password, final EMCallBack callBack) {
        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().addConnectionListener(myConnectionListener);//添加连接状态的监听
                EMClient.getInstance().chatManager().loadAllConversations();
                EMClient.getInstance().groupManager().loadAllGroups();
                if (callBack != null) callBack.onSuccess();
            }

            @Override
            public void onError(int i, String s) {
                if (callBack != null) callBack.onError(i, s);
            }

            @Override
            public void onProgress(int i, String s) {
                if (callBack != null) callBack.onProgress(i, s);
            }
        });
    }

    public void logoutEMchat(EMCallBack callBack) {
        EMClient.getInstance().removeConnectionListener(myConnectionListener);
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                Activity activity = ActivityManager.getInstance().getCurrentActivity();
                activity.startActivity(new Intent(activity, LoginActivity.class));
                ActivityManager.getInstance().finishAllActivity();
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
