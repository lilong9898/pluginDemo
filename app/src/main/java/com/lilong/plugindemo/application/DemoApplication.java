package com.lilong.plugindemo.application;

import com.lilong.plugindemo.plugin.PluginManager;

import android.app.Application;
import android.content.Context;

/**
 * Created by lilong on 18-5-18.
 */

public class DemoApplication extends Application {

    private static DemoApplication sInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
        PluginManager.getInstance().init();
    }

    public static DemoApplication getInstance(){
        return sInstance;
    }

}
