package com.lilong.plugin;

import android.app.Fragment;
import android.content.Context;
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

    private TextView tvTextByXml;
    private TextView tvTextBySelfSetText;
    private TextView tvTextByExternalSetText;

    /**
     * 插件fragment的hostActivity仍然是主工程里的activity，
     * 但插件fragment的inflater用的context是主工程传来
     * 这个context实际上是主工程的activity被contextWrapper wrap了之后，修改成插件资源和主题的context
     * */
    private Context mPluginContext;

    public void setContext(Context context){
        mPluginContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mPluginContext != null){
            inflater = inflater.cloneInContext(mPluginContext);
        }
        fragmentRootView = inflater.inflate(R.layout.plugin_fragment, container, false);
        tvTextByXml = (TextView) fragmentRootView.findViewById(R.id.tvTextByXml);
        tvTextBySelfSetText = (TextView) fragmentRootView.findViewById(R.id.tvTextBySelfSetText);
        tvTextByExternalSetText = (TextView) fragmentRootView.findViewById(R.id.tvTextByExternalSetText);

        tvTextBySelfSetText.setText(R.string.tvTextBySelfSetText);
        tvTextByExternalSetText.setText(getResources().getString(R.string.tvTextByExternalSetText));

        return fragmentRootView;
    }
}
