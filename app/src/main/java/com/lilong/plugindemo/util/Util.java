package com.lilong.plugindemo.util;

import android.widget.Toast;

import com.lilong.plugindemo.R;
import com.lilong.plugindemo.application.DemoApplication;

/**
 * 主工程中的类，插件可以调用
 */

public class Util {
    public static void showToast(){
        Toast.makeText(DemoApplication.getInstance(), R.string.main_project_toast_text, Toast.LENGTH_SHORT).show();
    }
}
