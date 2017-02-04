package com.jinqiang.materialLogin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.jinqiang.widgets.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.register_edname)
    EditText account;
    @Bind(R.id.register_edpwd)
    EditText password;
    @Bind(R.id.register_edpwd_again)
    EditText passwordAgain;
    @Bind(R.id.register_fab)
    FloatingActionButton fab;
    @Bind(R.id.register_btn)
    Button next;
    @Bind(R.id.cardview)
    CardView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_register);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            startEnterAnimation();
        }
        fab.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @TargetApi(21)
    private void startEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.registertransition);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cv.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animationCompleteShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void animationCompleteShow(){
        Animator animator = ViewAnimationUtils.createCircularReveal(cv, cv.getWidth() / 2, 0, fab.getWidth() / 2, cv.getHeight());
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                cv.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        animator.start();
    }

    private void animationCloseRegister(){
        Animator animator = ViewAnimationUtils.createCircularReveal(cv, cv.getWidth() / 2, 0, cv.getHeight(),fab.getWidth() / 2);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                cv.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.mipmap.register);
                RegisterActivity.super.onBackPressed();
            }
        });
        animator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_fab:
                animationCloseRegister();
            break;
            case R.id.register_btn:
                // TODO: 2017/1/23 下一步
                break;
        }
    }
}
