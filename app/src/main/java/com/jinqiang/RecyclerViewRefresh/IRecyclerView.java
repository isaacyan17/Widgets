package com.jinqiang.RecyclerViewRefresh;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.jinqiang.RecyclerViewRefresh.View.LoadMoreFooter;
import com.jinqiang.RecyclerViewRefresh.View.RefreshHeader;

import java.util.ArrayList;
import java.util.List;

public class IRecyclerView extends RecyclerView{
    private boolean PullToRefreshEnabled = true; //下拉刷新控制
    private boolean LoadMoreEnabled = true;  // 加载更多控制
    private boolean isLoadingData = false;  // 是否正在加载数据
    private boolean isNoMore = false;  //没有更多数据
    private RefreshHeader mRefreshHeader;  //下拉控件
    private LoadMoreFooter mFooter;  // 加载更多控件
    private float mLastY = -1;  //记录上一次移动的Y轴位置
    private static final float DRAG_RATE = 3;
    private LoadingListener mLoadingListener; // 回调刷新监听
    private ArrayList<View> mHeaderViews = new ArrayList<>(); // 所有的头布局
    /** 头尾布局的保留字 **/
    private static final int TYPE_REFRESH_HEADER = 10000;//设置一个很大的数字,尽可能避免和用户的adapter冲突
    private static final int TYPE_FOOTER = 10001;
    private static final int HEADER_INIT_INDEX = 10002; //一般的header索引起始目录
    private static List<Integer> sHeaderTypes = new ArrayList<>();//每个header必须有不同的type,不然滚动的时候顺序会变化
    private final RecyclerView.AdapterDataObserver mDataObserver = new DataObserver();
    private AppBarStateChangeListener.State appbarState = AppBarStateChangeListener.State.EXPANDED;
    private MRecyclerAdapter mAdapter;
    private View mEmptyView; // TODO 空界面


    public IRecyclerView(Context context) {
        this(context,null);
    }

    public IRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化刷新布局
     */
    public void init(){
        if(PullToRefreshEnabled){
            mRefreshHeader = new RefreshHeader(getContext());
        }
        mFooter = new LoadMoreFooter(getContext());
        mFooter.setVisibility(GONE);
    }

    public void setArrowImageView(int resid){
        if (mRefreshHeader != null) {
            mRefreshHeader.setArrowImageView(resid);
        }
    }

    public void refreshComplete() {
        mRefreshHeader.refreshComplete();
        setNoMore(false);
    }

    public void loadMoreComplete() {
        isLoadingData = false;
        if (mFooter instanceof LoadMoreFooter) {
            mFooter.changeState(LoadMoreFooter.STATE_COMPLETE);
        } else {
            mFooter.setVisibility(View.GONE);
        }
    }

    public void setNoMore(boolean noMore){
        isLoadingData = false;
        isNoMore = noMore;
        if (mFooter instanceof LoadMoreFooter) {
            mFooter.changeState(isNoMore ? LoadMoreFooter.STATE_NOMORE:LoadMoreFooter.STATE_COMPLETE);
        } else {
            mFooter.setVisibility(View.GONE);
        }
    }

    public void setLoadingMoreEnabled(boolean enabled) {
        LoadMoreEnabled = enabled;
        if (!enabled) {
            if (mFooter instanceof LoadMoreFooter) {
                mFooter.changeState(LoadMoreFooter.STATE_COMPLETE);
            }
        }
    }

