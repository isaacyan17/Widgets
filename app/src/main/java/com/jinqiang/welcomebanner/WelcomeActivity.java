package com.jinqiang.welcomebanner;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.jinqiang.widgets.R;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @author jingqiang
 * @desc TODO
 */
public class WelcomeActivity extends Activity{

    @Bind(R.id.welcome_img)
    ImageView mImg;
    //这里可以是图片集
    private int[] imgs = {
            R.mipmap.welcome_img
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        ButterKnife.bind(this);
        mImg.setImageResource(imgs[0]);
        //timer操作符延迟1秒后执行动画。
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startAnimation();
                    }
                });
    }

    private void startAnimation() {
        //ofFloat(T target, Property<T, Float> property, float... values)
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mImg,"scaleX",1f, 1.25f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mImg,"scaleY",1f, 1.25f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1500)
                    .play(animatorX)
                    .with(animatorY);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(WelcomeActivity.this,"Done",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


}
