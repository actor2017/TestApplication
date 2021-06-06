package com.actor.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.actor.testapplication.R;
import com.actor.cpptest.CCallJava;
import com.actor.cpptest.JavaCallC;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: C & Java 互调
 * date       : 2020/12/10 on 15:50
 *
 * 13.29_JNI规范(jni.h, 王超老师)
 * Java的头文件所在目录: C:\Program Files\Java\jdk1.8.0_45\include\jni.h
 * 安卓的头文件所在目录: D:\AndroidStudioSDK\ndk\21.0.6113669\platforms\android-29\arch-arm\usr\include\jni.h (ndk在Android SDK里面)
 *              实际在: D:\AndroidStudioSDK\ndk\21.0.6113669\sysroot\usr\include\jni.h
 *      typedef unsigned char jboolean;//java类型的boolean
 *      typedef long long jlong;       //java类型的lang占8个字节
 *      typedef void* jobject;         //java类型的对象
 *      typedef const struct JNINativeInterface* JNIEnv;//一级结构体指针, android的jni.h是这样写
 *      typedef const struct JNIInvokeInterface* JavaVM;//一级结构体指针, android的jni.h是这样写
 *      ...
 *
 * day19_02_JNI[java调c]
 * 14.02_节目预告: 见: day02_JNI.md
 * 14.03_交叉编译
 * 14.04_交叉编译工具链
 * 14.05_ndk目录结构
 * 14.06_C代码实现Java中的本地方法
 * 14.07_JNI编写步骤
 * 14.08_Androidmk文件介绍
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
        setTitle("Java&C互调");

        // TODO: 2021/5/10 有空看视频再学习.

        //Java调C
        btn.setText(JavaCallC.stringFromJNI());
        btn.append("\n1+1=" + JavaCallC.add(1, 1));
        JavaCallC.add10(arr);//地址传递...
        btn.append("\n1-5数组每项+10=" + Arrays.toString(arr));

        //C调Java
        CCallJava.callVoid();
        CCallJava.staticMethodCalledVoid();//C调用Java的静态方法
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