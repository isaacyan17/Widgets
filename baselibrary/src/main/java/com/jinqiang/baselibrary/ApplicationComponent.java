package com.jinqiang.baselibrary;

import android.content.Context;

import com.jinqiang.baselibrary.module.ApiServiceModule;
import com.jinqiang.baselibrary.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author: jinqiang
 * @time: 2018/4/4 14:48
 * @desc:
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiServiceModule.class})
public interface ApplicationComponent {
    void inject(BaseApplication baseApplication);

//    @ApplicationContext
//    Context getContext();
}
