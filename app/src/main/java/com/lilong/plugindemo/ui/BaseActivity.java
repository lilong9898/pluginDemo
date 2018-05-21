package com.lilong.plugindemo.ui;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

import com.lilong.plugindemo.plugin.PluginManager;

/**
 * Created by lilong on 18-5-18.
 */

public class BaseActivity extends AppCompatActivity{

    private Context mBaseContext;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        mBaseContext = newBase;
    }

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
        return super.getTheme();
    }
}
