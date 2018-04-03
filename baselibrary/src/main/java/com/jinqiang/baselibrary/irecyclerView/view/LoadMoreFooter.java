package com.jinqiang.baselibrary.irecyclerView.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jinqiang.baselibrary.R;
import com.jinqiang.baselibrary.progressDialog.SimpleView;


public class LoadMoreFooter extends LinearLayout{
    public final static int STATE_LOADING = 0; //加载中
    public final static int STATE_COMPLETE = 1; //加载完成
    public final static int STATE_NOMORE = 2;  //没有更多数据

    private LinearLayout mContainer; //loadmore布局
    private TextView mLoadMoreText;
    private SimpleView mProgressBar;
    PullToRefreshIndicator mIndicator;  // progressBar的动画

    public LoadMoreFooter(Context context) {
        super(context);
        initView();
    }

    public LoadMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        // 将loadmore布局加载到这个View中
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.baselibrary_load_more_footer,null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,0,0);
        setLayoutParams(lp);
        setPadding(0,0,0,0);
        this.addView(mContainer,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.BOTTOM);
        // 初始化progressBar
        mLoadMoreText = (TextView) findViewById(R.id.loadmore_status_textview);
        mProgressBar = (SimpleView) findViewById(R.id.loadmore_progressbar);
        mIndicator = new PullToRefreshIndicator(getContext());
        mIndicator.setIndicatorColor(0xffB5B5B5);
        mProgressBar.setView(mIndicator);
        //测量一下添加布局后的view的宽高。
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    public void changeState(int state) {
        switch(state) {
            case STATE_LOADING:
                mProgressBar.setVisibility(View.VISIBLE);
                mLoadMoreText.setText(getContext().getText(R.string.listview_loading));
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mLoadMoreText.setText(getContext().getText(R.string.loading_done));
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mLoadMoreText.setText(getContext().getText(R.string.nomore_loading));
                mProgressBar.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }
}
