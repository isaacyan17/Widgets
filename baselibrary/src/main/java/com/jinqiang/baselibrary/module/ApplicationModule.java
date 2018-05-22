package com.jinqiang.baselibrary.module;

import android.app.Application;

import com.jinqiang.baselibrary.net.ApiHelper;
import com.jinqiang.baselibrary.net.AppApiHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author: jinqiang
 * @time: 2018/4/3 15:07
 * @desc:
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    public Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    ApiHelper providesApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }
}
