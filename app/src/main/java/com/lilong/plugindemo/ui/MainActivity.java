package com.lilong.plugindemo.ui;

import android.app.Fragment;
import android.os.Bundle;

import com.lilong.plugindemo.R;
import com.lilong.plugindemo.plugin.PluginManager;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Class c = PluginManager.getInstance().getPluginClassLoader().loadClass(PluginManager.PLUGIN_FRAGMENT_CLASS_NAME);
            getFragmentManager().beginTransaction().replace(R.id.layoutFragContainer, ((Fragment) c.newInstance())).commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
