package com.actor.testapplication.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.actor.cpptest.widget.PlasmaView;

/**
 * description: jni谷歌官网等离子体示例
 * @author    : ldf
 * date       : 2021/6/10 on 16:51
 */
public class PlasmaActivity extends BaseActivity {

    static {
        System.loadLibrary("plasma");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_plasma);
        setTitle("plasma等离子体示例");

        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
//        setContentView(new PlasmaView(this, display.getWidth(), display.getHeight()));
        setContentView(new PlasmaView(this, displaySize.x, displaySize.y));
    }
}
