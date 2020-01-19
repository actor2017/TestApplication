package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.actor.testapplication.R;
import com.actor.testapplication.widget.GridTableRadioGroup;
import com.actor.testapplication.widget.ItemTextInputLayout;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.options1)
    WheelView    wheelView;
    List<String> sdf = new ArrayList<>();

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

        sdf.add("sdfsdfsdf");
        sdf.add("sdfsdf123sdf");
        sdf.add("sdfsdfs234234df");
        /**
         * 网络请求/延时 设置Adapter, 不显示数据...
         */
        wheelView.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * date的类型: IPickerViewData, Integer, toString()
                 * @see WheelView#getContentText(Object)
                 */
                wheelView.setAdapter(new ArrayWheelAdapter<>(sdf));
                wheelView.setCurrentItem(0);//初始化时显示的数据, 默认第0条
                wheelView.setIsOptions(true);//不设置就不显示, 默认false, 意义?
                wheelView.setCyclic(false);//不循环滚动, 默认true
            }
        }, 1000);



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
