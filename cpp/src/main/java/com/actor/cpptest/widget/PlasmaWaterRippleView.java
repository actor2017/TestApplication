/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.actor.cpptest.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * description: plasma等离子做的水波纹
 *              http://bbs.mobiletrain.org/thread-61947-1-1.html
 *              https://blog.51cto.com/mzh3344258/808842
 *              https://blog.csdn.net/miema/article/details/84055888
 *  在Activity中使用: setContentView(new PlasmaWaterRippleView(this));
 *
 * @author : ldf
 * date       : 2021/6/9 on 22
 * @version 1.0
 */
public class PlasmaWaterRippleView extends View implements View.OnTouchListener{
    private Bitmap mBitmap;
    long time;
    long fps;
    public PlasmaWaterRippleView(Context context) {
        super(context);
        // TODO: 2021/6/10 未找到对应的.c文件, 所以驴子水波纹实现不了!
//        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.lvzi);
//        mBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);
//        AnimRender.setBitmap(bmp);
//        this.setOnTouchListener(this);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        long ct = System.currentTimeMillis();
        if(ct - time > 1000){
            time = ct;
            fps = 0;
        }
        fps++;

        AnimRender.render(mBitmap);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        postInvalidate();
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        AnimRender.drop((int)event.getX(), (int)event.getY(), 500);
        return false;
    }
}

// TODO: 2021/6/10 未找到对应的.c文件, 所以驴子水波纹实现不了!
class AnimRender {
    public static native void setBitmap(Bitmap src);
    public static native void render(Bitmap dst);
    public static native void drop(int x, int y, int height);

    static {
        System.loadLibrary("plasma");
    }
}