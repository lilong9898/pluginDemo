package com.lilong.plugindemo.plugin;

import com.lilong.plugindemo.application.PluginDemoApplication;

import dalvik.system.DexClassLoader;

/**
 * 插件管理器
 */

public class PluginManager {

    private DexClassLoader mPluginClassLoader;

    private static volatile PluginManager sInstance;

    private PluginManager(){
        mPluginClassLoader = new DexClassLoader(PluginConfig.PLUGIN_APK_ABS_PATH, PluginConfig.PLUGIN_APK_ODEX_ABS_PATH, null, PluginDemoApplication.getInstance().getClassLoader());
    }

    public static PluginManager getInstance(){
        if(sInstance == null){
            synchronized (PluginManager.class){
                if(sInstance == null){
                    sInstance = new PluginManager();
                }
            }
        }
        return sInstance;
    }

    public DexClassLoader getPluginClassLoader(){
        return mPluginClassLoader;
    }
}
