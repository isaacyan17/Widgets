package com.jinqiang.Utils.progressDialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.jinqiang.RecyclerViewRefresh.View.SimpleView;
import com.jinqiang.widgets.R;


/**
 * Created by jingqiang on 2017/3/22.
 */

public class BaseProgressDialog extends Dialog {

    private SimpleView progressView;
    private ProgressIndicator indicator;

    public BaseProgressDialog(Context context) {
        this(context,0);
    }

    public BaseProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.base_progressdialog_layout);
        //默认不能点击取消
        this.setCancelable(true);
        progressView = (SimpleView)findViewById(R.id.progressbar);
        indicator = new ProgressIndicator(getContext());
        indicator.setIndicatorColor(getContext().getResources().getColor(R.color.progressColor));
        progressView.setView(indicator);
    }

    protected BaseProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
