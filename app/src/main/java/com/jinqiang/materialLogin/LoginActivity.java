package com.jinqiang.materialLogin;


import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jinqiang.Utils.LogUtils;
import com.jinqiang.Utils.StringUtils;
import com.jinqiang.Utils.progressDialog.ProgressBarUtils;
import com.jinqiang.config.Config;
import com.jinqiang.materialLogin.net.LoginNet;
import com.jinqiang.widgets.MainActivity;
import com.jinqiang.widgets.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.login_edname)
    EditText account;
    @Bind(R.id.login_edpwd)
    EditText password;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.login_btn)
    Button comfirm;

    private LoginNet mNetModel;
    private ProgressBarUtils mProgress;
    private Context mContext;
    private String preAcount = "123";
    private String prePassword = "123";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_login_main);
        ButterKnife.bind(this);
        fab.setOnClickListener(this);
        comfirm.setOnClickListener(this);
        initNetModel();
        mProgress = new ProgressBarUtils(this);
        mContext = this;
    }

    private void initNetModel() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Config.SERVER_URL)
                .build();
        mNetModel = retrofit.create(LoginNet.class);
    }

    @TargetApi(21)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                getWindow().setEnterTransition(null);
                getWindow().setExitTransition(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
                break;
            case R.id.login_btn:
                /** update at 2017-07-06 */
                String name = account.getEditableText().toString();
                String pwd = password.getEditableText().toString();
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(pwd)) {
                    //账户或密码为空
                    Toast.makeText(this, "账户或密码为空", Toast.LENGTH_SHORT).show();
                } else {
                    mProgress.show();
                    if (name.equals(preAcount) && pwd.equals(prePassword)) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else{
                        checkLogin(name, pwd);
                    }
                }
                break;
        }
    }

    /**
     * 登录
     */
    private void checkLogin(String name, String password) {
        //TODO progressDialog
        mNetModel.login(name, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgress.hide();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        mProgress.hide();
                        LogUtils.getLogger().v(s);

                        //TODO
                    }
                });
    }
}
