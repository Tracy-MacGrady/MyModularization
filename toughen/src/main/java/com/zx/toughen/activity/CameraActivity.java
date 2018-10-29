package com.zx.toughen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zx.toughen.base.BaseAppCompatActivity;
import com.zx.toughen.R;
import com.zx.toughen.view.cameraview.CameraPreviewView;

/**
 * Created by 李健健 on 2017/4/17.
 */

public class CameraActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private CameraPreviewView cameraPreviewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        setListener();
    }

    @Override
    public void initView() {
        cameraPreviewView = (CameraPreviewView) findViewById(R.id.cameraPreviewView);
    }

    @Override
    public void setListener() {
        findViewById(R.id.take_picture_view).setOnClickListener(this);
        findViewById(R.id.cancel_view).setOnClickListener(this);
        findViewById(R.id.album_view).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_picture_view:
                if (cameraPreviewView != null) cameraPreviewView.takePicture();
                break;
            case R.id.cancel_view:
                finish();
                break;
            case R.id.album_view:
                Intent intent=new Intent();
                break;
        }
    }
}
