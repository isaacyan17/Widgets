package com.jinqiang.widgets;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    @Bind(R.id.main_fl)
    FrameLayout frameLayout;
    @Bind(R.id.bnb)
    BottomNavigationBar bar;
    FragmentManager manager;
    HomeFragment homeFragment;
    Home2Fragment home2Fragment;
    Home3Fragment home3Fragment;
    Home4Fragment home4Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        setDefaultFragment();
    }

    private void initView(){
        bar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bar
                .addItem(new BottomNavigationItem(R.mipmap.home,getResources().getString(R.string.home))/*.setActiveColor("#18ffff")*/)
                .addItem(new BottomNavigationItem(R.mipmap.search,getResources().getString(R.string.search))/*.setActiveColor("#ff9800")*/)
                .addItem(new BottomNavigationItem(R.mipmap.trade,getResources().getString(R.string.trade))/*.setActiveColor("#ffab91")*/)
                .addItem(new BottomNavigationItem(R.mipmap.mine,getResources().getString(R.string.mine))/*.setActiveColor("#ff4081")*/)
                .setIconAnimation(false)
                .setLabelTextSize(12)
                .initialise();
        bar.setTabSelectedListener(this);
    }

    private void setDefaultFragment(){
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.add(R.id.main_fl,homeFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position){
            case 0:
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                }else
                    transaction.replace(R.id.main_fl,homeFragment);
                break;
            case 1:
                if(home2Fragment == null){
                    home2Fragment = new Home2Fragment();
                }else
                    transaction.replace(R.id.main_fl,home2Fragment);
                break;
            case 2:
                if(home3Fragment == null){
                    home3Fragment = new Home3Fragment();
                }else
                    transaction.replace(R.id.main_fl,home3Fragment);
                break;
            case 3:
                if(home4Fragment == null){
                    home4Fragment = new Home4Fragment();
                }else
                    transaction.replace(R.id.main_fl,home4Fragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
