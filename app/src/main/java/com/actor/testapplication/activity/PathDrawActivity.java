package com.actor.testapplication.activity;

import android.os.Bundle;
import android.view.View;

import com.actor.testapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Path绘制测试
 */
public class PathDrawActivity extends BaseActivity {

    @BindView(R.id.view)
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_draw);
        ButterKnife.bind(this);
    }
}