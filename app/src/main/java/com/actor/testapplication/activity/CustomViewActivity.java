package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.actor.myandroidframework.widget.ItemTextInputLayout;
import com.actor.myandroidframework.widget.QuickSearchBar;
import com.actor.testapplication.R;
import com.actor.testapplication.databinding.ActivityCustomViewBinding;
import com.actor.testapplication.widget.ColorSelector;
import com.actor.testapplication.widget.ItemAddMinusLayout;
import com.blankj.utilcode.util.ClickUtils;

public class CustomViewActivity extends BaseActivity<ActivityCustomViewBinding> {

    private ItemAddMinusLayout  itemAddMinus;
    private ColorSelector       colorSelector;
    private ItemTextInputLayout itilResult;
    private QuickSearchBar      quickSearchBar;
    private TextView            tvLetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("自定义View");
        itemAddMinus = viewBinding.itemAddMinus;
        colorSelector = viewBinding.colorSelector;
        itilResult = viewBinding.itilResult;
        quickSearchBar = viewBinding.quickSearchBar;
        tvLetter = viewBinding.tvLetter;

        //颜色选中监听
        colorSelector.setmListener(new ColorSelector.IColorSelectorListener() {
            @Override
            public void onGetColorListener(int red, int green, int blue, int color) {
                itilResult.setText(getStringFormat("R:%d G:%d B:%d RGB:%s", red, green, blue, Integer.toHexString(color)));
            }
        });

        //右侧字母选中监听
        quickSearchBar.setOnLetterChangedListener((RecyclerView) null, new QuickSearchBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                tvLetter.setVisibility(View.VISIBLE);
                tvLetter.setText(letter);
            }

            @Override
            public void onActionUp() {
                tvLetter.setVisibility(View.GONE);
            }
        });

        //点击事件
//        ClickUtils2.setMultiClicksInSends()
        itemAddMinus.getTextViewItem().setOnClickListener(new ClickUtils.OnMultiClickListener(5, 1000 / 5) {
            @Override
            public void onTriggerClick(View v) {
                startActivity(new Intent(activity, BirthdayActivity.class));
            }

            @Override
            public void onBeforeTriggerClick(View v, int count) {
            }
        });

        setOnClickListeners(R.id.btn_drag_layout);
    }

    @Override
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_drag_layout:
                startActivity(new Intent(this, DragLayoutActivity.class));
                break;
            default:
                break;
        }
    }
}
