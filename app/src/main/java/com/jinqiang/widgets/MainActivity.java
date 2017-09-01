package com.jinqiang.widgets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.jinqiang.Utils.GlideRoundTransform;
import com.jinqiang.config.Config;
import com.jinqiang.fragments.Home2Fragment;
import com.jinqiang.fragments.Home3Fragment;
import com.jinqiang.fragments.Home4Fragment;
import com.jinqiang.fragments.Home1Fragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, DefaultHardwareBackBtnHandler {
    @Bind(R.id.main_fl)
    FrameLayout frameLayout;
    @Bind(R.id.bnb)
    BottomNavigationBar bar;
    //    @Bind(R.id.main_toolbar)
//    Toolbar toolbar;
    @Bind(R.id.main_toolBar)
    Toolbar toolbar;
    @Bind(R.id.main_nv_menu)
    NavigationView navigationView;
    @Bind(R.id.main_drawerLayout)
    DrawerLayout drawerLayout;
    //    @Bind(R.id.head_img)
    ImageView mHeadImg;


    private FragmentManager manager;
    private Home1Fragment home1Fragment;
    private Home2Fragment home2Fragment;
    private Home3Fragment home3Fragment;
    private Home4Fragment home4Fragment;
    private Fragment mCurrentFragment; //当前Fragment
    private String[] tags = new String[]{"homeFragment", "home2Fragment", "home3Fragment", "home4Fragment"};
    private Context mContext;

    //注册一下fragment的reactmanager;
    private ReactInstanceManager mReactInstanceManager;
    //search图标的显隐控制
    private MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);
        /** 首页setTitle 在setSupportActionBar之前调用，不然无效 **/
//        toolbar.setTitle(getResources().getString(R.string.home));
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//返回箭头

        /**
         * Get the reference to the ReactInstanceManager
         */
        mReactInstanceManager = ((ReactApplication) getApplication()).getReactNativeHost().getReactInstanceManager();
        initView();
        setDefaultFragment();
        /** 初始化navigationView **/
        initNavigationView();
    }

    private void initNavigationView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        startActivity(new Intent(mContext, ReactActivity.class));
                        break;
                }
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
        View headerView = navigationView.getHeaderView(0);
        if (headerView != null) {
            mHeadImg = (ImageView) headerView.findViewById(R.id.head_img);
            Glide.with(this).load(Config.HEAD_FONT_URL).asBitmap().centerCrop().transform(new GlideRoundTransform(this, 100)).into(mHeadImg);
        }
    }


    private void initView() {
        bar.setMode(BottomNavigationBar.MODE_FIXED);
        bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bar
                .addItem(new BottomNavigationItem(R.mipmap.home, getResources().getString(R.string.home))/*.setActiveColor("#18ffff")*/)
                .addItem(new BottomNavigationItem(R.mipmap.search, getResources().getString(R.string.search))/*.setActiveColor("#ff9800")*/)
                .addItem(new BottomNavigationItem(R.mipmap.trade, getResources().getString(R.string.trade))/*.setActiveColor("#ffab91")*/)
                .addItem(new BottomNavigationItem(R.mipmap.mine, getResources().getString(R.string.mine))/*.setActiveColor("#ff4081")*/)
                .setIconAnimation(false)
                .setLabelTextSize(12)
                .initialise();
        bar.setTabSelectedListener(this);
    }

    private void setDefaultFragment() {
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        home1Fragment = new Home1Fragment();
        transaction.add(R.id.main_fl, home1Fragment);
        transaction.commit();
        mCurrentFragment = home1Fragment;
        toolbar.setTitle(getResources().getString(R.string.home));
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        searchMenuItem.setVisible(false);
        switch (position) {
            case 0:
                if (home1Fragment == null) {
                    home1Fragment = new Home1Fragment();
                }
                //替换replace方法为hide或show,防止反复实例化Fragment。
                if (!home1Fragment.isAdded()) {
                    transaction.hide(mCurrentFragment).add(R.id.main_fl, home1Fragment, tags[position]);
                } else {
                    transaction.hide(mCurrentFragment).show(home1Fragment);
//                    transaction.replace(R.id.main_fl, homeFragment);
                }
                mCurrentFragment = home1Fragment;
                toolbar.setTitle(getResources().getString(R.string.home));
                toolbar.setVisibility(View.VISIBLE);

                break;
            case 1:
                if (home2Fragment == null) {
                    home2Fragment = new Home2Fragment();
                }
//                    transaction.replace(R.id.main_fl,home2Fragment);
                if (!home2Fragment.isAdded()) {
                    transaction.hide(mCurrentFragment).add(R.id.main_fl, home2Fragment, tags[position]);
                } else {
                    transaction.hide(mCurrentFragment).show(home2Fragment);
                }
                mCurrentFragment = home2Fragment;
                toolbar.setTitle(getResources().getString(R.string.search));
                toolbar.setVisibility(View.VISIBLE);
                searchMenuItem.setVisible(true);

                break;
            case 2:
                if (home3Fragment == null) {
                    home3Fragment = new Home3Fragment();
                }
//                    transaction.replace(R.id.main_fl,home3Fragment);

                if (!home3Fragment.isAdded()) {
                    transaction.hide(mCurrentFragment).add(R.id.main_fl, home3Fragment, tags[position]);
                } else {
                    transaction.hide(mCurrentFragment).show(home3Fragment);
                }
                mCurrentFragment = home3Fragment;
                toolbar.setTitle(getResources().getString(R.string.trade));
                toolbar.setVisibility(View.VISIBLE);

                break;
            case 3:
                toolbar.setVisibility(View.GONE);
                if (home4Fragment == null) {
                    home4Fragment = new Home4Fragment();
                }
//                    transaction.replace(R.id.main_fl,home4Fragment);
                if (!home4Fragment.isAdded()) {
                    transaction.hide(mCurrentFragment).add(R.id.main_fl, home4Fragment, tags[position]);
                } else {
                    transaction.hide(mCurrentFragment).show(home4Fragment);
                }
                mCurrentFragment = home4Fragment;
//                toolbar.setTitle(getResources().getString(R.string.mine));
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        checkState(outState);
    }

    /**
     * 低内存非正常销毁时，拿到临时数据，并通过tag拿到各fragment,防止重叠。
     *
     * @param saveInstanceBundle
     */
    private void checkState(Bundle saveInstanceBundle) {
        if (saveInstanceBundle == null) {
            setDefaultFragment();
        } else {
            Home1Fragment fragment = (Home1Fragment) getSupportFragmentManager().findFragmentByTag(tags[0]);
            Home2Fragment fragment2 = (Home2Fragment) getSupportFragmentManager().findFragmentByTag(tags[1]);
            Home3Fragment fragment3 = (Home3Fragment) getSupportFragmentManager().findFragmentByTag(tags[2]);
            Home4Fragment fragment4 = (Home4Fragment) getSupportFragmentManager().findFragmentByTag(tags[3]);
            getSupportFragmentManager().beginTransaction().show(fragment).hide(fragment2).hide(fragment3).hide(fragment4).commit();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toobar_menu, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        if (item.getItemId() == R.id.action_search) {
            if(home2Fragment != null){
                home2Fragment.handleSearchToggle();
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
