package com.actor.testapplication.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actor.testapplication.R;

import butterknife.BindView;


/**
 * Description: Test测试页面
 * Date       : 2019/12/30 on 11:38
 */
public class TestActivity extends BaseActivity {


    @BindView(R.id.tv_result)//显示结果
    TextView tvResult;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.btn)
    Button   btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
//        ButterKnife.bind(this);
        setTitle("Test测试页面");

//        setContentView(new PlasmaView(this));

        Display display = getWindowManager().getDefaultDisplay();
        setContentView(new PlasmaView(this, display.getWidth(), display.getHeight()));
    }

//    @OnClick(R.id.btn)
//    public void onViewClicked() {
//    }
}

//class PlasmaView extends View implements View.OnTouchListener{
//    private Bitmap mBitmap;
//    long time;
//    long fps;
//    public PlasmaView(Context context) {
//        super(context);
//        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(),R.drawable.logo);
//        mBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);
//        AnimRender.setBitmap(bmp);
//        this.setOnTouchListener(this);
//    }
//    @Override
//    protected void onDraw(Canvas canvas) {
//        long ct = System.currentTimeMillis();
//        if(ct - time > 1000){
//            time = ct;
//            fps = 0;
//        }
//        fps++;
//
//        AnimRender.render(mBitmap);
//        canvas.drawBitmap(mBitmap, 0, 0, null);
//        postInvalidate();
//    }
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        AnimRender.drop((int)event.getX(), (int)event.getY(), 500);
//        return false;
//    }
//}

class PlasmaView extends View {
    private Bitmap mBitmap;
    private long mStartTime;

    static {
        System.loadLibrary("plasma");
    }

    /* implementend by libplasma.so */
    private static native void renderPlasma(Bitmap  bitmap, long time_ms);

    public PlasmaView(Context context, int width, int height) {
        super(context);
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        mStartTime = System.currentTimeMillis();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(0xFFCCCCCC);
        renderPlasma(mBitmap, System.currentTimeMillis() - mStartTime);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        // force a redraw, with a different time-based pattern.
        invalidate();
    }
}

class AnimRender {
    public static native void setBitmap(Bitmap src);
    public static native void render(Bitmap dst);
    public static native void drop(int x, int y, int height);

    static {
        System.loadLibrary("plasma");
    }
}
