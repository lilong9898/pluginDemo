package com.lilong.plugindemo.plugin;

import android.content.Context;

import com.lilong.plugindemo.application.PluginDemoApplication;

/**
 * Created by lilong on 18-5-18.
 */

public class PluginConfig {

    /** */
    /** 插件apk的绝对路径*/
    public static final String PLUGIN_APK_ABS_PATH = "file:///android_asset/plugin.apk";

    /** 插件apk被解压后存放odex的目录*/
    public static final String PLUGIN_APK_ODEX_ABS_PATH = PluginDemoApplication.getInstance().getDir("plugin apk odex", Context.MODE_PRIVATE).getAbsolutePath();
}
