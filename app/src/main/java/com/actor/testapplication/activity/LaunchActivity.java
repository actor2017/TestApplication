package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.actor.testapplication.utils.Global;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onBackPressed();
        if (Global.IS_LIYONG_VERSION) {
            startActivity(new Intent(this, BirthdayActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}