package com.lilong.plugindemo.ui;

import android.app.Activity;
import android.content.res.Resources;

import com.lilong.plugindemo.plugin.PluginManager;

/**
 * Created by lilong on 18-5-18.
 */

public class BaseActivity extends Activity {

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getProxyResources();
    }

    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = getResources().newTheme();
        theme.setTo(super.getTheme());
        return theme;
    }
}
