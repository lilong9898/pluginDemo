package com.lilong.plugindemo.plugin;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;

/**
 * Created by lilong on 18-5-23.
 */

public class PluginFragmentContext extends ContextWrapper {

    private Resources mPluginFragmentResources;
    private Resources.Theme mPluginFragmentTheme;

    public PluginFragmentContext(Context baseContext, Resources pluginFragmentResources, Resources.Theme pluginFragmentTheme){
        super(baseContext);
        mPluginFragmentResources = pluginFragmentResources;
        mPluginFragmentTheme = pluginFragmentTheme;
    }

    @Override
    public Resources getResources() {
        return mPluginFragmentResources;
    }

    @Override
    public Resources.Theme getTheme() {
        return mPluginFragmentTheme;
    }
}
