package com.actor.android5.activity;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;

import com.actor.android5.R;
import com.actor.base.BaseActivity;

public class ShareActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().setSharedElementsUseOverlay(true);

        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new Fade());
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTarget("bt4");
        transitionSet.setDuration(1000);
        getWindow().setSharedElementEnterTransition(transitionSet);
        getWindow().setSharedElementExitTransition(transitionSet);
        getWindow().setSharedElementReturnTransition(transitionSet);
        getWindow().setSharedElementReenterTransition(transitionSet);
        setContentView(R.layout.activity_share);

//        findViewById(R.id.bt4)
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
