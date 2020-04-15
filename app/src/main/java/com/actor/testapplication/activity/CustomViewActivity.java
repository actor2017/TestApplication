package com.actor.testapplication.activity;

import android.os.Bundle;
import android.view.View;

import com.actor.testapplication.R;
import com.actor.testapplication.widget.ItemAddMinusLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends BaseActivity {

    @BindView(R.id.item_add_minus)
    ItemAddMinusLayout  itemAddMinus;
//    @BindView(R.id.sliding_drawer)
//    BaseSlidingDrawer slidingDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);

        setTitle("自定义View");
    }

    @OnClick({R.id.btn1/*, R.id.view, R.id.ll_handle*/})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                break;
//            case R.id.view:
//                toast("view clicked!");
//                break;
//            case R.id.ll_handle:
//                slidingDrawer.animateToggle();
//                break;
        }
    }
}
