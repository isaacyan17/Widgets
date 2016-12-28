package com.jinqiang.MVPtest.Model;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

/*

public class ChangeUserInfoBean {
    private User mUser; //用户信息Bean
    private Context context;
    private IndividualPresenter mIndividualPresenter;
    public ChangeUserInfoBean(IndividualPresenter mIndividualPresenter){
        this.mIndividualPresenter=mIndividualPresenter;
        this.context=mIndividualPresenter.getContext();
    }
    public User getmUser(){
        this.mUser=UserInfoManager.getUser(context);
        return  mUser;
    }
    public void setmUserNickName(String name){
        mUser.setNickName(name);
    }
    public void setmUserSignature(String signature){
        mUser.setUserSign(signature);
    }
    public void setmUserSix(int six){
        mUser.setUserSex(String.valueOf(six));
    }
    public void changeUser() {
        JSONObject json = new JSONObject();
        try {
           ...
           //网络请求
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void success(String s, int action) {
        if(action==0){
            mIndividualPresenter.hideDialog();
            //网络请求回调成功处理
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void failed(String s, int action) {
        mIndividualPresenter.hideDialog();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}

*/
