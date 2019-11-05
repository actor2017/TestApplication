package com.actor.testapplication.widget;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actor.testapplication.R;

/**
 * Description: 常用的单选输入布局,这是一个组合控件.
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/7/10 on 17:21
 * @version 1.0
 */
public class GridTableRadioGroup extends LinearLayout {

    private static final String NAME_SPACE = "http://schemas.android.com/apk/res-auto";
    private TextView tvRedStar;
    private TextView tv1;
//    private RadioGroup rg;
    private AppCompatRadioButton rb1;
    private AppCompatRadioButton rb2;
    private AppCompatRadioButton rb3;

    public GridTableRadioGroup(Context context) {
        this(context,null);
    }

    public GridTableRadioGroup(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public GridTableRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 给当前空的布局填充内容
         * 参3:A view group that will be the parent.
         * 传null表示当前布局没有父控件,大部分都传null
         * 传this表示已当前相对布局为这个布局的父控件,这样做了以后,当前空的布局就有内容了
         */
        //1.给SettingItemView(RelatuveLayout)填充布局
        View inflate = View.inflate(context, R.layout.item_grid_table_radio_group, this);
        tvRedStar = inflate.findViewById(R.id.tv_red_star_for_gtr);
        tv1 = inflate.findViewById(R.id.tv_item_for_gtr);
//        rg = (RadioGroup) inflate.findViewById(R.id.rg);
        rb1 = inflate.findViewById(R.id.rb_1_for_gtr);
        rb2 = inflate.findViewById(R.id.rb_2_for_gtr);
        rb3 = inflate.findViewById(R.id.rb_3_for_gtr);

        if (attrs == null) return;

        //读取自定义属性值
        int visiable = attrs.getAttributeIntValue(NAME_SPACE, "gtrRedStarVisiable", 0);
        tvRedStar.setVisibility(visiable * 4);//设置红点是否显示

        String gtrItemName = attrs.getAttributeValue(NAME_SPACE, "gtrItemName");//TextView的值
        String gtrRb1Text = attrs.getAttributeValue(NAME_SPACE, "gtrRb1Text");
        String gtrRb2Text = attrs.getAttributeValue(NAME_SPACE, "gtrRb2Text");
        String gtrRb3Text = attrs.getAttributeValue(NAME_SPACE, "gtrRb3Text");
        int gtrCheckedPosition = attrs.getAttributeIntValue(NAME_SPACE, "gtrCheckedPosition", 0);
        if (gtrItemName != null) tv1.setText(gtrItemName);
        if (gtrRb1Text != null) rb1.setText(gtrRb1Text);
        if (gtrRb2Text != null) rb2.setText(gtrRb2Text);
        if (gtrRb3Text == null) {
            rb3.setVisibility(GONE);
        } else {
            rb3.setText(gtrRb3Text);
        }
        setCheckedPosition(gtrCheckedPosition);
    }

    /**
     * 获取红点
     * @return
     */
    public TextView getTextViewRedStar() {
        return tvRedStar;
    }

    /**
     * 返回Item的TextView
     * @return
     */
    public TextView getTextViewItem() {
        return tv1;
    }

    public void setCheckedPosition(@IntRange(from = 0, to = 2) int position) {
        switch (position) {
            case 1:
                rb2.setChecked(true);
                break;
            case 2:
                rb3.setChecked(true);
                break;
            default:
                rb1.setChecked(true);
                break;
        }
    }

    public int getCheckedPosition() {
        if (rb1.isChecked()) return 0;
        if (rb2.isChecked()) return 1;
        if (rb3.isChecked()) return 2;
        return 0;
    }
}
