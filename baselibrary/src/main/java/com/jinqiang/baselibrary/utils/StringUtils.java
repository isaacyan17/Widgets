package com.jinqiang.baselibrary.utils;

/**
 * Created by jingqiang on 2017/7/6.
 */

public class StringUtils {

    /**
     * Determine whether the string is empty
     * @param string Need to determine the string
     * @return true:null ,false:not null
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0||string.equals("null");
    }

}
