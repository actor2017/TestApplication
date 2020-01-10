package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.actor.testapplication.R;
import com.actor.testapplication.widget.ItemTextInputLayout;
import com.actor.testapplication.widget.GridTableRadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends BaseActivity {

    @BindView(R.id.itil1)
    ItemTextInputLayout itil1;
    @BindView(R.id.grid_table_radio_group)
    GridTableRadioGroup gridTableRadioGroup;
    private boolean isAbc = true;
    String regEx1 = "[^a-zA-Z0-9\u4E00-\u9FA5]";  //只能输入字母,数字,文字
    String regEx2 = "[^a-z]";  //只能输入字母,数字,文字

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

    @OnClick({R.id.btn, R.id.btn1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                int pos = gridTableRadioGroup.getCheckedPosition();
                gridTableRadioGroup.setCheckedPosition(1);
                if (isAbc) {
                    itil1.setDigits("123456", true);
//                    itil1.setDigitsRegex(regEx1, true);
                } else {
//                itil1.setDigits("abcdefg", true);
                    itil1.setDigitsRegex(regEx2, true);
                }
                isAbc = !isAbc;
                break;
            case R.id.btn1:
                startActivity(new Intent(this, TestActivity.class));
                break;
        }

    }
}
