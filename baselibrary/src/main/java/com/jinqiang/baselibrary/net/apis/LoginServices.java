package com.jinqiang.baselibrary.net.apis;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author: jinqiang
 * @time: 2018/4/3 15:53
 * @desc:
 */

public interface LoginServices {
    //登录
    @GET("login")
    Observable<String> login(@Query("name") String name, @Query("password") String password);
}
