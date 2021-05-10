package com.actor.testapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.actor.testapplication.R;

/**
 * description: Path画线
 *
 * date       : 2021/4/7 on 14
 * @version 1.0
 */
public class PathView extends View {

    protected Paint mPaint;//笔

    @ColorInt
    protected int   color     = Color.RED;  //颜色
    protected float lineWidth = 5.0F;       //线宽
    private   Path  mPath;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        //创建Path对象
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        if (attrs != null) {
            //根据xml中属性, 给view赋值
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PathView);
            color = typedArray.getColor(R.styleable.PathView_pvColor, color);
            lineWidth = typedArray.getDimension(R.styleable.PathView_pvLineWidth, lineWidth);

            typedArray.recycle();
        }
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);//空心,填充模式 - 描边
        mPaint.setStrokeWidth(lineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);//画出路径
    }

    public Paint getPaint() {
        return mPaint;
    }

    public Path getPath() {
        return mPath;
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



    ///////////////////////////////////////////////////////////////////////////
    // Path 直线与点的操作
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 移动到指定点(将下一次操作路径的起点移动到(x,y)), 可和lineTo混合调用
     * 会影响下次操作，不会影响上一次操作
     */
    public void moveTo(float x, float y) {
        mPath.moveTo(x, y);
    }

    /**
     * 从当前点画一条直线到指定点
     */
    public void lineTo (float x, float y) {
        mPath.lineTo(x, y);
    }

    /**
     * 改变上一次操作路径的结束坐标点, 会影响下次操作，也会影响上一次操作
     * path.lineTo(300, 300);       //创建一条从原点到坐标(300,300)的直线
     * path.lineTo(100, 600);
     * path.lineTo(100, 800);
     * path.setLastPoint(500, 300);  //将上一次的操作路径的终点(100, 800)"移动"到(500, 300)
     * 等价于: ==>
     * path.lineTo(300, 300);
     * path.lineTo(100, 600);
     * path.lineTo(500, 300);
     */
    public void setLastPoint(float dx, float dy) {
        mPath.setLastPoint(dx, dy);
    }

    /**
     * 封闭当前路径
     */
    public void close() {
        mPath.close();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Path 基本形状
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 矩形
     * @param rect
     * @param dir
     */
    public void addRect(RectF rect, Path.Direction dir) {
        addRect(rect.left, rect.top, rect.right, rect.bottom, dir);
    }
    /**
     * 矩形
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param dir
     */
    public void addRect(float left, float top, float right, float bottom, Path.Direction dir) {
        mPath.addRect(left, top, right, bottom, dir);
    }

    /**
     * 圆形
     * @param x
     * @param y
     * @param radius
     * @param dir
     */
    public void addCircle(float x, float y, float radius, Path.Direction dir) {
        mPath.addCircle(x, y, radius, dir);
    }

    /**
     * 圆角矩形
     * @param rect
     * @param radii
     * @param dir
     */
    public void addRoundRect(@NonNull RectF rect, float rx, float ry, @NonNull Path.Direction dir) {
        addRoundRect(rect.left, rect.top, rect.right, rect.bottom, rx, ry, dir);
    }
    public void addRoundRect (float left,float top,float right,float bottom,float rx,float ry, Path.Direction dir) {

    }
    public void addRoundRect(RectF rect, float[] radii, Path.Direction dir) {
        mPath.addRoundRect(rect, radii, dir);
    }
    public void addRoundRect (float left,float top,float right,float bottom,float[] radii,Path.Direction dir) {

    }

}
