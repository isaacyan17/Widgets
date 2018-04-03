package com.jinqiang.baselibrary;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author: jinqiang
 * @time: 2018/3/1 10:48
 * @desc:
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
    private ApplicationDelegate applicationDelegate;


    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        applicationDelegate = new ApplicationDelegate();
        applicationDelegate.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();
        ARouter.init(this);
        instance = this;
        applicationDelegate.onCreate(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        applicationDelegate.onTerminate(this);
    }


}
