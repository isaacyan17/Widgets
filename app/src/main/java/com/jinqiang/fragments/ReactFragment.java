package com.jinqiang.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.jinqiang.widgets.ReactApplication;

/**
 * Created by jingqiang on 2017/3/9.
 */

public abstract class ReactFragment extends Fragment {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    /**
     * 返回appregistry注册的名字.
     * @return
     */
    public abstract  String getMainComponentName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mReactRootView = new ReactRootView(context);
        mReactInstanceManager =
                ((ReactApplication) getActivity().getApplication())
                        .getReactNativeHost()
                        .getReactInstanceManager();
    }

    /**
     * 这里改写根View为ReactRootView,因此继承此类的fragment实现`getMainComponentName`即可.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public ReactRootView onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mReactRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mReactRootView.startReactApplication(
                mReactInstanceManager,
                getMainComponentName(),
                null
        );
    }


}
