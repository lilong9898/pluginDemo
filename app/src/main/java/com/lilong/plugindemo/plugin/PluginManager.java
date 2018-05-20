package com.lilong.plugindemo.plugin;

import com.lilong.plugindemo.application.PluginDemoApplication;

import android.content.res.AssetManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import dalvik.system.DexClassLoader;

/**
 * 插件管理器
 */

public class PluginManager {

    private DexClassLoader mPluginClassLoader;

    private static volatile PluginManager sInstance;

    private PluginManager(){
    }

    public static PluginManager getInstance(){
        if(sInstance == null){
            synchronized (PluginManager.class){
                if(sInstance == null){
                    sInstance = new PluginManager();
                }
            }
        }
        return sInstance;
    }

    /** 插件apk的名字*/
    public static final String PLUGIN_APK_NAME = "plugin.apk";

    /** 插件fragment的类名*/
    public static final String PLUGIN_FRAGMENT_CLASS_NAME = "com.lilong.plugin.PluginFragment";

    /** 插件apk要被复制到的目录绝对路径*/
    public String getAppFileDirAbsPath(){
        return PluginDemoApplication.getInstance().getFilesDir().getAbsolutePath();
    }

    /** 插件apk要被复制到的位置绝对路径*/
    public String getPluginApkDestAbsPath(){
        return getAppFileDirAbsPath() + File.separator + PLUGIN_APK_NAME;
    }

    public void init(){
        copyPluginApkFromAssetsToFileDir();
        mPluginClassLoader = new DexClassLoader(getPluginApkDestAbsPath(), getAppFileDirAbsPath(), null, PluginDemoApplication.getInstance().getClassLoader());
    }

    public DexClassLoader getPluginClassLoader(){
        copyPluginApkFromAssetsToFileDir();
        return mPluginClassLoader;
    }

    /** 将插件apk从assets目录拷贝到file目录*/
    // TODO 放到工作线程中
    private void copyPluginApkFromAssetsToFileDir() {

        // 如果该位置已经有了，就不拷贝了
        if(new File(getPluginApkDestAbsPath()).exists()){
            return;
        }

        AssetManager assetManager = PluginDemoApplication.getInstance().getAssets();
        InputStream in;
        BufferedInputStream bin;
        FileOutputStream fout;
        try {
            in = assetManager.open(PLUGIN_APK_NAME);
            bin = new BufferedInputStream(in);
            fout = new FileOutputStream(getPluginApkDestAbsPath());
            byte[] data = new byte[1024];
            int length = 0;
            while((length = bin.read(data)) != -1){
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
}
