package com.actor.testapplication.activity;

import android.os.Bundle;
import android.view.View;

import com.actor.testapplication.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                break;
        }
    }
}
