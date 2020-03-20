package com.lilong.plugindemo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lilong.plugindemo.R;
import com.lilong.plugindemo.application.DemoApplication;
import com.lilong.plugindemo.plugin.PluginManager;
import com.lilong.plugininterface.IPluginFragment;

public class MainActivity extends BaseActivity {

    private Button mBtnMainCallPluginMethod;
    private Button mBtnMainGetDemoApplicationInstance;
    private TextView tvTextUsePluginResource;

    private static final String CLASS_LOADER_TEST_DEMO_APPLICATION_CLASS_NAME = "com.lilong.plugindemo.application.DemoApplication";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnMainCallPluginMethod = (Button) findViewById(R.id.btnMainCallPluginMethod);
        tvTextUsePluginResource = (TextView) findViewById(R.id.tvTextUsePluginResource);
        mBtnMainCallPluginMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment pluginFragment = getSupportFragmentManager().findFragmentById(R.id.layoutFragContainer);
                ((IPluginFragment) pluginFragment).callPluginMethod();
            }
        });
        mBtnMainGetDemoApplicationInstance = (Button) findViewById(R.id.btnMainGetDemoApplicationInstance);
        mBtnMainGetDemoApplicationInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class c = null;
                try {
                    c = getClassLoader().loadClass(CLASS_LOADER_TEST_DEMO_APPLICATION_CLASS_NAME);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                DemoApplication instance = DemoApplication.getInstance();
                Toast.makeText(MainActivity.this, "Class object = " + c + ", \ninstance = " + instance, Toast.LENGTH_LONG).show();
            }
        });
        tvTextUsePluginResource.setText(0x71109998);
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
