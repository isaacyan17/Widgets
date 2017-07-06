package com.jinqiang.materialLogin.net;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jingqiang on 2017/7/6.
 */

public interface LoginNet {
    //登录
    @GET("login")
    Observable<String> login(@Query("name") String name,@Query("password") String password);
}
