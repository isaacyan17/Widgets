package com.jinqiang.baselibrary.module;

import android.app.Application;
import android.content.Context;

/**
 * @author: jinqiang
 * @desc:
 */

public interface IAppLife {

    void attachBaseContext(Context base);

    void onCreate(Application application);

    void onTerminate(Application application);
}
