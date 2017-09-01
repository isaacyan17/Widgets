package com.jinqiang.fragments;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

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
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        /**  我觉得acitivity中有这个替换，fragment中也需要声明   **/
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        toolbar.setTitle(getResources().getString(R.string.search));

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
//        if (hidden)
//            searchBar.setVisibility(View.GONE);
//        else
//            searchBar.setVisibility(View.VISIBLE);
    }

    /**
     * change toolbar search icon action when touched
     */
    public void handleSearchToggle() {
        if (searchBar.getVisibility() == View.VISIBLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(searchBar,
                        searchBar.getWidth() - dp2px(50),
                        dp2px(23),
                        (float) Math.hypot(searchBar.getWidth(), searchBar.getHeight()),
                        0);
                circularReveal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(searchBar.getWindowToken(), 0);
                        searchBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                circularReveal.setDuration(300);
                circularReveal.start();

            } else {
                ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(searchBar.getWindowToken(), 0);
                searchBar.setVisibility(View.GONE);
            }
            searchBar.setText("");
        } else if (searchBar.getVisibility() == View.GONE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Animator circularReveal = ViewAnimationUtils.createCircularReveal(searchBar,
                        searchBar.getWidth() - dp2px(50),
                        dp2px(23),
                        0,
                        (float) Math.hypot(searchBar.getWidth(), searchBar.getHeight()));
                circularReveal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        searchBar.setText("");
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                searchBar.setVisibility(View.VISIBLE);
                circularReveal.setDuration(300);
                circularReveal.start();

            } else {
                searchBar.setVisibility(View.VISIBLE);
                ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }
    }

    public int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
