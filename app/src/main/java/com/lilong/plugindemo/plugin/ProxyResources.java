package com.lilong.plugindemo.plugin;

import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 通过静态代理模式来处理资源请求，按照资源id分段来决定是转发请求给主工程resources还是插件resources
 * 按照主工程resource的初始化方法来初始化本类，使得在本类未规定的情况下都走主工程Resources
 */

public class ProxyResources extends Resources {

    private Resources mMainResources;
    private Resources mPluginResources;

    private static final int RES_ID_RANGE_MAIN_MIN = 0x7f000000;
    private static final int RES_ID_RANGE_MAIN_MAX = 0x7fffffff;

    public ProxyResources(AssetManager assets, DisplayMetrics metrics, Configuration config, Resources mainResources, Resources pluginResources) {
        super(assets, metrics, config);
        mMainResources = mainResources;
        mPluginResources = pluginResources;
    }

    /**
     * 是否为主工程的资源id
     */
    private boolean isMainResourceId(int id) {
        return id >= RES_ID_RANGE_MAIN_MIN && id <= RES_ID_RANGE_MAIN_MAX;
    }

    @Override
    public void getValue(int id, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
        if (isMainResourceId(id)) {
            mMainResources.getValue(id, outValue, resolveRefs);
        } else {
            mPluginResources.getValue(id, outValue, resolveRefs);
        }
    }

    @Override
    public void getValue(String name, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
        try {
            mMainResources.getValue(name, outValue, resolveRefs);
        } catch (Exception e) {
            mPluginResources.getValue(name, outValue, resolveRefs);
        }
    }

    @Override
    public XmlResourceParser getLayout(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getLayout(id);
        } else {
            return mPluginResources.getLayout(id);
        }
    }

    @Override
    public float getDimension(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getDimension(id);
        } else {
            return mPluginResources.getDimension(id);
        }
    }

    @Override
    public int getDimensionPixelOffset(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getDimensionPixelOffset(id);
        } else {
            return mPluginResources.getDimensionPixelOffset(id);
        }
    }

    @Override
    public int getDimensionPixelSize(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getDimensionPixelSize(id);
        } else {
            return mPluginResources.getDimensionPixelSize(id);
        }
    }

    @Override
    public int getColor(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getColor(id);
        } else {
            return mPluginResources.getColor(id);
        }
    }

    @Override
    public ColorStateList getColorStateList(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getColorStateList(id);
        } else {
            return mPluginResources.getColorStateList(id);
        }
    }

    @Override
    public Drawable getDrawable(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getDrawable(id);
        } else {
            return mPluginResources.getDrawable(id);
        }
    }

    @Override
    public XmlResourceParser getAnimation(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getAnimation(id);
        } else {
            return mPluginResources.getAnimation(id);
        }
    }

    @Override
    public XmlResourceParser getXml(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getXml(id);
        } else {
            return mPluginResources.getXml(id);
        }
    }

    @Override
    public String getString(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getString(id);
        } else {
            return mPluginResources.getString(id);
        }
    }

    @Override
    public String getString(int id, Object... formatArgs) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getString(id, formatArgs);
        } else {
            return mPluginResources.getString(id, formatArgs);
        }
    }

    @Override
    public String[] getStringArray(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getStringArray(id);
        } else {
            return mPluginResources.getStringArray(id);
        }
    }

    @Override
    public CharSequence getText(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getString(id);
        } else {
            return mPluginResources.getString(id);
        }
    }

    @Override
    public CharSequence getText(int id, CharSequence def) {
        if (isMainResourceId(id)) {
            return mMainResources.getString(id, def);
        } else {
            return mPluginResources.getString(id, def);
        }
    }

    @Override
    public int getInteger(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getInteger(id);
        } else {
            return mPluginResources.getInteger(id);
        }
    }

    @Override
    public boolean getBoolean(int id) throws NotFoundException {
        if (isMainResourceId(id)) {
            return mMainResources.getBoolean(id);
        } else {
            return mPluginResources.getBoolean(id);
        }
    }

    @Override
    public String getResourceName(int resid) throws NotFoundException {
        if (isMainResourceId(resid)) {
            return mMainResources.getResourceName(resid);
        } else {
            return mPluginResources.getString(resid);
        }
    }

    @Override
    public String getResourcePackageName(int resid) throws NotFoundException {
        if (isMainResourceId(resid)) {
            return mMainResources.getResourcePackageName(resid);
        } else {
            return mPluginResources.getResourcePackageName(resid);
        }
    }

    @Override
    public String getResourceTypeName(int resid) throws NotFoundException {
        if (isMainResourceId(resid)) {
            return mMainResources.getResourceTypeName(resid);
        } else {
            return mPluginResources.getResourceTypeName(resid);
        }
    }

    @Override
    public String getResourceEntryName(int resid) throws NotFoundException {
        if (isMainResourceId(resid)) {
            return mMainResources.getResourceEntryName(resid);
        } else {
            return mPluginResources.getResourceEntryName(resid);
        }
    }
}
