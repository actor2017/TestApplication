package com.actor.testapplication.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

/**
 * description: PopupWindow基类
 *
 * @author : 李大发
 * date       : 2020/5/31 on 12:13
 * @version 1.0
 * @deprecated Android7.0中PopupWindow 有点问题, api懒得适配, 还是用其它框架吧
 */
@Deprecated
public class BasePopupWindow extends PopupWindow {

    public BasePopupWindow(Context context) {
        super(context);
        init();
    }

    public BasePopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public BasePopupWindow() {
        init();
    }

    public BasePopupWindow(View contentView) {
        super(contentView);
        init();
    }

    public BasePopupWindow(int width, int height) {
        super(width, height);
        init();
    }

    public BasePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        init();
    }

    /**
     * @param contentView PopupWindow 显示内容
     * @param width 固定宽度 or ViewGroup.LayoutParams.MATCH_PARENT/WRAP_CONTENT
     * @param height 高
     * @param focusable
     */
    public BasePopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        init();
    }

    //初始化
    protected void init() {
        //设置背景,全透明; 只有设置了背景, 点击返回键和窗口外侧, 弹窗才能够消失
        setBackgroundDrawable(new ColorDrawable(/*Color.TRANSPARENT*/));
    }

    /**
     * @deprecated 设置 PopupWindow 内容的背景(不是设置外面空白地方的背景, 所以感觉没什么用)
     */
    @Deprecated
    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
    }

    public void setLayout(Context context, @LayoutRes int layout) {
        View view = LayoutInflater.from(context).inflate(layout, null, false);
        setContentView(view);
        setOutsideTouchable(true);
        setFocusable(true);//如果不设置true, 点击外部的时候不会消失
    }

    /**
     * @param animationStyle 设置动画, R.style.xxx
     */
    @Override
    public void setAnimationStyle(int animationStyle) {
        super.setAnimationStyle(animationStyle);
    }

    /**
     * @param anchor 在 anchor 的下方显示, x&y偏移 = 0
     */
    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }

    /**
     * @param anchor 在 anchor 的下方显示
     * @param xoff PopupWindow 左上角 x轴偏移量, 可为负数
     * @param yoff PopupWindow 左上角 y轴偏移量, 可为负数
     */
    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
    }

    /**
     * @param anchor 在 anchor 的下方显示
     * @param xoff x轴偏移量
     * @param yoff y轴偏移量
     * @param gravity PopupWindow 相对 anchor 位置
     *                @see android.view.Gravity#TOP
     *                @see android.view.Gravity#START 开始位置
     *                @see android.view.Gravity#END 结束位置
     */
    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
//        PopupWindowCompat.showAsDropDown(this, anchor, xoff, yoff, gravity);
    }

    /**
     * 显示在某个位置
     * @param parent 任意view, 用于获取WindowToken
     * @param gravity 重心位置 相对于整个屏幕的重心(不是相对于view)
     *                @see android.view.Gravity#CENTER 屏幕中心
     * @param x 基于重心位置的x偏移
     * @param y 基于重心位置的y偏移
     */
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
    }

    /**
     * @return 是否已经显示
     */
    @Override
    public boolean isShowing() {
        return super.isShowing();
    }
}
