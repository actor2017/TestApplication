package com.actor.testapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.actor.testapplication.R;

/**
 * Description: 常用的单选输入布局,这是一个组合控件.
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/7/10 on 17:21
 *
 * 1.左侧红点显示类型, 默认visible
 * @see R.styleable#GridTableRadioGroup_gtrRedStarVisiable    //visible/invisible/gone
 * 2.左侧提示文字
 * @see R.styleable#GridTableRadioGroup_gtrItemName         //请选择性别：
 * 3.右侧RadioButton的gravity
 * @see R.styleable#GridTableRadioGroup_gtrGravity          //start|centerVertical
 * 4.RadioButton1的值
 * @see R.styleable#GridTableRadioGroup_gtrRb1Text          //男
 * 5.RadioButton2的值
 * @see R.styleable#GridTableRadioGroup_gtrRb2Text          //女
 * 6.RadioButton3的值
 * @see R.styleable#GridTableRadioGroup_gtrRb3Text          //未知
 * 7.选中第几个, 默认0
 * @see R.styleable#GridTableRadioGroup_gtrCheckedPosition  //0
 *
 * @version 1.0
 * @version 1.1 增加gtrGravity属性
 * @version 1.1.1 修改方法: {@link #setCheckedPosition(int)} 增加重复选中监听
 *
 * TODO: 2019/7/10 动态增加RadioButton
 */
public class GridTableRadioGroup extends LinearLayout {

    private TextView tvRedStar;
    private TextView tv1;
    private RadioGroup radioGroup;
    private AppCompatRadioButton rb1;
    private AppCompatRadioButton rb2;
    private AppCompatRadioButton rb3;
    private OnCheckedChangeListener onCheckedChangeListener;

    public GridTableRadioGroup(Context context) {
        super(context);
        init(context, null, -1);
    }

    public GridTableRadioGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1);
    }

    public GridTableRadioGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GridTableRadioGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        /**
         * 给当前空的布局填充内容
         * 参3:A view group that will be the parent.
         * 传null表示当前布局没有父控件,大部分都传null
         * 传this表示已当前相对布局为这个布局的父控件,这样做了以后,当前空的布局就有内容了
         */
        //填充布局
        View inflate = View.inflate(context, R.layout.item_grid_table_radio_group, this);
        tvRedStar = inflate.findViewById(R.id.tv_red_star_for_gtr);
        tv1 = inflate.findViewById(R.id.tv_item_for_gtr);
        radioGroup = inflate.findViewById(R.id.rg_for_gtr);
        rb1 = inflate.findViewById(R.id.rb_1_for_gtr);
        rb2 = inflate.findViewById(R.id.rb_2_for_gtr);
        rb3 = inflate.findViewById(R.id.rb_3_for_gtr);

        if (attrs == null) return;

        //读取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GridTableRadioGroup);
        //红点是否显示
        int visiable = typedArray.getInt(R.styleable.GridTableRadioGroup_gtrRedStarVisiable, 0);
        //左侧TextView的Text
        String gtrItemName = typedArray.getString(R.styleable.GridTableRadioGroup_gtrItemName);
        //radioButton1的值
        String gtrRb1Text = typedArray.getString(R.styleable.GridTableRadioGroup_gtrRb1Text);
        String gtrRb2Text = typedArray.getString(R.styleable.GridTableRadioGroup_gtrRb2Text);
        String gtrRb3Text = typedArray.getString(R.styleable.GridTableRadioGroup_gtrRb3Text);
        //默认选中第几个
        int gtrCheckedPosition = typedArray.getInt(R.styleable.GridTableRadioGroup_gtrCheckedPosition, 0);
        //RadioButton的居中gravity
        int gravity = typedArray.getInt(R.styleable.GridTableRadioGroup_gtrGravity, Gravity.START | Gravity.CENTER_VERTICAL);
        typedArray.recycle();

        tvRedStar.setVisibility(visiable * 4);//设置红点是否显示
        if (gtrItemName != null) tv1.setText(gtrItemName);
        if (gtrRb1Text != null) rb1.setText(gtrRb1Text);
        if (gtrRb2Text != null) rb2.setText(gtrRb2Text);
        if (gtrRb3Text == null) {
            rb3.setVisibility(GONE);
        } else {
            rb3.setText(gtrRb3Text);
        }
        setCheckedPosition(gtrCheckedPosition);
        radioGroup.setGravity(gravity);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (onCheckedChangeListener != null) onCheckedChangeListener.onCheckedChanged(group, checkedId, getCheckedPosition(), false);
        });
    }

    /**
     * @return 获取红点
     */
    public TextView getTextViewRedStar() {
        return tvRedStar;
    }

    /**
     * @return 返回Item的TextView
     */
    public TextView getTextViewItem() {
        return tv1;
    }

    /**
     * @return 返回RadioGroup
     */
    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public void setCheckedPosition(@IntRange(from = 0, to = 2) int position) {
        int checkedPosition = getCheckedPosition();
        switch (position) {
            case 1:
//                radioGroup.check(R.id.rb_2_for_gtr);//这种方式不行, 会回调多次
                rb2.setChecked(true);
                break;
            case 2:
                rb3.setChecked(true);
                break;
            default:
                rb1.setChecked(true);
                break;
        }
        //重复选中
        if (onCheckedChangeListener != null && checkedPosition == position) {
            onCheckedChangeListener.onCheckedChanged(radioGroup, radioGroup.getCheckedRadioButtonId(), position, true);
        }
    }

    public int getCheckedPosition() {
        if (rb1.isChecked()) return 0;
        if (rb2.isChecked()) return 1;
        if (rb3.isChecked()) return 2;
        return -1;//radioGroup.clearCheck();
    }

    /**
     * @param listener 设置选中监听
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;

    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        /**
         * @param group radioGroup
         * @param checkedId 选中的Checkbox的id
         * @param position 第几个position
         * @param reChecked 是否是重复选中
         */
        void onCheckedChanged(RadioGroup group, @IdRes int checkedId, int position, boolean reChecked);
    }
}
