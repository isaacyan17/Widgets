package com.jinqiang.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * @autor: legendary
 * @time: 2017/10/14 11:33
 */

public class SharedpreferencesUtils {

    /***
     * 用户信息是否失效
     * @return
     */
    public static boolean getLoginState(Context context){
        if (context == null)
            return false;
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String account = sp.getString("account", null);
        long stamp = sp.getLong("account_stamp", 0);
        long time = new Date().getTime();
        if(account != null)
            if ((time - stamp) / 1000 < 3600)
                return true;
        return false;
    }

    public static void setLoginState(Context context,String account){
        if(context == null)
            return ;
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("account", account);
        edit.putLong("account_stamp",new Date().getTime());
        edit.commit();
    }
}
