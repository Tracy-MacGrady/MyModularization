package com.zx.toughen.utils.emchat;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

/**
 * Created by 李健健 on 2017/2/23.
 */

public class EMMessageSendUtil {
    private static EMMessageSendUtil util = new EMMessageSendUtil();

    private EMMessageSendUtil() {
    }

    public static EMMessageSendUtil getInstance() {
        return util;
    }

    /**
     * @param isGroup    是否为群聊
     * @param messageVal 发送的文本消息
     * @param toChatUser 发送给聊天人的用户ID
     * @说明 发送文本消息
     */
    public void sendTextMessage(boolean isGroup, String messageVal, String toChatUser, EMCallBack callBack) {
        EMMessage message = EMMessage.createTxtSendMessage(messageVal, toChatUser);
        sendMessage(isGroup, message, callBack);
    }

    /**
     * @说明 发送消息
     */
    private void sendMessage(boolean isGroup, EMMessage message, EMCallBack callBack) {
        if (isGroup) message.setChatType(EMMessage.ChatType.GroupChat);
        message.setMessageStatusCallback(callBack);
        EMClient.getInstance().chatManager().sendMessage(message);
    }
}
