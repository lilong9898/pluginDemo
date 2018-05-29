package com.lilong.plugindemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.lilong.plugindemo.R;
import com.lilong.plugindemo.plugin.PluginManager;
import com.lilong.plugininterface.IPluginFragment;

public class MainActivity extends BaseActivity {

    private Button mBtnMainCallPluginMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnMainCallPluginMethod = (Button) findViewById(R.id.btnMainCallPluginMethod);
        mBtnMainCallPluginMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment pluginFragment = getSupportFragmentManager().findFragmentById(R.id.layoutFragContainer);
                ((IPluginFragment) pluginFragment).callPluginMethod();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Class c = getClassLoader().loadClass(PluginManager.PLUGIN_FRAGMENT_CLASS_NAME);
            Fragment pluginFragment = (Fragment) c.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutFragContainer, pluginFragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
