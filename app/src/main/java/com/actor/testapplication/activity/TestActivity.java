package com.actor.testapplication.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.actor.testapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {

    @BindView(R.id.tv_result)//显示结果
    TextView tvResult;
    @BindView(R.id.btn)
    Button   btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        setTitle("测试页面");
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
    }
}
