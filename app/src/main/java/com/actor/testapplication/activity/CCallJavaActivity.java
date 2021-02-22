package com.actor.testapplication.activity;

import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.actor.testapplication.R;
import com.actor.testapplication.utils.CCallJava;
import com.actor.testapplication.utils.JavaCallC;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: C & Java 互调
 * @author    : 李大发
 * date       : 2020/12/10 on 15:50
 */
public class CCallJavaActivity extends BaseActivity {

    @BindView(R.id.btn)
    Button btn;

    private int[]             arr = new int[]{1, 2, 3, 4, 5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_call_java);
        ButterKnife.bind(this);

        //Java调C
        btn.setText(JavaCallC.stringFromJNI());
        btn.append("\n1+1=" + JavaCallC.add(1, 1));
        JavaCallC.add10(arr);//地址传递...
        btn.append("\n1-5数组每项+10=" + Arrays.toString(arr));

        //C调Java
        CCallJava.callVoid();
        CCallJava.staticMethodCalledVoid();//C调用Java的静态方法

        Surface surface;//extends Object implements Parcelable
        SurfaceView surfaceView = null;//extends View
        GLSurfaceView glSurfaceView;//extends SurfaceView
        VideoView videoView;//extends SurfaceView

        SurfaceTexture surfaceTexture;//extends Object
        TextureView textureView = null;//extends View
    }

    @OnClick({R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                break;
            default:
                break;
        }
    }
}