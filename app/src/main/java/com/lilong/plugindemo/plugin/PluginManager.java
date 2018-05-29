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
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 插件管理器
 */

public class PluginManager {

    private ClassLoader mPluginCompatibleClassLoader;

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
        mPluginCompatibleClassLoader = buildPluginCompatibleClassLoader();

    }

    public ClassLoader getPluginCompatibleClassLoader() {
        return mPluginCompatibleClassLoader;
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

    /** ---------------------------------同时加载主工程和插件的代码和资源-------------------------------------*/

    /**
     * 创建用于加载插件代码的类加载器，因为类加载的委托机制，所以它可以加载主工程的代码
     */
    public ClassLoader buildPluginCompatibleClassLoader() {
        return new DexClassLoader(getPluginApkDestAbsPath(), getAppFileDirAbsPath(), null, DemoApplication.getInstance().getClassLoader());
    }

    /**
     * 创建可以同时解析主工程和插件资源的assetManager
     * */
    public AssetManager buildPluginCompatibleAssetManager(Context baseContext){
        AssetManager pluginCompatibleAssetManager = baseContext.getAssets();
        try {
            Method methodAddAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            methodAddAssetPath.setAccessible(true);
            methodAddAssetPath.invoke(pluginCompatibleAssetManager, getPluginApkDestAbsPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pluginCompatibleAssetManager;
    }

    /**
     * 创建可以同时解析主工程和插件资源的Resources
     * */
    public Resources buildPluginCompatibleResources(Context baseContext) {
        Resources baseResources = baseContext.getResources();
        DisplayMetrics appDisplayMetrics = baseResources.getDisplayMetrics();
        Configuration appConfiguration = baseResources.getConfiguration();
        return new Resources(buildPluginCompatibleAssetManager(baseContext), appDisplayMetrics, appConfiguration);
    }

    /** -----------------------------替换某个contextImpl中的mResources--------------------*/
    public void updateContextToPluginCompatible(Context baseContext, ClassLoader pluginCompatibleClassLoader, Resources pluginCompatibleResources){
        // 替换baseContext中的mResources，这样就足够支持同时访问主工程和插件资源了，为了保险把baseContext中的 mPackageInfo中的mResources也换掉
        Tools.setField(baseContext, "mResources", pluginCompatibleResources);
        Object loadedApk = Tools.getField(baseContext, "mPackageInfo");
        Tools.setField(loadedApk, "mResources", pluginCompatibleResources);
        // 替换baseContext中的classLoader，方法是替换其mPackageInfo中的classLoader
        Tools.setField(loadedApk, "mClassLoader", pluginCompatibleClassLoader);
    }
}
