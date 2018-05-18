package com.lilong.plugindemo.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by lilong on 18-5-18.
 */

public class PluginDemoApplication extends Application {

    private static PluginDemoApplication sInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
    }

    public static PluginDemoApplication getInstance(){
        return sInstance;
    }

}
