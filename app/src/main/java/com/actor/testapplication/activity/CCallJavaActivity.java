package com.actor.testapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actor.cpptest.CCallJava;
import com.actor.cpptest.JavaCallC;
import com.actor.testapplication.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: C & Java 互调
 * 谷歌示例: https://github.com/android/ndk-samples  Android NDK samples with Android Studio
 * date       : 2020/12/10 on 15:50
 *
 * 13.29_JNI规范(jni.h, 王超老师)
 * Java的头文件所在目录: C:\Program Files\Java\jdk1.8.0_45\include\jni.h
 * 安卓的头文件所在目录: D:\AndroidStudioSDK\ndk\21.0.6113669\platforms\android-29\arch-arm\  usr\include\jni.h (ndk在Android SDK里面)
 *              实际在: D:\AndroidStudioSDK\ndk\21.0.6113669\sysroot\  usr\include\jni.h (\ u会报错: https://www.jianshu.com/p/b13f99ac0988)
 *      typedef unsigned char jboolean;//java类型的boolean
 *      typedef long long jlong;       //java类型的lang占8个字节
 *      typedef void* jobject;         //java类型的对象
 *      typedef const struct JNINativeInterface* JNIEnv;//一级结构体指针, android的jni.h是这样写
 *      typedef const struct JNIInvokeInterface* JavaVM;//一级结构体指针, android的jni.h是这样写
 *      ...
 *   步骤:
 *      1.SDK Manager -> SDK Tools -> 勾选: CMake, LLDB, NDK -> 下载
 *      2.File -> Project Structure -> Android NDK location: 我的目前是D:\AndroidStudioSDK\ndk\21.0.6113669
 *      3.创建项目 -> 勾选"Include C++ support" ->Next -> 选择C++ Standard, Exception Support, Runtime Type Information Support
 *          3.1.会在模块的gradle文件中生成 externalNativeBuild{}, externalNativeBuild{}
 *          3.2.会在模块中生成CMakeLists.txt 文件, main文件夹下生成cpp文件夹(或者手动创建cpp文件夹: 右击模块->New->Folder->JNI Folder)
 *      4.写一个native方法, alt + enter, 会自动在.c文件生成函数. 或者直接在.c文件中敲类名+方法, 有提示的.
 *      5.Build -> Rebuild Project, 编译.so, 在模块 ->build -> intermediates -> cmake文件夹下
 *      6.具体见 CMakeLists.txt, cpp/build.gradle
 *
 * day19_02_JNI[java调c]
 * 14.02_节目预告: 见: ★★★★★ day02_JNI.md ★★★★★
 * 14.03_交叉编译
 * 14.04_交叉编译工具链
 * 14.05_ndk目录结构
 * 14.06_C代码实现Java中的本地方法
 * 14.07_JNI编写步骤
 * 14.08_Android.mk文件介绍
 *      ★★★注意: 如果使用 CMakeLists.txt, 就不用再写 Android.mk 和 Application.mk
 * 14.09_javah命令(用于生成.h头文件, 可将生产的方法copy到相应的.c文件)
 * 14.10_添加x86支持
 * 14.11_常见的错误
 */
public class CCallJavaActivity extends BaseActivity {

    @BindView(R.id.tv_result)
    TextView tvResult;

    private final int[] arr = {1, 2, 3, 4, 5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_call_java);
        ButterKnife.bind(this);
        setTitle("Java&C互调");

        //C调Java
        CCallJava.callVoid();
        //C调用Java的静态方法
        CCallJava.staticMethodCalledVoid();
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1://Java调C, 普通方法. ★★★静态方法参2是jclass, 非静态是jobject★★★
                tvResult.setText(JavaCallC.getInstance().stringFromJNI());
                break;
            case R.id.btn_2://Java调C, 静态方法带下划线: _add(1, 1)
                tvResult.setText(getStringFormat("1 + 1 = %d", JavaCallC._add(1, 1)));
                break;
            case R.id.btn_3://Java调C, 静态方法, 数组每位+10: arrayAdd10(arr)
                int pos0 = arr[0];
                int posl = arr[arr.length - 1];
                JavaCallC.arrayAdd10(arr);//地址传递...
                tvResult.setText(getStringFormat("%d-%d数组每项 + 10 = %s", pos0, posl, Arrays.toString(arr)));
                break;
            default:
                break;
        }
    }
}