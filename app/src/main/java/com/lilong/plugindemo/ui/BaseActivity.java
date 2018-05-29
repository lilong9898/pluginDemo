package com.lilong.plugindemo.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.lilong.plugindemo.plugin.PluginManager;

/**
 * 主工程的基类activity，其contextImpl中的classLoader和resource替换为兼容插件的classLoader和resource
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        PluginManager.getInstance().updateContextToPluginCompatible(newBase, PluginManager.getInstance().getPluginCompatibleClassLoader(), PluginManager.getInstance().buildPluginCompatibleResources(newBase));
    }



}
