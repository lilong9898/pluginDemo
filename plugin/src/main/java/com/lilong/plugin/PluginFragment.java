package com.lilong.plugin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 插件fragment
 */

public class PluginFragment extends Fragment {

    private View fragmentRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentRootView = inflater.inflate(R.layout.plugin_fragment, container, false);
        return fragmentRootView;
    }
}
