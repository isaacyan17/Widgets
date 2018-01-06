package com.jinqiang.fragments.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jinqiang.Utils.TimeLineLinearLayout;
import com.jinqiang.widgets.R;

/**
 * @author: jinqiang
 * @time: 2018/1/6 14:44
 * @desc:
 */

public class TimeLineShowActivity extends AppCompatActivity {
    private TimeLineLinearLayout timeLayout;
    private String[] step={"读卡","选金额","支付","贴卡"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_show_layout);
        timeLayout = (TimeLineLinearLayout) findViewById(R.id.timeLayout);
        initTimeLine();
    }

    private void initTimeLine(){
        for(int i=0;i<step.length;i++){
            View v = LayoutInflater.from(this).inflate(R.layout.timeline_item_layout, timeLayout, false);
            TextView textView = (TextView) v.findViewById(R.id.tv);
            textView.setText(step[i]);
            if(i==0){
                textView.setTextColor(Color.parseColor("#0bb9ff"));
            }else{
                textView.setTextColor(Color.parseColor("#333333"));
            }
            timeLayout.addView(v);
        }
    }
}
