package com.actor.testapplication.utils.encrypt;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.Utils;

import java.util.Random;

//import android.support.annotation.ColorInt;
//import android.support.annotation.RequiresApi;

/**
 * Created by zhengping on 2017/2/28,15:37.
 * 1.dp 和 像素 的互转    dp2px(用的多)  px2dp
 * 2.获取屏幕宽度/高度getScreenWidth
 */

public class UiUtils {

    public static Context getContext() {
        return Utils.getApp();
    }

    /**
     * 获取字符串资源
     */
    public static String getString(@StringRes int resId) {
        return getContext().getResources().getString(resId);
    }

    /**
     * 获取字符串数组
     */
    public static String[] getStringArray(@ArrayRes int resId) {
        return getContext().getResources().getStringArray(resId);
    }

    /**
     * 获取Drawable
     */
    public static Drawable getDrawable(@DrawableRes int resId) {
        return getContext().getResources().getDrawable(resId);
    }

    /**
     * 获取color
     */
    public static int getColor(@ColorRes int resId) {
        return getContext().getResources().getColor(resId);
    }

    /**
     * 获取颜色列表?
     */
	public static ColorStateList getColorStateList(@ColorRes int resId) {
        return getContext().getResources().getColorStateList(resId);
    }

    /**
     * 颜色随机，也不能够太随机，应该控制在一定的范围之内   30~220
     */
    public static int getRandomColor() {
        Random random = new Random();
        int red = 30 + random.nextInt(191);
        int green = 30 + random.nextInt(191);
        int blue = 30 + random.nextInt(191);
        return Color.rgb(red, green, blue);
    }

    /**
     * 获取随机字体大小,16~25sp之间
     */
    public static int getRandomTextSize() {
        Random random = new Random();
        return 16 + random.nextInt(10);
    }

    /**
     * 获取尺寸
     */
    public static int getDimen(@DimenRes int resId) {
        return getContext().getResources().getDimensionPixelSize(resId);
    }

    /**
     * dp 转换为 像素,传入"dp",输出"像素",java代码中一般用这种
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);//四舍五入
        //   3.1   --> 3
        //  3.7   --> 3
        //3.6-->3
        //4.2-->4
    }

    /**
     * 像素 转换为 dp,传入"像素",输出"dp"
     */
    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px/density + 0.5F);
    }

    /**
     * 字体的sp 转换为 px
     */
    public static int sp2px(float sp) {
        float var2 = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int)(sp * var2 + 0.5F);
    }

    /**
     * px 转换为 sp
     */
    public static int px2sp(float px) {
        float var2 = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int)(px / var2 + 0.5F);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreemHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取控件宽度
     */
    public static int[] getViewSize(final View view){
        //我们可以对view监听视图树
        final int[] size = {0,0};
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                size[0] = view.getWidth();
                size[1] = view.getHeight();
            }
        });
        return size;
    }

    /**
     * 设置View的Margin
     */
    public static void setViewMargin(View view, int left, int top, int right, int bottom) {
        //LinearLayout.LayoutParams extends ViewGroup.MarginLayoutParams extends ViewGroup.LayoutParams
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        } else {
            /**
             *  第一种方法,强转:LinearLayout.LayoutParams(ViewGroup.LayoutParams)
             *  如果这个控件实在XML中定义的 比如Textview,验证可行
             *  如果这个控件是我们new出来的，就会发现报空指针错误(没试过.应该是返回null,不是空指针)
             */
//            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();

            /**
             * 第二种方法,new一个对象,验证可行
             */
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);//(ViewGroup.LayoutParams)
        }
    }

    /**
     * xml文件中animated-selector
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static AnimatedStateListDrawable getAnimatedSelector() {
        AnimatedStateListDrawable asld = new AnimatedStateListDrawable();
        return asld;
    }

    //干什么的?
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static AnimatedVectorDrawable getAnimatedVectorDrawable() {
        AnimatedVectorDrawable avd = new AnimatedVectorDrawable();
        return avd;
    }

    /**
     * xml文件中的animation-list
     */
    public static AnimationDrawable getAnimationList() {
        AnimationDrawable ad = new AnimationDrawable();
        return ad;
    }

    /**
     * xml文件中的bitmap
     */
    public static BitmapDrawable getBitmap() {
        BitmapDrawable bd = new BitmapDrawable();//有构造方法
        return bd;
    }

    /**
     * xml文件中的clip
     */
    public static ClipDrawable getClip(Drawable drawable, int gravity, int orientation) {
        ClipDrawable cd = new ClipDrawable(drawable, gravity, orientation);
        return cd;
    }

    /**
     * xml文件中的color
     */
    public static ColorDrawable getColor() {
        ColorDrawable cd = new ColorDrawable();//还有一个构造方法
        return cd;
    }

    /**
     * xml文件中的shape
     */
    public static GradientDrawable getShape(int radius, int color) {//@ColorInt
    GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(radius);
        shape.setColor(color);
        return shape;
    }

    /**
     * xml文件中的inset
     */
    public static InsetDrawable getInset(Drawable drawable, int inset) {
        InsetDrawable id = new InsetDrawable(drawable, inset);//还有一个构造方法
        return id;
    }

    /**
     * xml文件中的layer-list
     */
    public static LayerDrawable getLayerList(@NonNull Drawable[] layers) {
        LayerDrawable ld = new LayerDrawable(layers);
        return ld;
    }

    /**
     * xml文件中的level-list
     */
    public static LevelListDrawable getLevelList() {
        LevelListDrawable lld = new LevelListDrawable();
        return lld;
    }

    /**
     * xml文件中的ripple
     */
    public static RippleDrawable getRipple(@NonNull ColorStateList color, @Nullable Drawable content,
                                           @Nullable Drawable mask) {
        RippleDrawable rd = new RippleDrawable(color, content, mask);
        return rd;
    }

    /**
     * xml文件中的rotate
     */
    public static RotateDrawable getRotate() {
        RotateDrawable rd = new RotateDrawable();
        return rd;
    }

    /**
     * xml文件中的scale
     */
    public static ScaleDrawable getScale(Drawable drawable, int gravity, float scaleWidth, float scaleHeight) {
        ScaleDrawable sd = new ScaleDrawable(drawable, gravity, scaleWidth, scaleHeight);
        return sd;
    }

    /**
     * xml文件中的shape
     */
    public static ShapeDrawable getShape() {
        ShapeDrawable sd = new ShapeDrawable();//还有一个构造方法
        return sd;
    }

    /**
     * xml文件中selector
     */
    public static StateListDrawable getSelector(Drawable pressedDrawable, Drawable normalDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();//代表写了一个selector的标签
        //给selector增加规则
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},pressedDrawable);//增加按下的规则
        stateListDrawable.addState(new int[]{},normalDrawable);//增加默认的规则
        return stateListDrawable;
    }

    /**
     * xml文件中transition
     */
    public static TransitionDrawable getTransition(Drawable[] layers) {
        TransitionDrawable td = new TransitionDrawable(layers);
        return td;
    }

    /**
     * xml文件中vertor
     */
    public static VectorDrawable getVector() {
        VectorDrawable vd = new VectorDrawable();
        return vd;
    }
}
