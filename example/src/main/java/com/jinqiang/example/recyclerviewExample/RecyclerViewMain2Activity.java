package com.jinqiang.example.recyclerviewExample;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


import com.alibaba.android.arouter.facade.annotation.Route;


import com.jinqiang.baselibrary.irecyclerView.IRecyclerView;
import com.jinqiang.example.R;
import com.jinqiang.example.recyclerviewExample.adapter.MyAdapter;

import java.util.ArrayList;

@Route(path="/example/recyclerView")
public class RecyclerViewMain2Activity extends AppCompatActivity{

    private ArrayList<String> listData;
    private MyAdapter mAdapter;
    private int refreshTime = 0;
    private int times = 0;
    private IRecyclerView iRecyclerView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_main2);
        iRecyclerView = (IRecyclerView)findViewById(R.id.recyclerview) ;
        toolbar = (Toolbar)findViewById(R.id.toolbar) ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

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
                            listData.add("ex " + i );
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
                            Toast.makeText(RecyclerViewMain2Activity.this,"已经没有更多数据了",Toast.LENGTH_SHORT).show();
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
        mAdapter = new MyAdapter(listData);
        iRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
