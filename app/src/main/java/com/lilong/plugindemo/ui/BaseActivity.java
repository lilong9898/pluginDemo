package com.lilong.plugindemo.ui;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.lilong.plugindemo.plugin.PluginManager;

/**
 * Created by lilong on 18-5-18.
 */

public class BaseActivity extends Activity {

    @Override
    public AssetManager getAssets() {
        return PluginManager.getInstance().getPluginAssetManager();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getPluginResources();
    }

//    @Override
//    public Resources.Theme getTheme() {
        // TODO theme的替换仍然不知原理，这么替换可行
//        Resources.Theme theme = mBaseContext.getTheme();
//        theme.applyStyle(R.style.AppTheme, false);
//        return theme;
//    }
}
