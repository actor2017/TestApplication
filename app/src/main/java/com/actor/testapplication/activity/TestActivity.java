package com.actor.testapplication.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.actor.testapplication.R;
import com.actor.testapplication.widget.GridTableRadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {

    @BindView(R.id.tv_result)//显示结果
    TextView            tvResult;
    @BindView(R.id.btn)
    Button              btn;
    @BindView(R.id.grid_table_radio_group)
    GridTableRadioGroup gridTableRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        setTitle("测试页面");
        gridTableRadioGroup.setOnCheckedChangeListener(new GridTableRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId, int position, boolean reChecked) {
                tvResult.setText(getStringFormat("checkedId = %d, pos = %d, reChecked = %b", checkedId, position, reChecked));
            }
        });
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        int pos = gridTableRadioGroup.getCheckedPosition();
        gridTableRadioGroup.setCheckedPosition(1);
    }
}
