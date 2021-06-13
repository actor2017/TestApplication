package com.actor.testapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.ContentLoadingProgressBar;

import com.actor.cpptest.CCallJava;
import com.actor.cpptest.ConstUtils;
import com.actor.cpptest.GuoLuPress;
import com.actor.cpptest.JavaCallC;
import com.actor.testapplication.R;
import com.actor.testapplication.widget.VerticalProgressBar;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: C & Java 互调
 * 谷歌示例: https://github.com/android/ndk-samples  Android NDK samples with Android Studio
 *          https://gitee.com/gsdh/ndk-samples/blob/master/bitmap-plasma/app/build.gradle
 *
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
 * 14.07_JNI编写步骤, 例: https://www.jianshu.com/p/ca60886e0595
 *  如果使用Android.mk文件这种编译.so
 *      如果ndk-build.cmd设置了环境变量, 在Android.mk所在目录敲命令: ndk-build
 *      如果没有设置环境变量, 要在Android.mk所在路径敲命令: ndk-build -C F:\AndroidProjects\jniProject\ndk\jni
 * 14.08_Android.mk文件介绍
 *      ★★★注意: 如果使用 CMakeLists.txt, 就不用再写 Android.mk 和 Application.mk
 * 14.09_javah命令(用于生成.h头文件, 可将生产的方法copy到相应的.c文件)
 * 14.10_添加x86支持
 * 14.11_常见的错误
 * 14.12_添加本地支持
 * 14.13_传递int类型的数据
 * 14.14_传递数组
 * 14.15_学习美图秀秀
 * 14.16_独孤秀秀
 * 14.17_C代码中输出日志
 *
 * 15.03_C回调Java
 * 15.04_锅炉压力原理
 * 15.05_获取锅炉压力值
 * 15.06_锅炉压力v1.0
 * 15.07_锅炉压力v2.0
 * 15.08_自定义控件
 * 15.09_锅炉压力v3.0
 * 15.10_锅炉压力v4.0需求
 * 15.11_cpp, 见: com_actor_cpptest_ConstUtils.h
 * 15.12_jni资料
 *
 *
 * 获取方法签名: 在: \模块\build\intermediates\javac\debug\classes 目录下输入: javap -s 全类名
 *              ()V                                     //括号内是参数类型, V: 返回类型是void
 *              ()Landroid/content/pm/PackageManager;   //无参, 返回PackageManager
 *              (I)V                                    //参数int
 *              (Landroid/os/Bundle;)V                  //Activity的onCreate方法
 *              (Ljava/lang/String;)V                   //参数String, 返回void
 *              (Ljava/lang/String;)Ljava/lang/String;  //参数String, 返回String
 *              (Landroid/content/Context;Z)V           //参数String, boolean, 返回String
 *              (Landroid/content/Context;ZI)V          //参数String, boolean, int, 返回String
 *              (Landroid/content/Context;ZILjava/lang/String;)V    //参数String, boolean, int, String, 返回String
 */
public class CCallJavaActivity extends BaseActivity {

    @BindView(R.id.tv_result)//计算结果
    TextView                  tvResult;
    @BindView(R.id.tv_press)//压力值
    TextView                  tvPress;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.vertical_progress_bar)
    VerticalProgressBar       verticalProgressBar;
    @BindView(R.id.iv)
    ImageView                 iv;

    private final int[] arr = {1, 2, 3, 4, 5};
    private final int num = 10;
    //锅炉压力监测回调
    private GuoLuPress.OnPressListener listener = new GuoLuPress.OnPressListener() {
        @Override
        public void onPress(int progress) {
//            int progress = randValue % 101;//0-100
            tvPress.setText(getStringFormat("压力: %d", progress));
            progressBar.setProgress(progress);
            verticalProgressBar.setProgress(progress);
        }
    };

//    private Bitmap bitmapLogo;
//    private int[] pixels;
//    private int width, height;

//    private final JNI jni = new JNI();

