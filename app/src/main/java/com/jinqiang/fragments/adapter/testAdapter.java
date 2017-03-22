package com.jinqiang.fragments.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinqiang.RecyclerViewRefresh.IRecyclerView;
import com.jinqiang.widgets.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingqiang on 2017/1/10.
 */

public class testAdapter extends IRecyclerView.Adapter<testAdapter.ViewHolder> {
    public ArrayList<String> datas = null;
    public testAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }
//    public static final int TYPE_HEADER = 0;
    public static final int HEADER_ONE = 1;
    public static final int HEADER_TWO = 2;

    public static final int TYPE_NORMAL = 0;

    private ArrayList<View> mHeaderViews = new ArrayList<>(); // 所有的头布局
    private List<Integer> sHeaderTypes = new ArrayList<>();//每个header必须有不同的type,不然滚动的时候顺序会变化


    public boolean isHeader(int position){
        if(position<mHeaderViews.size())
            return true;
        return false;
    }

    public void setmHeaderViews(ArrayList<View> mHeaderViews) {
        this.mHeaderViews = mHeaderViews;
    }

    public  void setsHeaderTypes(List<Integer> sHeaderTypes) {
        this.sHeaderTypes = sHeaderTypes;
    }


    @Override
    public int getItemViewType(int position) {
        if(mHeaderViews==null || mHeaderViews.size() <= 0)
            return TYPE_NORMAL;
        if(isHeader(position))
            return sHeaderTypes.get(position);
        return TYPE_NORMAL;
    }

    public int getHeaderCount(){
        return mHeaderViews.size();
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(mHeaderViews!=null && sHeaderTypes.contains(viewType))
            return new ViewHolder(getHeaderViewByType(viewType));
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if(isHeader(position)){
            // TODO
        }else {
            //position要重新校验
            int adjPosition = position - getHeaderCount();
            viewHolder.mTextView.setText(datas.get(adjPosition));
        }
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mHeaderViews == null ? datas.size():datas.size()+getHeaderCount();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.text);
        }
    }

    private boolean isHeaderType(int type){
        return mHeaderViews.size() > 0 && sHeaderTypes.contains(type);
    }

    private View getHeaderViewByType(int itemType){
        if(!isHeaderType(itemType))
            return null;
        return mHeaderViews.get(itemType - TYPE_NORMAL - 1);
    }
}