package com.actor.testapplication.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * description: TODO: 各种状态selector, ShowPartImageView
 * company    :
 *
 * @author : ldf
 * date       : 2024/3/1 on 18
 * @version 1.0
 */
public class BaseImageView extends AppCompatImageView {

    public BaseImageView(@NonNull Context context) {
        super(context);
    }

    public BaseImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
