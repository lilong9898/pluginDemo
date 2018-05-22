package com.lilong.plugin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 插件fragment
 */

public class PluginFragment extends Fragment {

    private View fragmentRootView;
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentRootView = inflater.inflate(R.layout.plugin_fragment, container, false);
        tv = (TextView) fragmentRootView.findViewById(R.id.tv);
//        tv.setText(R.string.tv_text);
        return fragmentRootView;
    }
}
