package com.actor.testapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.actor.myandroidframework.utils.LogUtils;

import java.util.Arrays;

// TODO: 2020/6/2 待完善.
public class BaseNestedScrollView extends NestedScrollView {

    /**
     * 该控件滑动的高度，高于这个高度后交给子滑动控件
     */
    int mParentScrollHeight;
    int mScrollY;

    public BaseNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public BaseNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyScrollHeight(int scrollLength) {
        this.mParentScrollHeight = scrollLength;
    }

    /**
     * 子控件告诉父控件 开始滑动了
     *
     * @param target
     * @param dx
     * @param dy
     * @param consumed 如果有就返回true
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
        if (mScrollY < mParentScrollHeight) {
            //告诉child我消费了多少
            consumed[0] = dx;
            consumed[1] = dy;
            scrollBy(0, dy);
        }
        LogUtils.formatError("target=%s, dx=%d, dy=%d, consumed=%s", target, dx, dy, Arrays.toString(consumed));
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtils.formatError("l=%d, t=%d, oldl=%d, oldt=%d", l, t, oldl, oldt);
        mScrollY = t;
    }
}
