package com.lilong.plugindemo.application;

import android.app.Application;
import android.content.Context;

import com.lilong.plugindemo.plugin.PluginManager;

/**
 * 主工程的application，其contextImpl中的classLoader和resource替换为兼容插件的classLoader和resource
 */

public class DemoApplication extends Application {

    private static DemoApplication sInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sInstance = this;
        PluginManager.getInstance().init();
        PluginManager.getInstance().updateContextToPluginCompatible(base, PluginManager.getInstance().getPluginCompatibleClassLoader(), PluginManager.getInstance().buildPluginCompatibleResources(base));
    }

    public static DemoApplication getInstance() {
        return sInstance;
    }

}
