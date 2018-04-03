package com.jinqiang.baselibrary;

import android.app.Application;
import android.content.Context;

import com.jinqiang.baselibrary.module.IAppLife;
import com.jinqiang.baselibrary.module.IModuleConfig;
import com.jinqiang.baselibrary.utils.ManifestParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jinqiang
 * @time: 2018/3/1 10:53
 * @desc:
 */

public class ApplicationDelegate implements IAppLife{
    private List<IModuleConfig> list;
    private List<IAppLife> appLifes;
    private List<Application.ActivityLifecycleCallbacks> liferecycleCallbacks;


    public ApplicationDelegate() {
        appLifes = new ArrayList<>();
        liferecycleCallbacks = new ArrayList<>();
    }

    @Override
    public void attachBaseContext(Context base) {
        ManifestParser manifestParser = new ManifestParser(base);
        list = manifestParser.parse();
        if (list != null && list.size() > 0) {
            for (IModuleConfig configModule :
                    list) {
                configModule.injectAppLifecycle(base, appLifes);
                configModule.injectActivityLifecycle(base, liferecycleCallbacks);
            }
        }
        if (appLifes != null && appLifes.size() > 0) {
            for (IAppLife life :
                    appLifes) {
                life.attachBaseContext(base);
            }
        }
    }

    @Override
    public void onCreate(Application application) {
        if (appLifes != null && appLifes.size() > 0) {
            for (IAppLife life :
                    appLifes) {
                life.onCreate(application);
            }
        }
        if (liferecycleCallbacks != null && liferecycleCallbacks.size() > 0) {
            for (Application.ActivityLifecycleCallbacks life :
                    liferecycleCallbacks) {
                application.registerActivityLifecycleCallbacks(life);
            }
        }
    }

    @Override
    public void onTerminate(Application application) {
        if (appLifes != null && appLifes.size() > 0) {
            for (IAppLife life :
                    appLifes) {
                life.onTerminate(application);
            }
        }
        if (liferecycleCallbacks != null && liferecycleCallbacks.size() > 0) {
            for (Application.ActivityLifecycleCallbacks life :
                    liferecycleCallbacks) {
                application.unregisterActivityLifecycleCallbacks(life);
            }
        }
    }
}
