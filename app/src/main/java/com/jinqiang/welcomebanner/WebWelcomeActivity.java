package com.jinqiang.welcomebanner;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jinqiang.widgets.R;
import com.tencent.smtt.sdk.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebWelcomeActivity extends AppCompatActivity{
    @Bind(R.id.web_wel)
    WebView mWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_welcome);
        ButterKnife.bind(this);
        mWeb.loadUrl("file:///android_asset/web_page/page.html");
        mWeb.setVerticalScrollBarEnabled(false);
        mWeb.getSettings().setJavaScriptEnabled(true);
    }
}
