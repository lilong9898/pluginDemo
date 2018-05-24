package com.lilong.plugindemo.plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.lilong.plugindemo.application.DemoApplication;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 插件管理器
 */

public class PluginManager {

    private ClassLoader mPluginClassLoader;
    private AssetManager mPluginAssetManager;
    private Resources mPluginResources;
    private Resources.Theme mPluginTheme;

    private static volatile PluginManager sInstance;

    private PluginManager() {
    }

    public static PluginManager getInstance() {
        if (sInstance == null) {
            synchronized (PluginManager.class) {
                if (sInstance == null) {
                    sInstance = new PluginManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 插件apk的名字
     */
    public static final String PLUGIN_APK_NAME = "plugin.apk";

    /**
     * 插件fragment的类名
     */
    public static final String PLUGIN_FRAGMENT_CLASS_NAME = "com.lilong.plugin.PluginFragment";

    /**
     * 插件apk要被复制到的目录绝对路径
     */
    public String getAppFileDirAbsPath() {
        return DemoApplication.getInstance().getFilesDir().getAbsolutePath();
    }

    /**
     * 插件apk要被复制到的位置绝对路径
     */
    public String getPluginApkDestAbsPath() {
        return getAppFileDirAbsPath() + File.separator + PLUGIN_APK_NAME;
    }

    public void init() {
        copyPluginApkFromAssetsToFileDir();
        mPluginClassLoader = buildPluginClassLoader();
        mPluginAssetManager = buildPluginAssetManager();
        mPluginResources = buildPluginResources();
        mPluginTheme = buildPluginTheme();
    }

    public ClassLoader getPluginClassLoader() {
        return mPluginClassLoader;
    }

    public AssetManager getPluginAssetManager() {
        return mPluginAssetManager;
    }

    public Resources getPluginResources() {
        return mPluginResources;
    }

    public Resources.Theme getPluginTheme() {
        return mPluginTheme;
    }

    /**
     * 将插件apk从assets目录拷贝到file目录
     */
    // TODO 放到工作线程中
    private void copyPluginApkFromAssetsToFileDir() {

        // 如果该位置已经有了，就不拷贝了
//        if (new File(getPluginApkDestAbsPath()).exists()) {
//            return;
//        }

        AssetManager assetManager = DemoApplication.getInstance().getAssets();
        InputStream in;
        BufferedInputStream bin;
        FileOutputStream fout;
        try {
            in = assetManager.open(PLUGIN_APK_NAME);
            bin = new BufferedInputStream(in);
            fout = new FileOutputStream(getPluginApkDestAbsPath());
            byte[] data = new byte[1024];
            int length = 0;
            while ((length = bin.read(data)) != -1) {
                fout.write(data, 0, length);
            }
            bin.close();
            fout.flush();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bin = null;
            fout = null;
        }
    }

    /**
     * 创建用于加载插件代码的类加载器
     */
    public ClassLoader buildPluginClassLoader() {
        return new DexClassLoader(getPluginApkDestAbsPath(), getAppFileDirAbsPath(), null, DemoApplication.getInstance().getClassLoader());
    }

    /**
     * 创建用于解析插件apk资源的assetManager
     */
    public AssetManager buildPluginAssetManager() {
        AssetManager pluginAssetManager = null;
        try {
            pluginAssetManager = AssetManager.class.newInstance();
            Method methodAddAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            methodAddAssetPath.setAccessible(true);
            methodAddAssetPath.invoke(pluginAssetManager, getPluginApkDestAbsPath());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return pluginAssetManager;
    }

    /**
     * 创建代表插件apk资源信息的resouces
     */
    public Resources buildPluginResources() {
        Resources appResources = DemoApplication.getInstance().getBaseContext().getResources();
        DisplayMetrics appDisplayMetrics = appResources.getDisplayMetrics();
        Configuration appConfiguration = appResources.getConfiguration();
        Resources pluginResources = new Resources(mPluginAssetManager, appDisplayMetrics, appConfiguration);
        return pluginResources;
    }

    /**
     * 创建插件的theme
     */
    public Resources.Theme buildPluginTheme() {
        return getPluginResources().newTheme();
    }

    /**
     * 建立给插件fragment用的context
     */
    public PluginFragmentContext buildPluginFragmentContext(Context hostContext) {
        return new PluginFragmentContext(hostContext, getPluginClassLoader(), getPluginResources(), getPluginTheme());
    }
}
