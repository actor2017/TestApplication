package com.actor.testapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;

/**
 * description: 描述
 * company    :
 *
 * @author : ldf
 * date       : 2021/11/16 on 10
 * @version 1.0
 */
// TODO: 2021/11/16 添加注释
public class ColorSelector extends View {

    private Paint                  bnPaint;
    private Shader                 bnShader;
    private int                    brightness;
    private int                    color;
    private Bitmap                 colorBitmap;
    private int[]                  colors;
    private int                    mBrightnessLine;
    private Rect                   mBrightnessRect;
    private Canvas                 mCanvas;
    private IColorSelectorListener mListener;
    private Point                  mPoint;
    private Point                  mSelectPoint;
    private Rect                   mShowRect;
    private PointF                 mTouchPoint;
    private int                    radius;
    private Paint                  rgPaint;
    private Paint                  sgPaint;
    private int                    showRectSelectorSpace;

    public ColorSelector(Context context) {
        super(context);
        init(context, null);
    }

    public ColorSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ColorSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        color = -16776961;
        colors = new int[]{SupportMenu.CATEGORY_MASK, -65281, -16776961, -16711681, -16711936, -256, SupportMenu.CATEGORY_MASK};
        showRectSelectorSpace = 100;
        mBrightnessLine = 0;
        brightness = 0;
        mSelectPoint = new Point();
    }

    public float computeBothPointSpace(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private void computeColor(int pixel) {
        int red = Color.red(pixel);
        int blue = Color.blue(pixel);
        int green = Color.green(pixel);
        color = Color.rgb(red, green, blue);
        invalidate();
        if (mListener != null) {
            mListener.onGetColorListener(red, green, blue, color);
        }
    }

    private void drawColorSelectorBitmap() {
        int i;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bnShader);
        mCanvas.drawRect(mBrightnessRect, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0f);
        mCanvas.drawRect(mBrightnessRect, paint);
        paint.setColor(SupportMenu.CATEGORY_MASK);
        mCanvas.drawLine((float) mBrightnessRect.left, (float) mBrightnessLine, (float) mBrightnessRect.right, (float) mBrightnessLine, paint);
        mCanvas.drawCircle((float) mPoint.x, (float) mPoint.y, (float) radius, sgPaint);
        mCanvas.drawCircle((float) mPoint.x, (float) mPoint.y, (float) radius, rgPaint);
        Paint paint2 = bnPaint;
        if (brightness < 0) {
            i = 0;
        } else {
            i = brightness > MotionEventCompat.ACTION_MASK ? MotionEventCompat.ACTION_MASK : brightness;
        }
        paint2.setAlpha(i);
        mCanvas.drawCircle((float) mPoint.x, (float) mPoint.y, (float) radius, bnPaint);
    }

    private void drawShowRect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawRect(mShowRect, paint);
    }

    private void drawTouchCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(-1);
        paint.setStrokeWidth(5.0f);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mTouchPoint.x, mTouchPoint.y, 10.0f, paint);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mTouchPoint.x, mTouchPoint.y, 10.0f, paint);
    }

    private void initPaint() {
        sgPaint = new Paint();
        sgPaint.setAntiAlias(true);
        sgPaint.setStyle(Paint.Style.FILL);
        Shader sweepGradient = new SweepGradient((float) mPoint.x, (float) mPoint.y, colors, null);
        Shader radialGradient = new RadialGradient((float) mPoint.x, (float) mPoint.y, (float) mPoint.x, -1, ViewCompat.MEASURED_SIZE_MASK, Shader.TileMode.CLAMP);
        bnShader = new LinearGradient((float) mBrightnessRect.left, (float) mBrightnessRect.top, (float) mBrightnessRect.left, (float) mBrightnessRect.bottom, -1, ViewCompat.MEASURED_STATE_MASK, Shader.TileMode.MIRROR);
        sgPaint.setShader(sweepGradient);
        rgPaint = new Paint(sgPaint);
        rgPaint.setShader(radialGradient);
        rgPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        bnPaint = new Paint(rgPaint);
        bnPaint.setShader(null);
    }

    private void initViewSize(int w, int h) {
        mShowRect = new Rect(30, 10, w - 30, 100);
        mBrightnessRect = new Rect();
        mBrightnessRect.right = mShowRect.right;
        mBrightnessRect.top = 0;
        mBrightnessRect.left = mBrightnessRect.right - 100;
        int diameter = Math.min((mShowRect.width() - mBrightnessRect.width()) - 100, h);
        radius = diameter / 2;
        mBrightnessRect.bottom = mBrightnessRect.top + diameter;
        colorBitmap = Bitmap.createBitmap(w, mBrightnessRect.height(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(colorBitmap);
        mPoint = new Point(radius + mShowRect.left, radius);
        mTouchPoint = new PointF();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawShowRect(canvas);
        canvas.drawBitmap(colorBitmap, 0.0f, (float) (mShowRect.bottom + showRectSelectorSpace), sgPaint);
        drawTouchCircle(canvas);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initViewSize(w, h);
        initPaint();
        drawColorSelectorBitmap();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = ((int) event.getY()) - (showRectSelectorSpace + mShowRect.bottom);
        if (computeBothPointSpace((float) x, (float) y, (float) mPoint.x, (float) mPoint.y) <= ((float) radius)) {
            mTouchPoint.x = event.getX();
            mTouchPoint.y = event.getY();
            mSelectPoint.x = x;
            mSelectPoint.y = y;
            computeColor(colorBitmap.getPixel(mSelectPoint.x, mSelectPoint.y));
            return true;
        } else if (!mBrightnessRect.contains(x, y)) {
            return true;
        } else {
            mBrightnessLine = y;
            brightness = 255 - ((mBrightnessRect.bottom - y) / (mBrightnessRect.height() / MotionEventCompat.ACTION_MASK));
            System.out.println("brightness:" + brightness);
            drawColorSelectorBitmap();
            computeColor(colorBitmap.getPixel(mSelectPoint.x, mSelectPoint.y));
            return true;
        }
    }

    public void setmListener(IColorSelectorListener mListener) {
        this.mListener = mListener;
    }


    public interface IColorSelectorListener {
        void onGetColorListener(int red, int green, int blue, int color);
    }
}
