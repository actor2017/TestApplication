package com.actor.android5.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Fade;

import com.actor.android5.R;
import com.actor.base.BaseActivity;

public class FadeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        setContentView(R.layout.activity_animation);

        Fade fade = new Fade();
        fade.setDuration(5000);
        //fade.addTarget(findViewById(R.id.view));
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        Intent intent = getIntent();
        System.out.println(intent.getDataString());             //callPhone://buzhidao
        Uri data = intent.getData();
        System.out.println("Scheme:" + data.getScheme());       //callPhone
        System.out.println("Authority:" + data.getAuthority()); //buzhidao
        System.out.println("Host:" + data.getHost());           //buzhidao
        System.out.println("Port:" + data.getPort());           //-1
        System.out.println("Path:" + data.getPath());           //
        System.out.println("Query:" + data.getQuery());         //null
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
