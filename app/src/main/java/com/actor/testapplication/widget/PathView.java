package com.actor.testapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.actor.testapplication.R;

/**
 * description: Path画线
 *
 * @author : 李大发
 * date       : 2021/4/7 on 14
 * @version 1.0
 */
public class PathView extends View {

    protected Paint paint;//笔

    @ColorInt
    protected int   color     = Color.CYAN;//颜色
    protected float lineWidth = 2.0F;      //线宽

    public PathView(Context context) {
        super(context);
        init(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        if (attrs != null) {
            //根据xml中属性, 给view赋值
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PathView);
            color = typedArray.getColor(R.styleable.PathView_pvColor, color);
            lineWidth = typedArray.getDimension(R.styleable.PathView_pvLineWidth, lineWidth);

            typedArray.recycle();
        }
        paint.setColor(color);
        paint.setStrokeWidth(lineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 设置笔的颜色
     */
    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    /**
     * 设置笔的线宽
     */
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }
}
