package com.jinqiang.baselibrary.module;

import android.app.Application;
import android.content.Context;

import java.util.List;

/**
 * @author: jinqiang
 * @time: 2018/3/1 11:00
 * @desc:
 */

public interface IModuleConfig {
    void injectAppLifecycle(Context context, List<IAppLife> iAppLifes);

    void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycleCallbackses);
}