    /**
     * 上拉动作触发加载布局
     * @param state
     */
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && !isLoadingData && LoadMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) { //网格
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) { // 瀑布流
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1 &&
                    layoutManager.getItemCount() > layoutManager.getChildCount() &&
                    !isNoMore && mRefreshHeader.getState() < RefreshHeader.STATE_REFRESHING) {//判断是否大于等于最后一个item，并且不处于下拉刷新的状态中
                isLoadingData = true;
                if (mFooter instanceof LoadMoreFooter) {
                    mFooter.changeState(LoadMoreFooter.STATE_LOADING);
                } else {
                    mFooter.setVisibility(View.VISIBLE);
                }
                mLoadingListener.onLoadMore();
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = new MRecyclerAdapter(adapter);
        super.setAdapter(mAdapter);
        adapter.registerAdapterDataObserver(mDataObserver); // 注册数据变化的observer.(即notifyDataSetChanged)
        mDataObserver.onChanged();
    }

    //避免用户自己调用getAdapter() 引起的ClassCastException
    @Override
    public Adapter getAdapter() {
        if(mAdapter != null)
            return mAdapter.getOriginalAdapter();
        else
            return null;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if(mAdapter != null){
            if (layout instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) layout);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (mAdapter.isHeader(position) || mAdapter.isFooter(position) || mAdapter.isRefreshHeader(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(mLastY == -1)
            mLastY = e.getRawY();
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = e.getRawY() - mLastY;
                mLastY = e.getRawY();
                if(PullToRefreshEnabled && appbarState == AppBarStateChangeListener.State.EXPANDED && isOnTop()){
                    mRefreshHeader.onMove(deltaY / DRAG_RATE); //这里 除DRAG_RATE 是为了增大拉出刷新头需要走的Y值，以保证不至于拉出过多空白.
                    if (mRefreshHeader.getVisibleHeight() > 0 && mRefreshHeader.getState() < RefreshHeader.STATE_REFRESHING) {
                        return false;
                    }
                }
                break;
            default:
                mLastY = -1; // reset,无论是否是刷新态，都做回弹效果.
                if (isOnTop() && PullToRefreshEnabled && appbarState == AppBarStateChangeListener.State.EXPANDED) {
                    if (mRefreshHeader.releaseType()) {
                        if (mLoadingListener != null) {
                            mLoadingListener.onRefresh();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    private boolean isOnTop() {
        if (mRefreshHeader.getParent() != null) {
            return true;
        } else {
            return false;
        }
    }

    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }

    public interface LoadingListener {

        void onRefresh();

        void onLoadMore();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //解决和CollapsingToolbarLayout冲突的问题
        AppBarLayout appBarLayout = null;
        ViewParent p = getParent();
        while (p != null) {
            if (p instanceof CoordinatorLayout) {
                break;
            }
            p = p.getParent();
        }
        if(p instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout)p;
            final int childCount = coordinatorLayout.getChildCount();
            for (int i = childCount - 1; i >= 0; i--) {
                final View child = coordinatorLayout.getChildAt(i);
                if(child instanceof AppBarLayout) {
                    appBarLayout = (AppBarLayout)child;
                    break;
                }
            }
            if(appBarLayout != null) {
                appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
                    @Override
                    public void onStateChanged(AppBarLayout appBarLayout, State state) {
                        appbarState = state;
                    }
                });
            }
        }
    }

    private boolean isHeaderType(int type){
        return mHeaderViews.size() > 0 && sHeaderTypes.contains(type);
    }

    private View getHeaderViewByType(int itemType){
        if(!isHeaderType(itemType))
            return null;
        return mHeaderViews.get(itemType - HEADER_INIT_INDEX);
    }

    /**
     * 判断是否是保留字段 10000，10001，10002
     * @param itemType
     * @return
     */
    private boolean isReservedItemViewType(int itemType){
        if(itemType == TYPE_REFRESH_HEADER || itemType == TYPE_FOOTER || sHeaderTypes.contains(itemType))
            return true;
        return false;
    }

    /** adapter  */
    class MRecyclerAdapter extends  RecyclerView.Adapter<ViewHolder>{
        RecyclerView.Adapter mAdapter;

        public MRecyclerAdapter(RecyclerView.Adapter adapter){
            this.mAdapter = adapter;
        }
        public RecyclerView.Adapter getOriginalAdapter(){
            return this.mAdapter;
        }

        /**
         * 判断当前item是否是header
         * @param position
         * @return
         */
        public boolean isHeader(int position) {
            return position >= 1 && position < mHeaderViews.size() + 1;
        }

        public boolean isFooter(int position) {
            if(LoadMoreEnabled) {
                return position == getItemCount() - 1;
            }else {
                return false;
            }
        }

        public boolean isRefreshHeader(int position) {
            return position == 0;
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_REFRESH_HEADER) {
                return new SimpleViewHolder(mRefreshHeader);
            } else if (isHeaderType(viewType)) {
                return new SimpleViewHolder(getHeaderViewByType(viewType));
            } else if (viewType == TYPE_FOOTER) {
                return new SimpleViewHolder(mFooter);
            }
            // 一般的holder
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isHeader(position) || isRefreshHeader(position)) {
                return;
            }
            int adjPosition = position - (getHeadersCount() + 1); //即普通view
            int adapterCount;
            if (mAdapter != null) {
                adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    mAdapter.onBindViewHolder(holder, adjPosition);
                }
            }
        }

        // some times we need to override this
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position,List<Object> payloads) {
            if (isHeader(position) || isRefreshHeader(position)) {
                return;
            }
            int adjPosition = position - (getHeadersCount() + 1);
            int adapterCount;
            if (mAdapter != null) {
                adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    if(payloads.isEmpty()){
                        mAdapter.onBindViewHolder(holder, adjPosition);
                    }
                    else{
                        mAdapter.onBindViewHolder(holder, adjPosition,payloads);
                    }
                }
            }
        }


        @Override
        public int getItemCount() {
            if(LoadMoreEnabled) {
                if (mAdapter != null) {
                    return getHeadersCount() + mAdapter.getItemCount() + 2;
                } else {
                    return getHeadersCount() + 2;
                }
            }else {
                if (mAdapter != null) {
                    return getHeadersCount() + mAdapter.getItemCount() + 1;
                } else {
                    return getHeadersCount() + 1;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            int adjPosition = position - (getHeadersCount() + 1);
            if (isRefreshHeader(position)) {
                return TYPE_REFRESH_HEADER;
            }
            if (isHeader(position)) {
                position = position - 1;
                return sHeaderTypes.get(position);
            }
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }
            int adapterCount;
            if (mAdapter != null) {
                adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    int type =  mAdapter.getItemViewType(adjPosition);
                    if(isReservedItemViewType(type)) {
                        throw new IllegalStateException("IRecyclerView require itemViewType in adapter should be less than 10000 " );
                    }
                    return type;
                }
            }
            return 0;
        }

        @Override
        public long getItemId(int position) {
            if (mAdapter != null && position >= getHeadersCount() + 1) {
                int adjPosition = position - (getHeadersCount() + 1);
                if (adjPosition < mAdapter.getItemCount()) {
                    return mAdapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeader(position) || isFooter(position) || isRefreshHeader(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
            mAdapter.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            mAdapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && (isHeader(holder.getLayoutPosition()) ||isRefreshHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
            mAdapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
            mAdapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            mAdapter.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
            return mAdapter.onFailedToRecycleView(holder);
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            mAdapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            mAdapter.registerAdapterDataObserver(observer);
        }

        /**
         *  viewholder
         */
        private class SimpleViewHolder extends RecyclerView.ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    private class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
            if (mAdapter != null && mEmptyView != null) {
                int emptyCount = 1 + mAdapter.getHeadersCount();
                if (LoadMoreEnabled) {
                    emptyCount++;
                }
                if (mAdapter.getItemCount() == emptyCount) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    IRecyclerView.this.setVisibility(View.GONE);
                } else {

                    mEmptyView.setVisibility(View.GONE);
                    IRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    }

}