//    static {
//        //孤独秀libmtimage-jni.so
//        System.loadLibrary("mtimage-jni");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_call_java);
        ButterKnife.bind(this);
        setTitle("Java&C互调");
        progressBar.setMax(100);
        String ip = ConstUtils.getString(ConstUtils.IP);
        String port = ConstUtils.getString(ConstUtils.PORT);
        toast(getStringFormat("ip&port = %s:%s", ip, port));

//        bitmapLogo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
//        width = bitmapLogo.getWidth();
//        height = bitmapLogo.getHeight();
//        pixels = new int[width * height];
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_start_monitor,
            R.id.btn_stop_monitor, R.id.btn_dgx, R.id.btn_plasma, R.id.btn_water_ripple})
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
                JavaCallC.arrayAdd(arr, num);//地址传递...
                tvResult.setText(getStringFormat("%d-%d数组每项 + %d = %s", pos0, posl, num, Arrays.toString(arr)));
                break;
            case R.id.btn_4://C回调Java, 普通方法
                CCallJava.getInstance().callByC();
                // FIXME: 2021/6/8 c调用java方法崩溃!!!
//                CCallJava.getInstance().callByC(getClass(), "calledByC", "(Ljava/lang/String;)V");
                toast("c回调java普通方法成功");
                break;
            case R.id.btn_5://C回调Java, 静态方法
                CCallJava.staticMethodCalledVoid();
                // FIXME: 2021/6/8 下方方法崩溃
//                CCallJava.staticMethodCalledVoid(getClass(), "calledByC", "(Ljava/lang/String;)V");
                toast("c回调java静态方法成功");
                break;
            case R.id.btn_start_monitor://开始监测锅炉压力
                GuoLuPress.startMonitor(listener);
                break;
            case R.id.btn_stop_monitor://停止监测
                GuoLuPress.stopMonitor();
                break;
            case R.id.btn_dgx://独孤秀
                /**
                 * https://blog.csdn.net/xx326664162/article/details/52240795
                 * getPixels()函数把一张图片，从指定的偏移位置（offset），
                 * 指定的位置（x,y）截取指定的宽高（width,height ），
                 * 把所得图像的每个像素颜色转为int值，存入pixels。
                 *
                 * 将Bitmap写入int[]中, 如果将写入结果图片记作: bmap
                 *
                 * @ColorInt int[] pixels 存放图片所有像素的颜色
                 * int offset             写入像素的第一个索引, bmap[0,width*height]的第几个像素开始写入
                 * int stride             指定在行之间跳过的像素的数目。图片是二维的，存入一个一维数组中，那么就需要这个参数来指定多少个像素换一行。
                 *                        如果一行的像素个数不够，用0（透明）来填充到 stride个。可以为负。
                 * int x                  x轴的起始点坐标
                 * int y                  y轴的起始点坐标
                 * int width              从每行读取的像素数
                 * int height
                 */
                //  int[] pixels, offset, stride, x, y, width, height
//                bitmapLogo.getPixels(pixels, 0, width, 0, 0, width, height);
//                jni.StyleLomoHDR(pixels, width, height);
//                Glide.with(this).load(pixels).into(iv);
                toast("我的荣耀v30不适配这个图片处理.so文件...");
                break;
            case R.id.btn_plasma://plasma等离子体示例
                startActivity(new Intent(this, PlasmaActivity.class));
                break;
            case R.id.btn_water_ripple://plasma做水波纹
                toast("未找到对应的.c文件, 所以驴子水波纹实现不了!");
                break;
            default:
                break;
        }
    }

    //供C语言调用
    public void calledByC(String msg) {
        System.out.println("calledByC: msg=" + msg);
        toast("calledByC: msg=" + msg);
    }

    /**
     * C调用静态方法
     */
    public void StaticMethodCalledByC(int randValue) {
        System.out.println("StaticMethodCalledByC: Java中的<静态>方法被C调用了,randValue=" + randValue);
//        ToastUtils.showShort("StaticMethodCalledByC: Java中的<静态>方法被C调用了,randValue=" + randValue);
    }

    @Override
    protected void onDestroy() {
        GuoLuPress.stopMonitor();
        super.onDestroy();
    }
}