package com.lilong.plugindemo.ui;

import android.app.Activity;
import android.content.res.Resources;

import com.lilong.plugindemo.plugin.PluginManager;

/**
 * 基类
 */

public class BaseActivity extends Activity {

    /**
     * 使用代理resources来处理这个activity收到的资源请求
     * 代理resources可以根据资源id的package段的数字来区分是进一步请求主工程资源还是插件资源
     * */
    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getProxyResources();
    }

}
