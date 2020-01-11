package com.actor.testapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
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
 * @version 1.0
 */
public class GridTableAddMinus extends LinearLayout {

    private              TextView  tvRedStar;
    private              TextView  tv1;
    private TextView tvValue;
    private int minValue = 0, maxValue = 1, nowValue = 0;

    public GridTableAddMinus(Context context) {
        this(context,null);
    }

    public GridTableAddMinus(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public GridTableAddMinus(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = View.inflate(context, R.layout.item_grid_table_add_minus, this);
        tvRedStar = inflate.findViewById(R.id.tv_red_star_for_gtam);
        tv1 = inflate.findViewById(R.id.tv_item_name_for_gtam);
        tvValue = inflate.findViewById(R.id.tv_value_for_gtam);
        //减
        inflate.findViewById(R.id.iv_minus_for_gtam).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nowValue <= minValue) {
                    ToastUtils.show("已经是最小了");
                } else {
                    tvValue.setText(String.valueOf(-- nowValue));
                }
            }
        });
        //加
        inflate.findViewById(R.id.iv_add_for_gtam).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nowValue >= maxValue) {
                    ToastUtils.show("已经是最大了");
                } else {
                    tvValue.setText(String.valueOf(++ nowValue));
                }
            }
        });

        if (attrs == null) return;
        //根据xml中属性, 给view赋值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GridTableAddMinus);
        //红点是否显示
        int redStarVisiable = typedArray.getInt(R.styleable.GridTableAddMinus_gtamRedStarVisiable, 0);
        //左侧TextView的Text
        String gtamItemName = typedArray.getString(R.styleable.GridTableAddMinus_gtamItemName);
        //最小值
        minValue = typedArray.getInt(R.styleable.GridTableAddMinus_gtamMinValue, 0);
        //最大值
        maxValue = typedArray.getInt(R.styleable.GridTableAddMinus_gtamMaxValue, 1);
        //默认值
        int defaultValue = typedArray.getInt(R.styleable.GridTableAddMinus_gtamDefaultValue, 0);
        if (defaultValue < minValue || defaultValue > maxValue) defaultValue = minValue;
        nowValue = defaultValue;
        tvValue.setText(String.valueOf(nowValue));

        tvRedStar.setVisibility(redStarVisiable * 4);
        if (gtamItemName != null) tv1.setText(gtamItemName);
        typedArray.recycle();
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
