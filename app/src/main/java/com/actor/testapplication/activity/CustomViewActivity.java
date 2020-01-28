package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.actor.testapplication.R;
import com.actor.testapplication.widget.GridTableRadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends BaseActivity {

    @BindView(R.id.grid_table_radio_group)
    GridTableRadioGroup gridTableRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);

        setTitle("自定义View");
        gridTableRadioGroup.setOnCheckedChangeListener(new GridTableRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId, int position, boolean reChecked) {
                logFormat("checkedId = %d, pos = %d, reChecked = %b", checkedId, position, reChecked);
            }
        });
    }

    @OnClick({R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                int pos = gridTableRadioGroup.getCheckedPosition();
                gridTableRadioGroup.setCheckedPosition(1);
                break;
        }
    }
}
