package com.wuyson.common.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Application 基类
 @date : 2018/5/18-15:24
 @author : Wuyson
 */
public class BaseApplication extends Application {
    private static BaseApplication baseApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }
    public static Context getAppContext() {
        return baseApplication;
    }
    public static Resources getAppResources() {
        return baseApplication.getResources();
    }
}
