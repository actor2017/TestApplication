package com.actor.android5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;

import com.actor.android5.R;
import com.actor.base.BaseActivity;

/**
 * Activity转场动画
 */
public class TransitionsActivity extends BaseActivity implements View.OnClickListener {

    private View bt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.fade));
        setContentView(R.layout.activity_transitions);

        bt4 = findViewById(R.id.bt4);
        findViewById(R.id.bt1).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
        findViewById(R.id.bt3).setOnClickListener(this);
        bt4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt1) {//爆炸
            startActivityExplode(new Intent(this, ExplodeActivity.class));
//                transition = new Explode();
//                intent = new Intent(this, ExplodeActivity.class);
        } else if (id == R.id.bt2) {//淡入淡出
            startActivityFade(new Intent(this, FadeActivity.class));
//                transition = new Fade();
//                intent = new Intent(this, FadeActivity.class);
        } else if (id == R.id.bt3) {//滑动
            startActivitySlide(new Intent(this, SlideActivity.class));
//                transition = new Slide();
//                intent = new Intent(this, SlideActivity.class);
        } else if (id == R.id.bt4) {//共享元素
            startActivityShare(new Intent(this, ShareActivity.class), bt4);
//                TransitionSet set = new TransitionSet();
//                set.addTransition(new Explode());
//                set.addTransition(new Fade());
//                set.setDuration(1000);
//                getWindow().setReturnTransition(set);
//                getWindow().setReenterTransition(set);
//                getWindow().setEnterTransition(set);
//                getWindow().setExitTransition(set);
//
//                bt4.setTransitionName("bt4");
//                TransitionSet transitionSet = new TransitionSet();
//                transitionSet.addTransition(new ChangeTransform());
//                transitionSet.addTransition(new ChangeBounds());
//                transitionSet.addTarget("bt4");
//                transitionSet.setDuration(1000);
//                getWindow().setSharedElementEnterTransition(transitionSet);
//                getWindow().setSharedElementExitTransition(transitionSet);
//                getWindow().setSharedElementReturnTransition(transitionSet);
//                getWindow().setSharedElementReenterTransition(transitionSet);
//                intent = new Intent(this, ShareActivity.class);
//
//                //Activity activity, View sharedElement, String sharedElementName
////                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, bt4, bt4.getTransitionName());
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(bt4, bt4.getTransitionName()));
//                startActivity(intent, options.toBundle());
        }
//        if (v.getId() != R.id.bt4) {
//            transition.setDuration(1000);
//            getWindow().setEnterTransition(transition);//设置进场动画
//            getWindow().setExitTransition(transition);//设置出场动画
//            getWindow().setReturnTransition(transition);
//            getWindow().setReenterTransition(transition);//设置再次进场动画
//            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//        }
    }
}
