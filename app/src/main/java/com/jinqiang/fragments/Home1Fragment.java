package com.jinqiang.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.jinqiang.RecyclerViewRefresh.IRecyclerView;
import com.jinqiang.fragments.adapter.HomeAdapter;
import com.jinqiang.widgets.R;
import com.jinqiang.widgets.ReactActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Home1Fragment extends Fragment {
    @Bind(R.id.click)
    Button btn;
    @Bind(R.id.main_recyclerview)
    IRecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private HomeAdapter mAdapter;
    private ArrayList<String> mList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        /**  我觉得acitivity中有这个替换，fragment中也需要声明   **/
        toolbar.setTitle(getResources().getString(R.string.home));
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ReactActivity.class));
            }
        });
        /** init **/
        mList = new ArrayList<>();
        for(int i = 0; i < 15 ;i++){
            mList.add("item: " + i );
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        recyclerView.setLoadingListener(new IRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {

                        mList.clear();
                        for(int i = 0; i < 15 ;i++){
                            mList.add("" + i );
                        }
                        mAdapter.notifyDataSetChanged();
                        recyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {

            }
        });
        mAdapter = new HomeAdapter(mList);
        recyclerView.setAdapter(mAdapter);
    }
}
