package com.actor.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actor.testapplication.databinding.ActivityTestBinding;


/**
 * Description: Test测试页面
 * Date       : 2019/12/30 on 11:38
 */
public class TestActivity extends BaseActivity<ActivityTestBinding> {

    private TextView tvResult;//显示结果
    private EditText editText;
    private Button   btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvResult = viewBinding.tvResult;
        editText = viewBinding.editText;
        btn = viewBinding.btn;
        setTitle("Test测试页面");
    }

    @Override
    public void onViewClicked(View view) {
    }
}
