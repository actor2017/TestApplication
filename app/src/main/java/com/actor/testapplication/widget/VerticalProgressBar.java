package com.actor.testapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Description: 垂直进度条
 * Author     : ldf
 * Date       : 2017/8/9 on 22:48
 */

public class VerticalProgressBar extends View {

    protected int progress = 0;
    protected int max = 100;
    protected Paint paint;

    public VerticalProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public VerticalProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VerticalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public VerticalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //进度百分比
        float percent = 1.0F * progress / max;
        if (percent > 0.8F) {
            paint.setColor(Color.RED);
        } else if (percent >= 0.5F) {
            paint.setColor(Color.YELLOW);
        } else paint.setColor(Color.GREEN);
        int height = getHeight();
        //进度条离顶部的距离
        int marginTop = (int)(percent * height);
//        int marginTop = (int)(1.0 * progress / max * height);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //rx, ry:圆角矩形两边的半径,示例:getWidth()/2
            //float left, float top, float right, float bottom, float rx, float ry, @NonNull Paint paint
            canvas.drawRoundRect(0, height - marginTop, getWidth(), height, 10, 10, paint);
        } else {
            //float left, float top, float right, float bottom, @NonNull Paint paint
            canvas.drawRect(0, height - marginTop, getWidth(), height, paint);
        }
    }

    //获取进度
    public int getProgress() {
        return progress;
    }

    //设置进度
    public void setProgress(int progress) {
        this.progress = progress;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            invalidate();
        } else postInvalidate();
    }

    //设置最大值
    public int getMax() {
        return max;
    }

    //设置最小值
    public void setMax(int max) {
        this.max = max;
    }
}
