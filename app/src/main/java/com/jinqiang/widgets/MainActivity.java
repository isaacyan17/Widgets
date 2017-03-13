package com.jinqiang.widgets;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.jinqiang.fragments.Home2Fragment;
import com.jinqiang.fragments.Home3Fragment;
import com.jinqiang.fragments.Home4Fragment;
import com.jinqiang.fragments.Home1Fragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener,DefaultHardwareBackBtnHandler {
    @Bind(R.id.main_fl)
    FrameLayout frameLayout;
    @Bind(R.id.bnb)
    BottomNavigationBar bar;
    @Bind(R.id.main_toolbar)
    Toolbar toolbar;

    FragmentManager manager;
    private Home1Fragment home1Fragment;
    private Home2Fragment home2Fragment;
    private Home3Fragment home3Fragment;
    private Home4Fragment home4Fragment;
    private Fragment mCurrentFragment; //当前Fragment
    private String[] tags = new String[]{"homeFragment","home2Fragment","home3Fragment","home4Fragment"};

    //注册一下fragment的reactmanager;
    private ReactInstanceManager mReactInstanceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /** 首页setTitle 在setSupportActionBar之前调用，不然无效 **/
        toolbar.setTitle(getResources().getString(R.string.home));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//返回箭头

        /**
         * Get the reference to the ReactInstanceManager
         */
        mReactInstanceManager = ((ReactApplication)getApplication()).getReactNativeHost().getReactInstanceManager();
        initView();
        setDefaultFragment();
    }

    private void initView(){
        bar.setMode(BottomNavigationBar.MODE_FIXED);
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
        home1Fragment = new Home1Fragment();
        transaction.add(R.id.main_fl, home1Fragment);
        transaction.commit();
        mCurrentFragment = home1Fragment;
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position){
            case 0:
                if(home1Fragment == null){
                    home1Fragment = new Home1Fragment();
                }
                //替换replace方法为hide或show,防止反复实例化Fragment。
                if(!home1Fragment.isAdded()){
                    transaction.hide(mCurrentFragment).add(R.id.main_fl, home1Fragment,tags[position]);
                }else {
                    transaction.hide(mCurrentFragment).show(home1Fragment);
//                    transaction.replace(R.id.main_fl, homeFragment);
                }
                mCurrentFragment = home1Fragment;
                toolbar.setTitle(getResources().getString(R.string.home));
                break;
            case 1:
                if(home2Fragment == null){
                    home2Fragment = new Home2Fragment();
                }
//                    transaction.replace(R.id.main_fl,home2Fragment);
                if(!home2Fragment.isAdded()){
                    transaction.hide(mCurrentFragment).add(R.id.main_fl,home2Fragment,tags[position]);
                }else {
                    transaction.hide(mCurrentFragment).show(home2Fragment);
                }
                mCurrentFragment = home2Fragment;
                toolbar.setTitle(getResources().getString(R.string.search));
                break;
            case 2:
                if(home3Fragment == null){
                    home3Fragment = new Home3Fragment();
                }
//                    transaction.replace(R.id.main_fl,home3Fragment);

                if(!home3Fragment.isAdded()){
                    transaction.hide(mCurrentFragment).add(R.id.main_fl,home3Fragment,tags[position]);
                }else {
                    transaction.hide(mCurrentFragment).show(home3Fragment);
                }
                mCurrentFragment = home3Fragment;
                toolbar.setTitle(getResources().getString(R.string.trade));
                break;
            case 3:
                if(home4Fragment == null){
                    home4Fragment = new Home4Fragment();
                }
//                    transaction.replace(R.id.main_fl,home4Fragment);
                if(!home4Fragment.isAdded()){
                    transaction.hide(mCurrentFragment).add(R.id.main_fl,home4Fragment,tags[position]);
                }else {
                    transaction.hide(mCurrentFragment).show(home4Fragment);
                }
                mCurrentFragment = home4Fragment;
                toolbar.setTitle(getResources().getString(R.string.mine));
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
     * @param saveInstanceBundle
     */
    private void checkState(Bundle saveInstanceBundle){
        if(saveInstanceBundle == null){
            setDefaultFragment();
        }else{
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
}
