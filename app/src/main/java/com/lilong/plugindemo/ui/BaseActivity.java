package com.lilong.plugindemo.ui;

import com.lilong.plugindemo.plugin.PluginManager;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lilong on 18-5-18.
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    public AssetManager getAssets() {
        return PluginManager.getInstance().getPluginAssetManager();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getPluginResources();
    }

    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = getResources().newTheme();
        theme.setTo();
        return super.getTheme();
    }
}
