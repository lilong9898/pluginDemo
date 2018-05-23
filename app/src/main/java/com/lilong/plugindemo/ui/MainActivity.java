package com.lilong.plugindemo.ui;

import android.app.Fragment;
import android.os.Bundle;

import com.lilong.plugindemo.R;
import com.lilong.plugindemo.plugin.PluginManager;
import com.lilong.plugininterface.IPluginFragment;

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
            Fragment pluginFragment = (Fragment) c.newInstance();
            ((IPluginFragment) pluginFragment).setContext(PluginManager.getInstance().buildPluginFragmentContext(this));
            getFragmentManager().beginTransaction().replace(R.id.layoutFragContainer, pluginFragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
