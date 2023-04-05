package com.actor.testapplication.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.actor.testapplication.databinding.ActivityDragLayoutBinding;

/**
 * description: 拖拽 测试
 *
 * date       : 2020/6/28 on 18:06
 * @version 1.0
 */
public class DragLayoutActivity extends BaseActivity<ActivityDragLayoutBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout flMain = viewBinding.flMain;
    }
}
