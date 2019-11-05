package com.actor.testapplication.widget;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actor.testapplication.R;

import java.util.List;

/**
 * Description: 常用的Spinner选择布局,这是一个组合控件.
 * 注意:本控件的item里面只有String,没有做其余复杂扩展.
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/7/10 on 17:22
 *
 * 用法:
 * 1.在xml里面写数据源或者代码里面:gridTableSpinner.setDatas(this,myDatas);
 * 2.获取/设置数据的时候,直接调取本类的 get/set 方法即可.
 *
 * @version 1.0
 */
public class GridTableSpinner extends LinearLayout {

    private static final String NAME_SPACE = "http://schemas.android.com/apk/res-auto";
    private TextView tvRedStar;
    private TextView tv1;
    private AppCompatSpinner spinner;

    public GridTableSpinner(Context context) {
        this(context, null);
    }

    public GridTableSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public GridTableSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 给当前空的布局填充内容
         * 参3:A view group that will be the parent.
         * 传null表示当前布局没有父控件,大部分都传null
         * 传this表示已当前相对布局为这个布局的父控件,这样做了以后,当前空的布局就有内容了
         */
        View view = View.inflate(context, R.layout.item_grid_table_spinner, this);
        tvRedStar = view.findViewById(R.id.tv_red_star_for_gts);
        tv1 = view.findViewById(R.id.tv_item_for_gts);
        spinner = view.findViewById(R.id.spinner_for_gts);

        if (attrs == null) return;

        //读取自定义属性值
        int visiable = attrs.getAttributeIntValue(NAME_SPACE, "gtsRedStarVisiable", 0);
        tvRedStar.setVisibility(visiable * 4);//设置红点是否显示

        //TextView的值
        String gtsItemName = attrs.getAttributeValue(NAME_SPACE, "gtsItemName");
        if (gtsItemName != null) tv1.setText(gtsItemName);

        //如果有数据源,获取数据源并加载
        int gtsEntries = attrs.getAttributeResourceValue(NAME_SPACE, "gtsEntries", 0);
        if (gtsEntries != 0) {
            String[] textArray = context.getResources().getStringArray(gtsEntries);
            setDatas(textArray);
        }
    }

    /**
     * 获取红点
     *
     * @return
     */
    public TextView getTextViewRedStar() {
        return tvRedStar;
    }

    /**
     * 返回Item的TextView
     *
     * @return
     */
    public TextView getTextViewItem() {
        return tv1;
    }

    /**
     * 传入String[]
     *
     * @param datas
     */
    public void setDatas(String[] datas) {
        if (datas != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, datas);
            adapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    /**
     * 可传入List和ArrayList
     *
     * @param datas
     */
    public void setDatas(List<String> datas) {
        if (datas != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout
                    .simple_spinner_item, datas);
            adapter.setDropDownViewResource(android.support.v7.appcompat.R.layout
                    .support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    /**
     * 直接返回该条目的值.(item只有TextView的时候有效,否则返回该item的地址值)
     *
     * @return
     */
    public String getSelectedItemText() {
        if (spinner != null && spinner.getSelectedItem() != null) {
            return spinner.getSelectedItem().toString();
        } else return null;//adapter = null的时候,会空指针
    }

    /**
     * 获取选中的哪一项
     * 注意:如果adapter = null,返回-1
     * @return
     */
    public int getSelectedItemPosition() {
        return spinner.getSelectedItemPosition();
    }

    /**
     * 选中某一项
     * @param position 必须 >= 0
     */
    public void setSelectedItemPosition(@IntRange(from = 0) int position) {
        if (position >= 0 && position < spinner.getCount()) {
            spinner.setSelection(position);
        }
    }

    /**
     * 判断是否选择的最后一个Item
     */
    public boolean isSelectedLastPosition() {
        return getSelectedItemPosition() == spinner.getCount() - 1;
    }
}
