package com.jinqiang.baselibrary.utils;

import android.util.Log;

/**
 * Created by jingqiang on 2017/6/1.
 */

public class LogUtils {

    private  String logtag = "tag";


    private static LogUtils instance = new LogUtils();

    private LogUtils() {

    }

    public static LogUtils getLogger() {
        return instance;
    }


    public void i(String msg) {
        Log.i(logtag, createMessage(msg));
    }

    public void v(String msg) {
        Log.v(logtag,  createMessage(msg));
    }

    public void e(String msg) {
        Log.e(logtag, createMessage(msg));
    }


    private  String createMessage(String msg) {
        String functionName = getFunctionName();
        String message = (functionName == null ? msg : (functionName + " - " + msg));
        return message;
    }

    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }

            return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId()
                    + "): " +"(" + st.getFileName() + ":" + st.getLineNumber() + ")]";
        }

        return null;
    }

}

