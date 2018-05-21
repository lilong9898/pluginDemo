package com.lilong.plugindemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import com.lilong.plugindemo.R;
import com.lilong.plugindemo.plugin.PluginManager;

public class MainActivity extends BaseActivity {

    private ViewGroup layoutFragContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutFragContainer = (ViewGroup) findViewById(R.id.layoutFragContainer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Class c = PluginManager.getInstance().getPluginClassLoader().loadClass(PluginManager.PLUGIN_FRAGMENT_CLASS_NAME);
            getSupportFragmentManager().beginTransaction().add(R.id.layoutFragContainer, ((Fragment) c.newInstance())).commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
