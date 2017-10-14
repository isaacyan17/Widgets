package com.jinqiang.welcomebanner;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;

import com.jinqiang.Utils.SharedpreferencesUtils;
import com.jinqiang.materialLogin.LoginActivity;
import com.jinqiang.widgets.MainActivity;
import com.jinqiang.widgets.R;
import com.tencent.smtt.sdk.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebWelcomeActivity extends AppCompatActivity {
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
        WebWelcomeActivity.JSKit jsKit = new WebWelcomeActivity.JSKit(this);
        mWeb.addJavascriptInterface(jsKit, "Widgets");
    }

    class JSKit {
        private WebWelcomeActivity context;

        public JSKit(WebWelcomeActivity context) {
            this.context = context;
        }

        @JavascriptInterface
        public void jumpMainActivity() {
            //名字或许能再霸气一点
            //这里判断用户登录是否失效
            boolean loginState = SharedpreferencesUtils.getLoginState(context);
            if(!loginState) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                //TODO transition
                finish();
            }else{
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
