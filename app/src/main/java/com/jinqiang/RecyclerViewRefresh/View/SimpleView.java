package com.jinqiang.RecyclerViewRefresh.View;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class SimpleView extends ViewGroup {

    public SimpleView(Context context) {
        super(context);
    }

    public SimpleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 找到出最大的子view
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = this.getChildCount();
        int maxHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
            maxWidth = child.getMeasuredWidth();
            maxHeight = child.getMeasuredHeight();
        }
        setMeasuredDimension(maxWidth, maxHeight);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(0, 0, r - l, b - t);
            }
        }
    }

    public void setView(View view) {
        if (this.getChildCount() != 0) {
            this.removeViewAt(0);
        }
        this.addView(view, 0);
    }
}
