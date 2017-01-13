package com.jinqiang.RecyclerViewRefresh.View;


import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinqiang.widgets.R;

import java.util.Date;

public class RefreshHeader extends LinearLayout {
    // 刷新状态,正常-准备刷新-刷新-完成
    public static final int STATE_NORMAL = 0;
    public static final int STATE_RELEASE_TO_REFRESH = 1;
    public static final int STATE_REFRESHING = 2;
    public static final int STATE_DONE = 3;

    private LinearLayout mContainer;
    private ImageView mArrowImageView;
    private SimpleView mProgressBar;
    private TextView mStatusTextView;
    private int mState = STATE_NORMAL; //记录的是上一次的状态.
    //箭头旋转动画
    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    //动画延时
    private static final int ROTATE_ANIM_DURATION = 180;
    //实际高度
    public int mMeasuredHeight;

    PullToRefreshIndicator mIndicator;

    public RefreshHeader(Context context) {
        super(context);
//        this(context,null);
        initView();
    }

    public RefreshHeader(Context context, @Nullable  AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        //初始化header布局
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_header_layout, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        setLayoutParams(lp);
        setPadding(0, 0, 0, 0);
        this.addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);

        mArrowImageView = (ImageView) findViewById(R.id.listview_header_arrow);
        mStatusTextView = (TextView) findViewById(R.id.refresh_status_textview);
        //初始化progressbar
        mProgressBar = (SimpleView) findViewById(R.id.listview_header_progressbar);
        mIndicator = new PullToRefreshIndicator(getContext());
        mIndicator.setIndicatorColor(0xffB5B5B5);
        mProgressBar.setView(mIndicator);

        //箭头翻转动画
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    public void setArrowImageView(int resid) {
        mArrowImageView.setImageResource(resid);
    }

    public int getState() {
        return mState;
    }

    /**
     * 滑动过程中改变当前头部的状态
     * @param state
     */
    public void changeState(int state){
        //状态和上一次相同，不用改变.
        if(state == mState)
            return ;
        if(state == STATE_NORMAL){
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }else if(state == STATE_REFRESHING){
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            mArrowImageView.clearAnimation();
            smoothScrollTo(mMeasuredHeight);
        }else if(state == STATE_RELEASE_TO_REFRESH){
            mProgressBar.setVisibility(View.INVISIBLE);
            mArrowImageView.setVisibility(View.VISIBLE);
        }else if(state == STATE_DONE){
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }


        switch(state){
            case STATE_NORMAL:
                if (mState == STATE_RELEASE_TO_REFRESH) {
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                mStatusTextView.setText(R.string.listview_header_hint_normal);
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_RELEASE_TO_REFRESH) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mStatusTextView.setText(R.string.listview_header_hint_release);
                }
                break;
            case     STATE_REFRESHING:
                mStatusTextView.setText(R.string.refreshing);
                break;
            case    STATE_DONE:
                mStatusTextView.setText(R.string.refresh_done);
                break;
            default:
                break;
        }
        mState = state; //记录本次状态
    }

    /**
     * 设置移动过程中的高度
     */
    public void setVisibleHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    /**
     * 获取当前刷新头部的可见高度
     * @return
     */
    public int getVisibleHeight(){
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        return lp.height;
    }

    /**
     * 头部滑动到指定位置的效果
     * @param destHeight getRawY()值，需要移动的高度
     */
    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    /**
     * 滑动过程中的状态变化
     * @param delta 相对滑动距离
     */
    public void onMove(float delta){
        if(getVisibleHeight() > 0 || delta > 0) {
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) {
                if (getVisibleHeight() > mMeasuredHeight) { //滑动高度大于固定高度，准备刷新
                    changeState(STATE_RELEASE_TO_REFRESH);
                }else {
                    changeState(STATE_NORMAL);
                }
            }
        }
    }

    /**
     * 刷新完成的状态改变
     */
    public void refreshComplete(){
        changeState(STATE_DONE);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                reset();
            }
        }, 200);
    }

    public void reset() {
        smoothScrollTo(0);
        //延时恢复初始状态
        new Handler().postDelayed(new Runnable() {
            public void run() {
                changeState(STATE_NORMAL);
            }
        }, 500);
    }

    /**
     * 判断当前是否是刷新态
     * @return
     */
    public boolean releaseType() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) // 刷新头不可见
            isOnRefresh = false;

        if(height > mMeasuredHeight &&  mState < STATE_REFRESHING){
            changeState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        // 可能在刷新过程中再次触发下滑刷新操作.
        if (mState == STATE_REFRESHING && height <=  mMeasuredHeight) {
            //return;
        }
        if (mState != STATE_REFRESHING) {
            smoothScrollTo(0);
        }
        //刷新时将刷新头的高度设置为初始高度.
        if (mState == STATE_REFRESHING) {
            int destHeight = mMeasuredHeight;
            smoothScrollTo(destHeight);
        }

        return isOnRefresh;
    }
}
