package com.jinqiang.baselibrary.net.cache;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.ProviderKey;
import io.rx_cache2.Reply;

/**
 * @author: jinqiang
 * @time: 2018/4/4 09:25
 * @desc: RxJavaCache 缓存
 */

public interface CacheProviders {
    /**
     * 缓存自动过期时间15分钟
     */
    int CACHE_TIME = 15;

    /**
     * 缓存登录信息
     * @param loginCache
     * @param evictProvider
     * @return
     */
    @ProviderKey("loginCache")
    @LifeCache(duration = CACHE_TIME, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<String>> getLoginCache(Observable<String> loginCache, EvictProvider evictProvider);

}
