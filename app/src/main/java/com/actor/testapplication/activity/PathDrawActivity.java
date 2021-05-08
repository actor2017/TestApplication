package com.actor.testapplication.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.actor.myandroidframework.widget.BaseSpinner;
import com.actor.testapplication.R;
import com.actor.testapplication.widget.PathView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Path绘制测试
 *
 *  TODO: 2021/5/8 亲爱的自己, 下次再修改的时候是什么时候?
 * PathMeasure:
 * https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=PathMeasure&oq=PathMeasure
 * https://blog.csdn.net/weiwozhiyi/article/details/70149147
 *
 *
 *
 * android 根据点计算贝塞尔曲线:
 * https://www.baidu.com/s?ie=UTF-8&wd=android%20%E6%A0%B9%E6%8D%AE%E7%82%B9%E8%AE%A1%E7%AE%97%E8%B4%9D%E5%A1%9E%E5%B0%94%E6%9B%B2%E7%BA%BF
 *
 *
 *
 * doodleview:
 * https://www.baidu.com/s?ie=UTF-8&wd=doodleview
 *
 *
 * F:\AndroidProjects\AndroidNote\Animation动画\3.2.Path动画(用属性动画实现)
 *
 * F:\AndroidProjects\AndroidNote\Widget 自定义控件,自定义View\6.Paint 笔
 * F:\AndroidProjects\AndroidNote\Widget 自定义控件,自定义View\7.Canvas画板
 * F:\AndroidProjects\AndroidNote\Widget 自定义控件,自定义View\8.Path矢量图\基本Api
 *
 */
public class PathDrawActivity extends BaseActivity {

    @BindView(R.id.bs_paint_style)
    BaseSpinner bsPaintStyle;
    @BindView(R.id.et_x)
    EditText etX;
    @BindView(R.id.et_y)
    EditText etY;
    @BindView(R.id.path_view)
    PathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_draw);
        ButterKnife.bind(this);
        setTitle("Path绘制测试");
        bsPaintStyle.setOnItemSelectedListener(new BaseSpinner.OnItemSelectedListener2() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        pathView.getPaint().setStyle(Paint.Style.FILL);//画实心圆?
                        break;
                    case 2:
                        pathView.getPaint().setStyle(Paint.Style.STROKE);
                        break;
                    case 3:
                        pathView.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @OnClick({R.id.btn_lineto, })
    public void onViewClicked(View view) {
        float etx = Float.parseFloat(getText(etX));
        float ety = Float.parseFloat(getText(etY));
        switch (view.getId()) {
            case R.id.btn_lineto://lineTo
                pathView.lineTo(etx, ety);
                pathView.lineTo(100, 600);
                pathView.lineTo(100, 800);
                pathView.setLastPoint(500,300);
                pathView.invalidate();
                break;
            default:
                break;
        }
    }
}