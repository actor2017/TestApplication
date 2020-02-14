package com.actor.testapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actor.myandroidframework.utils.ToastUtils;
import com.actor.testapplication.R;

/**
 * Description: 加减
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-8-19 on 21:10
 *
 * 全部属性都是iaml开头:
 * 1.左侧红点显示类型, 默认visible
 * @see R.styleable#ItemAddMinusLayout_iamlRedStarVisiable      //visible/invisible/gone
 * 2.左侧提示文字
 * @see R.styleable#ItemAddMinusLayout_iamlItemName             //请选择语言：
 * 3.显示的默认值, 默认0
 * @see R.styleable#ItemAddMinusLayout_iamlDefaultValue         //0
 * 4.最大值, 默认1
 * @see R.styleable#ItemAddMinusLayout_iamlMaxValue             //1
 * 5.最小值, 默认0
 * @see R.styleable#ItemAddMinusLayout_iamlMinValue             //0
 * 6.已经是最大值了的提示, 默认没提示
 * @see R.styleable#ItemAddMinusLayout_iamlAlreadyMaxValueHint  //"已经是最大了"
 * 7.已经是最小值了的提示, 默认没提示
 * @see R.styleable#ItemAddMinusLayout_iamlAlreadyMinValueHint  //"已经是最小了"
 *
 * @version 1.0
 */
public class ItemAddMinusLayout extends LinearLayout {

    private TextView  tvRedStar, tvItem, tvValue;
    private int minValue = 0, maxValue = 1, nowValue = 0;
    private String hintAlreadyMaxValue, hintAlreadyMinValue;

    public ItemAddMinusLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ItemAddMinusLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ItemAddMinusLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ItemAddMinusLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        View view = View.inflate(context, R.layout.item_add_minus_layout, this);
        tvRedStar = view.findViewById(R.id.tv_red_star_for_iaml);
        tvItem = view.findViewById(R.id.tv_item_name_for_iaml);
        tvValue = view.findViewById(R.id.tv_value_for_iaml);
        //减
        view.findViewById(R.id.iv_minus_for_iaml).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nowValue <= minValue) {
                    if (hintAlreadyMinValue != null) ToastUtils.show(hintAlreadyMinValue);
                } else {
                    tvValue.setText(String.valueOf(-- nowValue));
                }
            }
        });
        //加
        view.findViewById(R.id.iv_add_for_iaml).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nowValue >= maxValue) {
                    if (hintAlreadyMaxValue != null) ToastUtils.show(hintAlreadyMaxValue);
                } else {
                    tvValue.setText(String.valueOf(++ nowValue));
                }
            }
        });

        if (attrs == null) return;
        //根据xml中属性, 给view赋值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemAddMinusLayout);
        //红点是否显示
        int redStarVisiable = typedArray.getInt(R.styleable.ItemAddMinusLayout_iamlRedStarVisiable, 0);
        //左侧TextView的Text
        String itemName = typedArray.getString(R.styleable.ItemAddMinusLayout_iamlItemName);
        //最小值
        minValue = typedArray.getInt(R.styleable.ItemAddMinusLayout_iamlMinValue, 0);
        //最大值
        maxValue = typedArray.getInt(R.styleable.ItemAddMinusLayout_iamlMaxValue, 1);
        //默认值
        int defaultValue = typedArray.getInt(R.styleable.ItemAddMinusLayout_iamlDefaultValue, 0);
        //已经是最大值了的提示
        hintAlreadyMaxValue = typedArray.getString(R.styleable.ItemAddMinusLayout_iamlAlreadyMaxValueHint);
        //已经是最小值了的提示
        hintAlreadyMinValue = typedArray.getString(R.styleable.ItemAddMinusLayout_iamlAlreadyMinValueHint);
        typedArray.recycle();
        if (defaultValue < minValue || defaultValue > maxValue) defaultValue = minValue;
        nowValue = defaultValue;
        tvValue.setText(String.valueOf(nowValue));

        tvRedStar.setVisibility(redStarVisiable * 4);
        if (itemName != null) tvItem.setText(itemName);
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
        return tvItem;
    }

    /**
     * @return 返回现在的值
     */
    public int getValue() {
        return nowValue;
    }

    public void setValut(int valut) {
        nowValue = valut;
        tvValue.setText(String.valueOf(nowValue));
    }
}
