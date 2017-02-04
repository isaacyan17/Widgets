package com.jinqiang.materialLogin;


import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jinqiang.widgets.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.login_edname)
    EditText account;
    @Bind(R.id.login_edpwd)
    EditText password;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.login_btn)
    Button comfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_login_main);
        ButterKnife.bind(this);
        fab.setOnClickListener(this);
    }

    @TargetApi(21)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                getWindow().setEnterTransition(null);
                getWindow().setExitTransition(null);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,fab,fab.getTransitionName());
                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class),options.toBundle());
                }else{
                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                }
                break;
            case R.id.login_btn:
                account.getEditableText().toString();
                break;
        }
    }
}
