package com.actor.testapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.internal.view.SupportMenu;

/**
 * description: 画的折线和三角形
 * company    :
 *
 * @author : ldf
 * date       : 2021/11/16 on 10
 * @version 1.0
 */
// TODO: 2021/11/16 添加注释 
public class LineAndTriangleView extends View {

    private Matrix           bMatrix;
    private CornerPathEffect cornerPathEffect;
    public  PathEffect       effect;
    private Paint            mPaint;
    private Matrix           matrix;
    private PointF           mid;
    private int              mode;
    private float            oldDist;
    private Path             path;
    private float            scale;
    private float            tanStartX;
    private float            tanStartY;
    private       float[]    value;
    private final Paint      paint = new Paint();
    private final Path       path2 = new Path();

    public LineAndTriangleView(Context context) {
        super(context);
        init(context, null);
    }

    public LineAndTriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LineAndTriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        this.effect = new DashPathEffect(new float[]{5.0f, 5.0f, 5.0f, 5.0f}, 1.0f);
        this.cornerPathEffect = new CornerPathEffect(50.0f);
        this.scale = 1.0f;
        this.mid = new PointF();
        this.oldDist = 1.0f;
        this.matrix = new Matrix();
        this.bMatrix = new Matrix();
        this.tanStartX = 0.0f;
        this.tanStartY = 0.0f;
        this.value = new float[9];
        this.mode = 0;
        this.path = new Path();
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.mPaint.setStrokeWidth(10.0f);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setPathEffect(this.cornerPathEffect);
    }

    private void midPoint(PointF point, MotionEvent event) {
        point.set((event.getX(0) + event.getX(1)) / 2.0f, (event.getY(0) + event.getY(1)) / 2.0f);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.concat(this.matrix);
        this.mPaint.setPathEffect(this.cornerPathEffect);
        canvas.drawPath(this.path, this.mPaint);
        canvas.translate(0.0f, 200.0f);
        this.mPaint.setPathEffect(null);
        canvas.drawPath(this.path, this.mPaint);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(SupportMenu.CATEGORY_MASK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0f);

        path2.reset();
        path2.moveTo(90.0f, 330.0f);
        path2.lineTo(150.0f, 330.0f);
        path2.lineTo(120.0f, 270.0f);
        path2.close();
        canvas.drawPath(path2, paint);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.path.moveTo(0.0f, 100.0f);
        for (int i = 1; i < 50; i++) {
            this.path.lineTo((float) (i * 30), ((float) Math.random()) * 60.0f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 0:
                this.mode = 2;
                this.tanStartX = event.getX();
                this.tanStartY = event.getY();
                return true;
            case 1:
                this.bMatrix.set(this.matrix);
                return true;
            case 2:
                this.matrix.getValues(this.value);
                if (this.mode == 2) {
                    this.matrix.set(this.bMatrix);
                    this.matrix.postTranslate(event.getX() - this.tanStartX, event.getY() - this.tanStartY);
                    invalidate();
                    return true;
                } else if (this.mode != 1) {
                    return true;
                } else {
                    float newDist = spacing(event);
                    if (newDist <= 50.0f) {
                        return true;
                    }
                    this.matrix.set(this.bMatrix);
                    this.scale = newDist / this.oldDist;
                    this.matrix.postScale(this.scale, this.scale, this.mid.x, this.mid.y);
                    this.matrix.getValues(this.value);
                    invalidate();
                    return true;
                }
            case 5:
                this.mode = 1;
                this.oldDist = spacing(event);
                if (this.oldDist <= 50.0f) {
                    return true;
                }
                midPoint(this.mid, event);
                return true;
            case 6:
                this.mode = 0;
                this.bMatrix.set(this.matrix);
                return true;
            default:
                return true;
        }
    }
}
