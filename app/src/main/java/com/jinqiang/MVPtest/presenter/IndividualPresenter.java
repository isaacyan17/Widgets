package com.jinqiang.MVPtest.presenter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


/*
public class IndividualPresenter implements IndividualContract.Presenter {
    private IndividualContract.View mView;
    private Activity mActivity;
    ChangeUserInfoBean mChangeUserInfoBean;
    public Context getContext(){
        return  mActivity;
    }

    public IndividualPresenter(IndividualContract.View view, Activity activity) {
        this.mView = view;
        this.mActivity = activity;
        mChangeUserInfoBean = new ChangeUserInfoBean(this);
    }

    @Override
    public void initData() {

        User mUser = mChangeUserInfoBean.getmUser();
        if (mUser.getUserSex().equals("0")) {
            mView.setUserSex("男");
        } else {
            mView.setUserSex("女");
        }
        mView.setNickName(mUser.getNickName());
        mView.setUserSign(mUser.getUserSign());
        mView.setUserCode(mUser.getUserAccount());
        mView.setUserHead(mUser.getHeadImg());
    }

    @Override
    public void changeNikeName(String title, String sign) {
        Intent intent = new Intent(mActivity, xxx.class);
        intent.putExtra("title", title);
        intent.putExtra("sign", sign);
        mActivity.startActivityForResult(intent, 3);
        mActivity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void changeSex(String title, String gender) {
        int select;
        if (gender.equals("男")) {
            select = 0;
        } else {
            select = 1;
        }

        final String[] strings={"男", "女"};
        new AlertDialog.Builder(mActivity)
                .setTitle(title)
                //.setIcon(android.R.drawable.ic_dialog_info)
                .setSingleChoiceItems(strings, select, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        mNewSexS = which;
                        mView.setUserSex(strings[mNewSexS]);
                        mChangeUserInfoBean.setmUserSix(mNewSexS);
                        updateUser();
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void changeSign(String title, String sign) {
        Intent intent = new Intent(mActivity, xxx.class);
        intent.putExtra("title", title);
        intent.putExtra("sign", sign);
        mActivity.startActivityForResult(intent, 4);
        mActivity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void changeHead(String title, String button) {
        Intent intent = new Intent(mActivity, xxx.class);
        intent.putExtra("title", title);
        intent.putExtra("button", button);
        mActivity.startActivityForResult(intent, 2);
    }

    @Override
    public void setUserHead() {
        mView.setUserHead( mChangeUserInfoBean.getmUser().getHeadImg());
    }


    @Override
    public void updateUser() {
        mView.showDialog();
        mChangeUserInfoBean.changeUser();
    }

    @Override
    public void hideDialog() {
        mView.hideDialog();
    }


    @Override
    public void updateResult(int requestCode, Intent data) {
        switch (requestCode) {
            case 3:
                if (data != null) {
                    String name = data.getStringExtra("xxx");

                    if (null != name && !name.isEmpty()) {
                        mView.setNickName(name);
                        mChangeUserInfoBean.setmUserNickName(name);
                        updateUser();
                    }
                }
                break;
            case 4:
                if (data != null) {
                    String signture = data.getStringExtra("xxx");
                    if (null != signture && !signture.isEmpty()) {
                        mView.setUserSign(signture);
                        mChangeUserInfoBean.setmUserSignature(signture);
                        updateUser();
                    }
                }
                break;
            default:
                break;
        }
    }

}
*/