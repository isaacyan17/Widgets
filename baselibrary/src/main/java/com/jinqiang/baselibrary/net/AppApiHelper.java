package com.jinqiang.baselibrary.net;


import com.google.gson.Gson;
import com.jinqiang.baselibrary.net.apis.LoginServices;
import com.jinqiang.baselibrary.net.cache.CacheProviders;
import com.orhanobut.logger.Logger;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;

/**
 * @author: jinqiang
 * @time: 2018/4/3 15:48
 * @desc: 返回接口调用以后的observable
 */

@Singleton
public class AppApiHelper implements ApiHelper {
    private static final String TAG = AppApiHelper.class.getSimpleName();
    LoginServices loginServices;
    CacheProviders cacheProviders;

    @Inject
    public AppApiHelper(CacheProviders cacheProviders,LoginServices loginServices) {
        this.loginServices = loginServices;
        this.cacheProviders = cacheProviders;

//        this.cacheProviders = cacheProviders;
//        this.noLimit91PornServiceApi = noLimit91PornServiceApi;
//        this.forum91PronServiceApi = forum91PronServiceApi;
//        this.gitHubServiceApi = gitHubServiceApi;
//        this.meiZiTuServiceApi = meiZiTuServiceApi;
//        this.mm99ServiceApi = mm99ServiceApi;
//        this.pigAvServiceApi = pigAvServiceApi;
//        this.proxyServiceApi = proxyServiceApi;
//        this.addressHelper = addressHelper;
//        this.gson = gson;
    }

    @Override
    public Observable<String> login(String name, String password , boolean cleanCache) {
        Observable<String> userLogin = loginServices.login(name, password);


        return cacheProviders.getLoginCache(userLogin,new EvictProvider(cleanCache))
                .map(new Function<Reply<String>, String>() {
                    @Override
                    public String apply(Reply<String> stringReply) throws Exception {
                        switch (stringReply.getSource()){
                            case CLOUD:
                                Logger.t(TAG).d("数据：网络");
                                break;
                            case MEMORY:
                                Logger.t(TAG).d("数据：内存");
                                break;
                            case PERSISTENCE:
                                Logger.t(TAG).d("数据：存储");
                                break;
                            default:break;
                        }
                        return stringReply.getData();
                    }
                });
    }
}
