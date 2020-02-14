package com.actor.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.actor.testapplication.R;
import com.actor.testapplication.widget.ItemRadioGroupLayout;
import com.actor.testapplication.widget.ItemAddMinusLayout;
import com.actor.testapplication.widget.ItemSpinnerLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends BaseActivity {

    @BindView(R.id.item_radio_group)
    ItemRadioGroupLayout itemRadioGroupLayout;
    @BindView(R.id.item_add_minus)
    ItemAddMinusLayout  itemAddMinus;
    @BindView(R.id.item_spinner)
    ItemSpinnerLayout   itemSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);

        setTitle("自定义View");
        itemRadioGroupLayout.setOnCheckedChangeListener(new ItemRadioGroupLayout.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId, int position, boolean reChecked) {
                String format = getStringFormat("checkedId=%d, pos=%d, reChecked=%b", checkedId, position, reChecked);
                logFormat(format);
                toast(format);
            }
        });
    }

    @OnClick({R.id.btn_check_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check_1:
                int pos = itemRadioGroupLayout.getCheckedPosition();
                itemRadioGroupLayout.setCheckedPosition(1);
                break;
        }
    }
}
