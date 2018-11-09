package com.zx.toughen.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李健健 on 2017/3/10.
 */
public class MessageListEntity implements Serializable {
    private List<MessageItemInfo> msgItemList;

    public List<MessageItemInfo> getMsgItemList() {
        return msgItemList;
    }

    public void setMsgItemList(List<MessageItemInfo> msgItemList) {
        this.msgItemList = msgItemList;
    }
}
