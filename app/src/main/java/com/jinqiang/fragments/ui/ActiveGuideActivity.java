package com.jinqiang.fragments.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jinqiang.widgets.R;

public class ActiveGuideActivity extends AppCompatActivity{
    ImageView mClose;
    int[] center;
    ActiveView mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_dialog_layout);
        mClose = (ImageView)findViewById(R.id.ready_close);
        mView = (ActiveView)findViewById(R.id.activelayout);
        center = getIntent().getIntArrayExtra("location");
        if(center!=null)
            mView.getCircleCenter(center);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
