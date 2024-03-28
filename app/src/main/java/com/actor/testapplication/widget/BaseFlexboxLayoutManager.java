package com.actor.testapplication.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.IntRange;

import com.google.android.flexbox.FlexLine;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

/**
 * description: 描述
 * company    :
 *
 * @author : ldf
 * date       : 2023/8/29 on 15
 */
// TODO: 2024/3/28 自定义行数
public class BaseFlexboxLayoutManager extends FlexboxLayoutManager {

    protected int maxLines = Integer.MAX_VALUE;

    public BaseFlexboxLayoutManager(Context context) {
        super(context);
    }

    public BaseFlexboxLayoutManager(Context context, int flexDirection) {
        super(context, flexDirection);
    }

    public BaseFlexboxLayoutManager(Context context, int flexDirection, int flexWrap) {
        super(context, flexDirection, flexWrap);
    }

    public BaseFlexboxLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int getMaxLine() {
        return super.getMaxLine();
    }

    public void setMaxLines(@IntRange(from = 0) int maxLines) {
        if (maxLines >= 0) this.maxLines = maxLines;
    }

    /**
     * 这里限制了最大行数，多出部分被以 subList 方式截掉
     */
    @Override
    public List<FlexLine> getFlexLinesInternal() {
        List<FlexLine> flexLines = super.getFlexLinesInternal();
        int size = flexLines.size();
        if (maxLines > 0 && size > maxLines) {
            flexLines.subList(maxLines, size).clear();
        }
        return flexLines;
    }
}