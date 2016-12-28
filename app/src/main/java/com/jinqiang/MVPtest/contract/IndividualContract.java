package com.jinqiang.MVPtest.contract;


import android.content.Intent;

public interface IndividualContract {
    interface View {


        //         设置用户名称显示
        void setNickName(String name);

        //         设置用户性别显示
        void setUserSex(String sex);

        //         设置用户签名显示
        void setUserSign(String sign);

        //         设置用户账号显示
        void setUserCode(String code);

        //         设置用户头像显示
        void setUserHead(String code);

        void showDialog();

        void hideDialog();

    }

    interface Presenter {

        void initData();

        void changeNikeName(String title, String sign);

        void changeSex(String title, String gender);

        void changeSign(String title, String sign);

        void changeHead(String title, String button);

        //         设置用户头像显示
        void  setUserHead();

        void updateUser();

        void hideDialog();

        void updateResult(int requestCode, Intent data);
    }
}
