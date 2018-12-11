package com.wuyson.common.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.wuyson.common.app.AppManager;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupPreContentView();
        setContentView(setupContentView());
        setupView();
    }

    protected void setupPreContentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题(1.在AppCompatActivity是无效的，继承Activity才有效，可以设置Application主题去掉Actionbar)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // TODO: 2018/12/11 状态栏刘海适配
    }

    @LayoutRes
    protected abstract int setupContentView();

    protected abstract void setupView();



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
