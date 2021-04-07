package com.actor.testapplication.activity;

import android.os.Bundle;

import com.actor.testapplication.R;
import com.actor.testapplication.widget.PathView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Path绘制测试
 *
 * F:\AndroidProjects\AndroidNote\Animation动画\3.2.Path动画(用属性动画实现)
 *
 * F:\AndroidProjects\AndroidNote\Widget 自定义控件,自定义View\6.Paint 笔
 * F:\AndroidProjects\AndroidNote\Widget 自定义控件,自定义View\7.Canvas画板
 * F:\AndroidProjects\AndroidNote\Widget 自定义控件,自定义View\8.Path矢量图\基本Api
 *
 */
public class PathDrawActivity extends BaseActivity {

    @BindView(R.id.path_view)
    PathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_draw);
        ButterKnife.bind(this);
    }
}