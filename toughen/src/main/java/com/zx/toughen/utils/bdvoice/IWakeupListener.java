package com.zx.toughen.utils.bdvoice;

/**
 * Created by Administrator on 2018/1/2.
 */

public interface IWakeupListener {
    void onSuccess(String word, WakeUpResult result);

    void onStop();

    void onError(int errorCode, String errorMessge, WakeUpResult result);

    void onASrAudio(byte[] data, int offset, int length);
}
