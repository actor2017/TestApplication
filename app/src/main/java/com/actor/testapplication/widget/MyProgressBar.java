package com.actor.testapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.actor.testapplication.utils.Global;

/**
 * description: 进度条上加个文字
 * company    :
 *
 * @author : ldf
 * date       : 2021/12/6 on 19
 * @version 1.0
 */
// TODO: 2022/3/4 群里发的, 待测试
public class MyProgressBar extends ProgressBar {

    protected static final int PROGRESS_BAR_HEIGHT = Global.DP1 * 25;
    protected static final int TEXT_OFFSET = Global.DP1 * 5;
    protected Paint paint;

    public MyProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint();
        //设置样式
        //设置是否不确定模式
        setIndeterminate(false);
        setProgressDrawable(context.getDrawable(android.R.drawable.progress_horizontal));
        setIndeterminateDrawable(context.getDrawable(android.R.drawable.progress_indeterminate_horizontal));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 重新设置进度条的宽度和高度
        Drawable drawable = getProgressDrawable();
        if (drawable != null) {
            drawable.setBounds(0, 0, getMeasuredWidth(), PROGRESS_BAR_HEIGHT);
        }
        super.onDraw(canvas);

        final float w = getMeasuredWidth();
        final int progress = getProgress();
        final String text = progress + "%";
        final Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        final float baseLine = fontMetrics.descent - fontMetrics.ascent - fontMetrics.descent;
        final float textWidth = paint.measureText(text);
        // 设置x，不能越界
        float x = w * progress * 0.01f - textWidth / 2f;
        if (x < 0) {
            x = 0;
        }
        if (x + textWidth > w) {
            x = w - textWidth;
        }
        canvas.drawText(text, x, PROGRESS_BAR_HEIGHT + baseLine + TEXT_OFFSET, paint);
    }
}
