package com.jinqiang.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jinqiang.RecyclerViewRefresh.IRecyclerView;
import com.jinqiang.fragments.adapter.testAdapter;
import com.jinqiang.widgets.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Home4Fragment extends Fragment {
    @Bind(R.id.recyclerview)
    IRecyclerView iRecyclerView;
    private ArrayList<String> listData;
    private testAdapter mAdapter;
    private int refreshTime = 0;
    private int times = 0;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tab)
    TabLayout tabLayout;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;

    @Bind(R.id.toolbar1)
    LinearLayout view1;
    @Bind(R.id.toolbar2)
    LinearLayout view2;
    @Bind(R.id.layout1_tv)
    TextView layout1_tv;
    @Bind(R.id.layout2_tv1)
    TextView layout2_tv1;
    @Bind(R.id.layout2_tv2)
    TextView layout2_tv2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        /**布局切换抖动是  android:fitsSystemWindows="true" 的属性问题  **/
//        iRecyclerView = (IRecyclerView) this.findViewById(R.id.recyclerview);
        /**  我觉得acitivity中有这个替换，fragment中也需要声明   **/
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.addTab(tabLayout.newTab().setText("布局三"));
        tabLayout.addTab(tabLayout.newTab().setText("布局三"));
        tabLayout.addTab(tabLayout.newTab().setText("布局三"));
        tabLayout.addTab(tabLayout.newTab().setText("布局三"));


        iRecyclerView.setLayoutManager(layoutManager);
        iRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        iRecyclerView.setLoadingListener(new IRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime ++;
                times = 0;
                new Handler().postDelayed(new Runnable(){
                    public void run() {

                        listData.clear();
                        for(int i = 0; i < 15 ;i++){
                            listData.add("" + i );
                        }
                        mAdapter.notifyDataSetChanged();
                        iRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
                times ++ ;
            }


            @Override
            public void onLoadMore() {
                if(times < 2){
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            iRecyclerView.loadMoreComplete();
                            for(int i = 0; i < 15 ;i++){
                                listData.add("" + (i + listData.size()) );
                            }
                            iRecyclerView.loadMoreComplete();
                            mAdapter.notifyDataSetChanged();
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(),"已经没有更多数据了",Toast.LENGTH_SHORT).show();
                            iRecyclerView.setNoMore(true);
                            mAdapter.notifyDataSetChanged();
                        }
                    }, 1000);
                }
                times ++;
            }
        });

        listData = new ArrayList<String>();
        for(int i = 0; i < 15 ;i++){
            listData.add("" + i);
        }
        mAdapter = new testAdapter(listData);
        setHeader(iRecyclerView);
        iRecyclerView.setAdapter(mAdapter);

        /** appbarLayout **/
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0){
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.GONE);
                    //alpha 变化
                    setToolbar1Alpha(255);

                }else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.VISIBLE);
                    //alpha 变化
                    setToolbar2Alpha(255);
                }else{
                    int alpha=255-Math.abs(verticalOffset);
                    if(alpha<0){
                        //收缩toolbar
                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.VISIBLE);
                        setToolbar2Alpha(Math.abs(verticalOffset));
                    }else{
                        //张开toolbar
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.GONE);
                        setToolbar1Alpha(alpha);
                    }
                }
            }
        });
    }

    public void setHeader(IRecyclerView view){

        View header1 = LayoutInflater.from(getContext()).inflate(R.layout.head1_layout, view, false);
        View header2 = LayoutInflater.from(getContext()).inflate(R.layout.head2_layout, view, false);
        ArrayList<View> list = new ArrayList<>();
        ArrayList<Integer> type = new ArrayList<>();
        type.add(testAdapter.HEADER_ONE);
        type.add(testAdapter.HEADER_TWO);
        list.add(header1);
        list.add(header2);
        mAdapter.setmHeaderViews(list);
        mAdapter.setsHeaderTypes(type);
    }
    public void setToolbar1Alpha(int Alpha){
        layout1_tv.setAlpha(Alpha);
    }
    public void setToolbar2Alpha(int Alpha){
        layout2_tv1.setAlpha(Alpha);
        layout2_tv2.setAlpha(Alpha);
    }
}
