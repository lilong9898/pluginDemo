package com.lilong.plugin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lilong.plugindemo.util.Util;
import com.lilong.plugininterface.BasePluginFragment;

/**
 * 插件fragment
 * (1) 因为这个类是插件的入口点，由自定义的DexClassLoader加载，所以其引用的所有类，其中没被主工程类加载器加载的类(也就是插件中定义的类)，也由这个DexClassLoader加载，不需专门处理
 * 这是类加载器的委托机制决定的，所以自定义的DexClassLoader的加载类的方法，实际上可以加载任何主工程和插件中的类，而主工程的类加载器的加载类的方法，只能加载主工程的类
 * (2) 直接调用getResources方法，会最终调到hostActivity的getResources方法，而主工程中的getResources方法已经做了处理
 * 其返回的resources可以根据资源id分段来选择向主工程还是插件来获取资源id，所以可以获取到资源
 * 没有好的方法可以拦截这个过程，只能让主工程来处理了
 * (3) LayoutInflater的context换成插件的context，这样inflate出来的view内部的context也就都是插件的context了，这样才能读取插件xml中的资源
 * (4) getContext方法返回插件context，这样进一步的getResources或getClassLoader就可以获取到插件的资源或类加载器
 */

public class PluginFragment extends BasePluginFragment {

    private View fragmentRootView;

    private TextView tvTextByXml;
    private TextView tvTextBySelfSetText;
    private TextView tvTextByExternalSetText;

    private Button btnCallPluginProjectToast;
    private Button btnCallMainProjectToast;

    /**
     * 插件fragment的hostActivity仍然是主工程里的activity，
     * 但插件fragment的inflater用的context是主工程传来
     * 这个context实际上是主工程的activity被contextWrapper wrap了之后，修改成插件资源和主题的context
     */
    private Context mPluginContext;

    @Override
    public void setContext(Context context) {
        mPluginContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mPluginContext != null) {
            inflater = inflater.cloneInContext(mPluginContext);
        }
        fragmentRootView = inflater.inflate(R.layout.plugin_fragment, container, false);
        tvTextByXml = (TextView) fragmentRootView.findViewById(R.id.tvTextByXml);
        tvTextBySelfSetText = (TextView) fragmentRootView.findViewById(R.id.tvTextBySelfSetText);
        tvTextByExternalSetText = (TextView) fragmentRootView.findViewById(R.id.tvTextByExternalSetText);
        btnCallPluginProjectToast = (Button) fragmentRootView.findViewById(R.id.btnCallPluginProjectToast);
        btnCallMainProjectToast = (Button) fragmentRootView.findViewById(R.id.btnCallMainProjectToast);

        tvTextBySelfSetText.setText(R.string.tvTextBySelfSetText);
        tvTextByExternalSetText.setText(getResources().getString(R.string.tvTextByExternalSetText));
        btnCallPluginProjectToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.plugin_project_toast_text, Toast.LENGTH_SHORT).show();
            }
        });
        btnCallMainProjectToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showToast();
            }
        });
        return fragmentRootView;
    }

    // 通过getContext()来进一步getClassLoader, getResources得到的就是插件的类加载器和资源了
    @Override
    public Context getContext() {
        if (mPluginContext != null) {
            return mPluginContext;
        } else {
            return super.getContext();
        }
    }

}
