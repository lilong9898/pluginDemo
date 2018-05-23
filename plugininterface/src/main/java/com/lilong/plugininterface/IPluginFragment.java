package com.lilong.plugininterface;

import android.content.Context;

/**
 * 主工程向插件发送信息的接口
 */

public interface IPluginFragment {

    /** 给pluginFragment设置其layoutInflater用的context*/
    void setContext(Context context);

}
