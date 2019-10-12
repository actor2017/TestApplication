package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * Description: {@link CardViewApi21Impl}
 * Copyright  : Copyright (c) 2019
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019/6/19 on 00:45
 */
@RequiresApi(21)
public class MyCardViewApi21Impl implements CardViewImpl {

    @Override
    public void initialize(CardViewDelegate cardView, Context context,
                           ColorStateList backgroundColor, float radius, float elevation, float maxElevation) {
        //RoundRectDrawable用来绘制背景圆角矩形, edited 修改过
        final MyRoundRectDrawable background = new MyRoundRectDrawable(backgroundColor, radius);
        cardView.setCardBackground(background);

        View view = cardView.getCardView();
        view.setClipToOutline(true);//进行裁剪
        view.setElevation(elevation);//设置阴影大小
        setMaxElevation(cardView, maxElevation);//设置最大阴影大小
    }

    @Override
    public void setRadius(CardViewDelegate cardView, float radius) {
        getCardBackground(cardView).setRadius(radius);
    }

    //edited 增加方法
    public void setRadius(CardViewDelegate cardView, Rect radius) {
        getCardBackground(cardView).setRadius(radius);
    }

    @Override
    public void initStatic() {
    }

    @Override
    public void setMaxElevation(CardViewDelegate cardView, float maxElevation) {
        getCardBackground(cardView).setPadding(maxElevation,
                cardView.getUseCompatPadding(), cardView.getPreventCornerOverlap());
        updatePadding(cardView);
    }

    @Override
    public float getMaxElevation(CardViewDelegate cardView) {
        return getCardBackground(cardView).getPadding();
    }

    @Override
    public float getMinWidth(CardViewDelegate cardView) {
        return getRadius(cardView) * 2;
    }

    @Override
    public float getMinHeight(CardViewDelegate cardView) {
        return getRadius(cardView) * 2;
    }

    @Override
    public float getRadius(CardViewDelegate cardView) {
        return getCardBackground(cardView).getRadius();
    }

    @Override
    public void setElevation(CardViewDelegate cardView, float elevation) {
        cardView.getCardView().setElevation(elevation);
    }

    @Override
    public float getElevation(CardViewDelegate cardView) {
        return cardView.getCardView().getElevation();
    }

    @Override
    public void updatePadding(CardViewDelegate cardView) {
        if (!cardView.getUseCompatPadding()) {
            cardView.setShadowPadding(0, 0, 0, 0);
            return;
        }
        float elevation = getMaxElevation(cardView);
        final float radius = getRadius(cardView);
        int hPadding = (int) Math.ceil(RoundRectDrawableWithShadow
                .calculateHorizontalPadding(elevation, radius, cardView.getPreventCornerOverlap()));
        int vPadding = (int) Math.ceil(RoundRectDrawableWithShadow
                .calculateVerticalPadding(elevation, radius, cardView.getPreventCornerOverlap()));
        cardView.setShadowPadding(hPadding, vPadding, hPadding, vPadding);
    }

    @Override
    public void onCompatPaddingChanged(CardViewDelegate cardView) {
        setMaxElevation(cardView, getMaxElevation(cardView));
    }

    @Override
    public void onPreventCornerOverlapChanged(CardViewDelegate cardView) {
        setMaxElevation(cardView, getMaxElevation(cardView));
    }

    @Override
    public void setBackgroundColor(CardViewDelegate cardView, @Nullable ColorStateList color) {
        getCardBackground(cardView).setColor(color);
    }

    @Override
    public ColorStateList getBackgroundColor(CardViewDelegate cardView) {
        return getCardBackground(cardView).getColor();
    }

    //edited 修改过
    private MyRoundRectDrawable getCardBackground(CardViewDelegate cardView) {
        return ((MyRoundRectDrawable) cardView.getCardBackground());
    }
}
