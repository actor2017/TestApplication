package com.actor.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.actor.myandroidframework.utils.LogUtils;
import com.actor.testapplication.adapter.TextViewAdapter;
import com.actor.testapplication.databinding.ActivityNestedScrollViewBinding;
import com.actor.testapplication.widget.BaseNestedScrollView;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;

/**
 * description: 类的描述
 *
 * date       : 2020/6/2 on 10:55
 * @version 1.0
 */
public class NestedScrollViewActivity extends BaseActivity<ActivityNestedScrollViewBinding> {

    private BaseNestedScrollView nestedScrollView;
    private TextView tvTop1;
    private TextView     tvTop2;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nestedScrollView = viewBinding.nestedScrollView;
        tvTop1 = viewBinding.tvTop1;
        tvTop2 = viewBinding.tvTop2;
        recyclerView = viewBinding.recyclerView;

        recyclerView.setAdapter(new TextViewAdapter());

        View rootView = findViewById(android.R.id.content);

        tvTop2.post(new Runnable() {
            @Override
            public void run() {
                int actionBarHeight = BarUtils.getActionBarHeight();
                int statusBarHeight = BarUtils.getStatusBarHeight();
                int screenHeight = ScreenUtils.getScreenHeight();
                LogUtils.errorFormat("statusbarH=%d, actionbarH=%d, screenH=%d", statusBarHeight, actionBarHeight, screenHeight);

                int height1 = tvTop1.getHeight();
                int heightRoot = rootView.getHeight();
                int height2 = tvTop2.getHeight();
                LogUtils.errorFormat("rootH=%d, tv1H=%d, tv2H=%d", heightRoot, height1, height2);

                nestedScrollView.setMyScrollHeight(height1);
                int rvNewHeight = heightRoot - height2;

                ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
                layoutParams.height = rvNewHeight;
                recyclerView.setLayoutParams(layoutParams);
            }
        });
    }
}
