package com.zx.toughen.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zx.toughen.listenerinterface.MemorandumAuthListenerInterface;
import com.zx.toughenlib.tools.ScreenTool;

import java.util.ArrayList;
import java.util.List;

import toughen.zx.com.R;

/**
 * Created by 李健健 on 2017/4/12.
 */

public class MemorandumAuthDialog extends Dialog implements View.OnClickListener {
    private List<TextView> inputViewList = new ArrayList<>();
    private List<View> numberViewList = new ArrayList<>();
    private View cancelView;
    private String[] inputValues = new String[6];
    private int nowIndex = 0;
    private MemorandumAuthListenerInterface listener;

    public MemorandumAuthDialog(Context context) {
        super(context, R.style.dialog_is_not_transparent_memorandum_dialog);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_memorandum_auth);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = View.inflate(getContext(), layoutResID, null);
        initView(view);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = ScreenTool.getScreenWidth(getContext());
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
        super.setContentView(view, params);
    }

    private void initView(View view) {
        inputViewList.add((TextView) view.findViewById(R.id.auth_input_view1));
        inputViewList.add((TextView) view.findViewById(R.id.auth_input_view2));
        inputViewList.add((TextView) view.findViewById(R.id.auth_input_view3));
        inputViewList.add((TextView) view.findViewById(R.id.auth_input_view4));
        inputViewList.add((TextView) view.findViewById(R.id.auth_input_view5));
        inputViewList.add((TextView) view.findViewById(R.id.auth_input_view6));
        numberViewList.add(view.findViewById(R.id.num0));
        numberViewList.add(view.findViewById(R.id.num1));
        numberViewList.add(view.findViewById(R.id.num2));
        numberViewList.add(view.findViewById(R.id.num3));
        numberViewList.add(view.findViewById(R.id.num4));
        numberViewList.add(view.findViewById(R.id.num5));
        numberViewList.add(view.findViewById(R.id.num6));
        numberViewList.add(view.findViewById(R.id.num7));
        numberViewList.add(view.findViewById(R.id.num8));
        numberViewList.add(view.findViewById(R.id.num9));
        numberViewList.add(view.findViewById(R.id.delete));
        numberViewList.add(view.findViewById(R.id.ok_view));
        cancelView = view.findViewById(R.id.cancel_auth_view);
        setListener();
    }

    private void setListener() {
        cancelView.setOnClickListener(this);
        for (View view : numberViewList) {
            view.setOnClickListener(this);
        }

    }

    public void setMemorandumListener(MemorandumAuthListenerInterface listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ok_view) {
            String password = getPassWordString();
            dismiss();
            this.listener.authRight();
        } else if (v.getId() == R.id.cancel_auth_view) {
            dismiss();
            this.listener.cancelAuth();
        } else if (v.getId() == R.id.delete) {
            if (nowIndex > 0) nowIndex--;
            inputViewList.get(nowIndex).setText(null);
            inputValues[nowIndex] = null;
        } else if (nowIndex < 6) {
            switch (v.getId()) {
                case R.id.num0:
                    inputValues[nowIndex] = "0";
                    break;
                case R.id.num1:
                    inputValues[nowIndex] = "1";
                    break;
                case R.id.num2:
                    inputValues[nowIndex] = "2";
                    break;
                case R.id.num3:
                    inputValues[nowIndex] = "3";
                    break;
                case R.id.num4:
                    inputValues[nowIndex] = "4";
                    break;
                case R.id.num5:
                    inputValues[nowIndex] = "5";
                    break;
                case R.id.num6:
                    inputValues[nowIndex] = "6";
                    break;
                case R.id.num7:
                    inputValues[nowIndex] = "7";
                    break;
                case R.id.num8:
                    inputValues[nowIndex] = "8";
                    break;
                case R.id.num9:
                    inputValues[nowIndex] = "9";
                    break;
            }
            initInputViewValue();
        }
    }

    private void initInputViewValue() {
        inputViewList.get(nowIndex).setText(inputValues[nowIndex]);
        nowIndex++;
    }

    private String getPassWordString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String val : inputValues) {
            stringBuilder.append(val);
        }
        return stringBuilder.toString();
    }
}
