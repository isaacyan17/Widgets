package com.jinqiang.MVPtest.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

/*

public class IndividualCenterActivity extends BaseActivity implements IndividualContract.View {

    private ImageButton mBackBtn;
    private RoundImageView mHeadView;
    private View parentView;
    private RelativeLayout mSexLayout, mSignLayout, mNickLayout,mHeadLayout;


    private TextView mTitleTV, mNickNameTV, mSexTV, mSignatureTV,mUserCodeTV,mLevelTV;

    private IndividualContract.Presenter mPresenter;

    DisplayImageOptions options = ImageLoaderOptions.initiateDisplayImageOptions();


    @Override
    protected void findView() {
        parentView = getLayoutInflater().inflate(R.layout.individual, null);
        setContentView(parentView);
        mNickNameTV = (TextView) findViewById(R.id.user_nickname);
        mSignatureTV = (TextView) findViewById(R.id.user_signature);
        mLevelTV = (TextView) findViewById(R.id.user_level);
        mUserCodeTV = (TextView) findViewById(R.id.user_vcode);
        mSexTV = (TextView) findViewById(R.id.user_sex);
        mTitleTV = (TextView) findViewById(R.id.title);
        mHeadLayout= (RelativeLayout) findViewById(R.id.my_head);
        mNickLayout = (RelativeLayout) findViewById(R.id.nick_layout);
        mHeadView = (RoundImageView) findViewById(R.id.user_head);
        mSexLayout = (RelativeLayout) findViewById(R.id.sex_layout);
        mSignLayout = (RelativeLayout) findViewById(R.id.signature_layout);
        mBackBtn = (ImageButton) findViewById(R.id.back_btn);
        mPresenter = new IndividualPresenter(this,this);
    }

    @Override
    protected void setDate() {
        String title = getIntent().getStringExtra("title");
        mTitleTV.setText(title);
        mPresenter.initData();
    }

    @Override
    protected void control() {
//        昵称修改
        mNickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.changeNikeName("修改昵称",mNickNameTV.getText().toString());
            }
        });

//        性别修改
        mSexLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.changeSex("性别",mSexTV.getText().toString());
            }
        });

//        个性签名修改
        mSignLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.changeSign("修改个性签名",mSignatureTV.getText().toString());
            }
        });
        //头像修改
        mHeadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.changeHead("头像","修改");

            }
        });
        mHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.changeHead("头像","修改");

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;

            switch (requestCode) {
                case 2:
                    mPresenter.setUserHead();
                    break;
                case 3:
                case 4:
                    mPresenter.updateResult(requestCode,data);
                    break;
                default:
                    break;
            }

    }



    @Override
    public void setNickName(String name) {
        mNickNameTV.setText(name);
    }

    @Override
    public void setUserSex(String sex) {
        mSexTV.setText(sex);
    }

    @Override
    public void setUserSign(String sign) {
        mSignatureTV.setText(sign);
    }

    @Override
    public void setUserCode(String code) {
        mUserCodeTV.setText(code);
    }

    @Override
    public void setUserHead(String headURL) {
        ImageLoader.getInstance().displayImage(headURL, mHeadView, options);
    }

    @Override
    public void showDialog() {
        showProgressDialog();
    }

    @Override
    public void hideDialog() {
        hideProgressDialog();
    }
}

*/