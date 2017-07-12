package com.jinqiang.Utils.progressDialog;

import android.content.Context;

import com.jinqiang.widgets.R;


/**
 * Created by jingqiang on 2017/4/10.
 */

public class ProgressBarUtils {
    private Context mContext;
    private BaseProgressDialog mDialog;


    public ProgressBarUtils(Context context) {
        this.mContext = context;
    }

    public ProgressBarUtils initProgress(){
        if(mDialog == null)
            mDialog = new BaseProgressDialog(mContext, R.style.progress_dialog);
        return this;
    }

    public ProgressBarUtils show(){
        if(mDialog != null) {
            mDialog.show();
        }else{
            mDialog = new BaseProgressDialog(mContext, R.style.progress_dialog);
            mDialog.show();
        }
        return this;
    }

    public ProgressBarUtils hide(){
        if(mDialog != null){
            mDialog.dismiss();
        }
        return this;
    }
}
