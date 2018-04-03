package com.jinqiang.example;

import android.app.Application;
import android.content.Context;

import com.jinqiang.baselibrary.module.IAppLife;
import com.jinqiang.baselibrary.module.IModuleConfig;

import java.util.List;

/**
 * @author: jinqiang
 * @time: 2018/3/2 11:37
 * @desc:
 */

public class ExampleApplication implements IModuleConfig,IAppLife {
    @Override
    public void attachBaseContext(Context base) {

    }

    @Override
    public void onCreate(Application application) {

    }

    @Override
    public void onTerminate(Application application) {

    }

    @Override
    public void injectAppLifecycle(Context context, List<IAppLife> iAppLifes) {
        iAppLifes.add(this);
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycleCallbackses) {

    }
}
