package com.jinqiang.baselibrary.net;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author: jinqiang
 * @time: 2018/4/3 15:12
 * @desc: 所有api的集合
 */

public interface ApiHelper {
    //登录
    Observable<String> login(String name, String password, boolean cleanCache);


}
