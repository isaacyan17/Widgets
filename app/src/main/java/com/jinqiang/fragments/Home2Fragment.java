package com.jinqiang.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinqiang.RecyclerViewRefresh.IRecyclerView;
import com.jinqiang.widgets.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 搜索界面
 * TODO  主界面搜索栏没有提示文字，感觉又要下源码修改 ( - -! ): https://github.com/mancj/MaterialSearchBar
 * TODO  点击search box的时候的transition过渡，热门搜索。
 * TODO  ps: 这哥们写了个不错的slideUp控件。
 */
public class Home2Fragment extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    @Bind(R.id.search_recyc)
    IRecyclerView recyclerView;
    @Bind(R.id.search_bar)
    MaterialSearchBar searchBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchBar.setCardViewElevation(10);
        searchBar.setOnSearchActionListener(this);
//        searchBar.setText("搜索");
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden)
            searchBar.setVisibility(View.GONE);
        else
            searchBar.setVisibility(View.VISIBLE);
    }
}
