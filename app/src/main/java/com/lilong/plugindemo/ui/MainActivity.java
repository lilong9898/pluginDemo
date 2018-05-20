package com.lilong.plugindemo.ui;

import com.lilong.plugindemo.R;
import com.lilong.plugindemo.plugin.PluginManager;

import android.os.Bundle;
import android.widget.Toast;

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
            Toast.makeText(this, "className = " + c.getName(), Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
