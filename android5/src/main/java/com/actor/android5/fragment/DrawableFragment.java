package com.actor.android5.fragment;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actor.android5.R;
import com.actor.base.BaseFragment;

/**
 * 颜色和图片
 */
public class DrawableFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawable, container, false);
        View imageview = view.findViewById(R.id.im);
        final TextView v = (TextView) view.findViewById(R.id.v);
        final TextView vd = (TextView) view.findViewById(R.id.vd);
        final TextView vl = (TextView) view.findViewById(R.id.vl);
        final TextView m = (TextView) view.findViewById(R.id.m);
        final TextView md = (TextView) view.findViewById(R.id.md);
        final TextView ml = (TextView) view.findViewById(R.id.ml);

        Drawable drawable = imageview.getBackground();
        BitmapDrawable bd = (BitmapDrawable) drawable;

        //通过一个Bitmap对象来生成一个对应的Palette对象
        //异步不会阻塞主线程
        Palette.generateAsync(bd.getBitmap(), new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                // 有活力的颜色
                v.setBackgroundColor(palette.getVibrantColor(Color.BLACK));
                // 有活力的暗色
                vd.setBackgroundColor(palette.getDarkVibrantColor(Color.BLACK));
                // 有活力的亮色
                vl.setBackgroundColor(palette.getLightVibrantColor(Color.BLACK));
                // 柔和的颜色
                m.setBackgroundColor(palette.getMutedColor(Color.BLACK));
                // 柔和的暗色
                md.setBackgroundColor(palette.getDarkMutedColor(Color.BLACK));
                // 柔和的亮色
                ml.setBackgroundColor(palette.getLightMutedColor(Color.BLACK));
            }
        });
        return view;
    }

    public String getUrl() {
        return "file:///android_asset/drawable.html";
    }
}
