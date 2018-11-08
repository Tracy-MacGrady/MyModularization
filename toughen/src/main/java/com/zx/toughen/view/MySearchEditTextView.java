package com.zx.toughen.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toughen.libs.tools.AppUtils;
import com.zx.toughen.R;

public class MySearchEditTextView extends RelativeLayout implements View.OnClickListener, TextView.OnEditorActionListener {
    private OnSearchListener listener;
    private ImageView searchImgView;
    private EditText editText;

    public MySearchEditTextView(Context context) {
        super(context);
        init();
    }

    public MySearchEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MySearchEditTextView);
        editText.setTextColor(array.getColor(R.styleable.MySearchEditTextView_text_color, 0));
        editText.setHintTextColor(array.getColor(R.styleable.MySearchEditTextView_text_hint_color, 0));
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.MySearchEditTextView_text_size, 0));
        editText.setText(array.getString(R.styleable.MySearchEditTextView_text));
        editText.setHint(array.getString(R.styleable.MySearchEditTextView_text_hint));
        array.recycle();
    }

    public MySearchEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MySearchEditTextView, defStyleAttr, 0);
        editText.setTextColor(array.getColor(R.styleable.MySearchEditTextView_text_color, 0));
        editText.setHintTextColor(array.getColor(R.styleable.MySearchEditTextView_text_hint_color, 0));
        editText.setTextSize(array.getDimension(R.styleable.MySearchEditTextView_text_size, 0));
        editText.setText(array.getString(R.styleable.MySearchEditTextView_text));
        editText.setHint(array.getString(R.styleable.MySearchEditTextView_text_hint));
        array.recycle();

    }

    private void init() {
        View.inflate(getContext(), R.layout.view_search_edittext, this);
        searchImgView = findViewById(R.id.search_imgview);
        editText = findViewById(R.id.search_view);
        searchImgView.setOnClickListener(this);
        editText.setOnEditorActionListener(this);
    }

    public void setSearchListener(OnSearchListener searchListener) {
        this.listener = searchListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_imgview:
                if (editText.hasFocus())
                    AppUtils.getInstance().hideKeyboard((Activity) getContext(), editText);
                if (listener != null) listener.toSearch(editText.getText().toString());
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (editText.hasFocus())
                AppUtils.getInstance().hideKeyboard((Activity) getContext(), editText);
            if (listener != null) listener.toSearch(editText.getText().toString());
            return true;
        }
        return false;
    }

    public interface OnSearchListener {
        void toSearch(String searchTipValue);
    }
}
