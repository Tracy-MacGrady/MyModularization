package com.toughen.libs.http;

import android.os.Handler;
import android.os.Message;

import com.toughen.libs.libtools.FastJsonUtil;
import com.toughen.libs.tools.LogUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by lijianjian on 2018/4/9.
 */

public abstract class ResponseDataDispatchIml<T> implements ResponseDataDispatchInterface<T> {
    @Override
    public void onRequestFailure(String failureMsg) {
        Message msg = Message.obtain();
        msg.obj = failureMsg;
        msg.what = 2000;
        handler.sendMessage(msg);
    }

    @Override
    public void parseResponseData(Response response) throws IOException {
        String responseValue = response.body().string();
        LogUtils.e(responseValue);
        if (responseValue != null) {
            BaseHttpResponseEntity entity = FastJsonUtil.JsonStr2Object(responseValue, BaseHttpResponseEntity.class);
            ToughenLibResponse toughenLibResponse = new ToughenLibResponse();
            toughenLibResponse.setEntity(entity);
            toughenLibResponse.setHeaders(response.headers().toMultimap());
            Message msg = Message.obtain();
            msg.obj = toughenLibResponse;
            msg.what = 1000;
            handler.sendMessage(msg);
        }
    }

    /**
     * 将子线程的数据传递到主线程
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    ToughenLibResponse response = (ToughenLibResponse) msg.obj;
                    BaseHttpResponseEntity baseEntity = response.getEntity();
                    switch (baseEntity.getCode()) {
                        case 10000:
                            T data = FastJsonUtil.JsonStr2Object(response.getEntity().getData(), getType());
                            onSuccess(response.getHeaders(), data);
                            break;
                        default:
                            onFailure(baseEntity.getData());
                            break;
                    }
                    break;
                case 2000:
                    String failureMsg = (String) msg.obj;
                    onFailure(failureMsg);
                    break;
            }
        }
    };

    /**
     * 获取当前的类型
     *
     * @return
     */
    private Type getType() {
        Type type = String.class;
        Type mySuperClass = this.getClass().getGenericSuperclass();
        if (mySuperClass instanceof ParameterizedType)
            type = ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
        return type;
    }
}
