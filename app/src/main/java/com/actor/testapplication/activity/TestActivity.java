package com.actor.testapplication.activity;

import android.os.Bundle;

import com.actor.testapplication.R;

import butterknife.ButterKnife;

public class TestActivity extends BaseActivity {

//    @BindView(R.id.tv_result)//显示结果
//    TextView  tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }
}
