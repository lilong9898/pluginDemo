package com.lilong.plugindemo.application;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.lilong.plugindemo.plugin.PluginManager;
import com.lilong.plugindemo.plugin.ProxyResources;

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

    private Resources mProxyResources;

    /**
     * 创建Resources代理以便同时访问主工程和插件资源
     * 每个activity的getResources方法返回的resources都是不同的实例，因为每个activity都是不同的context，可以单独设置主题
     * 所以这个操作要在activity里做
     */
    public Resources buildProxyResources() {
        AssetManager activityAssetManager = super.getAssets();
        Resources activityResources = super.getResources();
        DisplayMetrics activityDisplayMetrics = activityResources.getDisplayMetrics();
        Configuration activityConfiguration = activityResources.getConfiguration();
        Resources pluginResources = PluginManager.getInstance().getPluginResources();
        return new ProxyResources(activityAssetManager, activityDisplayMetrics, activityConfiguration, activityResources, pluginResources);
    }

    /**
     * 使用代理resources来处理这个activity收到的资源请求
     * 代理resources可以根据资源id的package段的数字来区分是进一步请求主工程资源还是插件资源
     */
    @Override
    public Resources getResources() {
        if (mProxyResources == null) {
            mProxyResources = buildProxyResources();
        }
        return mProxyResources;
    }
}
