package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import static android.support.v7.widget.RoundRectDrawableWithShadow.calculateHorizontalPadding;
import static android.support.v7.widget.RoundRectDrawableWithShadow.calculateVerticalPadding;

/**
 * Description: {@link android.support.v7.widget.RoundRectDrawable}
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/6/19 on 00:29
 */
@RequiresApi(21)
public class MyRoundRectDrawable extends Drawable {
    private       float   mRadius;
    private final Paint   mPaint;
    private final RectF   mBoundsF;
    private final Rect    mBoundsI;
    private       float   mPadding;
    private       boolean mInsetForPadding = false;
    private       boolean mInsetForRadius = true;

    private ColorStateList mBackground;
    private PorterDuffColorFilter mTintFilter;
    private ColorStateList mTint;
    private PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;

    public MyRoundRectDrawable(ColorStateList backgroundColor, float radius) {
        mRadius = radius;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        setBackground(backgroundColor);

        mBoundsF = new RectF();
        mBoundsI = new Rect();
    }

    private void setBackground(ColorStateList color) {
        mBackground = (color == null) ?  ColorStateList.valueOf(Color.TRANSPARENT) : color;
        mPaint.setColor(mBackground.getColorForState(getState(), mBackground.getDefaultColor()));
    }

    void setPadding(float padding, boolean insetForPadding, boolean insetForRadius) {
        if (padding == mPadding && mInsetForPadding == insetForPadding
                && mInsetForRadius == insetForRadius) {
            return;
        }
        mPadding = padding;
        mInsetForPadding = insetForPadding;
        mInsetForRadius = insetForRadius;
        updateBounds(null);
        invalidateSelf();
    }

    float getPadding() {
        return mPadding;
    }

    @Override
    public void draw(Canvas canvas) {
        final Paint paint = mPaint;

        final boolean clearColorFilter;
        if (mTintFilter != null && paint.getColorFilter() == null) {
            paint.setColorFilter(mTintFilter);
            clearColorFilter = true;
        } else {
            clearColorFilter = false;
        }

        //edited 修改过
        canvas.drawRoundRect(mBoundsF, mRadius, mRadius, paint);

//        float right = mBoundsF.right;
//        Path path = new Path();
//        path.addArc(new RectF(0, 0, mRadius * 2, mRadius * 2), 180, 90);
//        path.arcTo(new RectF(right - mRadius, 0, right, mRadius * 2), 270, 90);
//        path.lineTo(right, mBoundsF.bottom);
//        path.lineTo(0, mBoundsF.bottom);
//        path.close();
//        canvas.clipPath(path);
//        canvas.drawPath(path, paint);


        if (clearColorFilter) {
            paint.setColorFilter(null);
        }
    }

    private void updateBounds(Rect bounds) {
        if (bounds == null) {
            bounds = getBounds();
        }
        mBoundsF.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
        mBoundsI.set(bounds);
        if (mInsetForPadding) {
            float vInset = calculateVerticalPadding(mPadding, mRadius, mInsetForRadius);
            float hInset = calculateHorizontalPadding(mPadding, mRadius, mInsetForRadius);
            mBoundsI.inset((int) Math.ceil(hInset), (int) Math.ceil(vInset));
            // to make sure they have same bounds.
            mBoundsF.set(mBoundsI);
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        updateBounds(bounds);
    }

    @Override
    public void getOutline(Outline outline) {
        outline.setRoundRect(mBoundsI, mRadius);
    }

    void setRadius(float radius) {
        if (radius == mRadius) {
            return;
        }
        mRadius = radius;
        updateBounds(null);
        invalidateSelf();
    }

    //edited 增加方法 https://www.cnblogs.com/woider/p/5122911.html
    void setRadius(Rect radius) {
//        if (radius == mRadius) {
//            return;
//        }
//        mRadius = radius;
//        updateBounds(null);
//        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setColor(@Nullable ColorStateList color) {
        setBackground(color);
        invalidateSelf();
    }

    public ColorStateList getColor() {
        return mBackground;
    }

    @Override
    public void setTintList(ColorStateList tint) {
        mTint = tint;
        mTintFilter = createTintFilter(mTint, mTintMode);
        invalidateSelf();
    }

    @Override
    public void setTintMode(PorterDuff.Mode tintMode) {
        mTintMode = tintMode;
        mTintFilter = createTintFilter(mTint, mTintMode);
        invalidateSelf();
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        final int newColor = mBackground.getColorForState(stateSet, mBackground.getDefaultColor());
        final boolean colorChanged = newColor != mPaint.getColor();
        if (colorChanged) {
            mPaint.setColor(newColor);
        }
        if (mTint != null && mTintMode != null) {
            mTintFilter = createTintFilter(mTint, mTintMode);
            return true;
        }
        return colorChanged;
    }

    @Override
    public boolean isStateful() {
        return (mTint != null && mTint.isStateful())
                || (mBackground != null && mBackground.isStateful()) || super.isStateful();
    }

    /**
     * Ensures the tint filter is consistent with the current tint color and
     * mode.
     */
    private PorterDuffColorFilter createTintFilter(ColorStateList tint, PorterDuff.Mode tintMode) {
        if (tint == null || tintMode == null) {
            return null;
        }
        final int color = tint.getColorForState(getState(), Color.TRANSPARENT);
        return new PorterDuffColorFilter(color, tintMode);
    }
}
