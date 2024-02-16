package com.actor.testapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;

import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.ThreadUtils;
import com.actor.testapplication.R;
import com.actor.testapplication.databinding.ActivityTestBinding;

/**
 * Description: Test测试页面
 * Date       : 2019/12/30 on 11:38
 */
public class TestActivity extends BaseActivity<ActivityTestBinding> {

    private final Handler mainHandler = new Handler(Looper.getMainLooper()/*, @Nullable Callback callback*/) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            LogUtils.error(msg);
        }
    };
    private Handler subHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Test测试页面");

        ThreadUtils.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0;
                message.arg1 = 1;
                message.arg2 = 2;
                message.obj = "子线程 -> 主线程: 发消息!";
                mainHandler.sendMessage(message);

                //子线程Handler
                Looper looper = Looper.myLooper();
                if (looper != null) {
                    Looper.prepare();       //初始化Looper
                    subHandler = new Handler(looper) {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            LogUtils.error(msg);
                        }
                    };
                    Looper.loop();          //启动消息循环
                }
            }
        });
    }

    //换行
    private String str1 = "这是一道关于正方体展开图的问题。根据题目描述，我们需要在右下图中画出相应的图案。\n\n首先观察左上图中的三个面的图案：前、上和右。\n-\n 前面是一个圆形；\n- 上面是两个相交的“X”形线段；\n- 右侧面也是一个圆形。\n\n接下来我们分析一下右下图：\n- 左侧面上有一个红色箭头指向下方，表示这个平面朝向图像底部方向；\n- 下底面有四个小三角形组成\n的大三角形，并且每个顶点处都有一个小圆圈标记；\n\n综上所述，在右侧下面应该绘制如下图形：\n\n[图片]\n\n其中，“O”的位置代表前面的圆形、“△”的位置代表上面的交叉线条以及后面的圆形。“→”的方向指示了该平面\n向下的方向。这样就完成了整个正方形物体所有表面图案的描绘。";

    //百度通义千问
    private String str2 = "若点D在反比例函数$y=\\frac{k}{x}（{x＜0}）$的图像上求k的值\n\n由题意得：AC⊥OF，则∠DAO+∠OAD=90°，又∵∠BOD=90°∴∠BAD+∠OAD=9";

    private String str3 = "这是一张显示了数学问题及其答案的图片。由于我无法直接查看或解析图像中的文本，因此不能提供对具体答案的评估。\n" +
            "        不过我可以告诉你如何解决这类几何证明和计算的问题：\n" +
            "        对于(1)，你需要根据题目中给定的角度（∠B=45°, ∠C=60°）来确定角AC'D与角度关系，并利用折叠后三角形相似性或者全等性的性质求出∠C'DB。\n" +
            "        对于(2) ，你可以使用已知条件CD = AB + BD 和 AD 平分∠BAC 来构建直角三角形ADC 或者ADB 的边长比例关系，然后通过面积公式 S_{△ABD}=\\frac{1}{2}\\cdot|AD|\\cdot|BD| 求得面积。\n" +
            "        对于(3)，你可能需要先找出所有未知量之间的关系，例如 CD、BC 等长度的关系式，然后再结合所给信息如∠C=24°进行推理得出结果。\n" +
            "        如果你有具体的步骤或疑问，请详细描述以便我能更准确地帮助你检查答案或提供建议。\n" +
            "        然后利用勾股定理求出BD的长度：$\\sqrt{A{C^2} - C{D^2}} = \\sqrt{{4^2}-{2^2}}$ ";

    @Override
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                Message message = new Message();
                message.what = 3;
                message.arg1 = 4;
                message.arg2 = 5;
                message.obj = "主线程 -> 子线程: 发消息!";
                subHandler.sendMessage(message);
                break;
            case R.id.btn1:
                viewBinding.webView.setContent(str2);
                break;
            case R.id.btn2:
                viewBinding.webView.setContent(str3);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainHandler.removeCallbacksAndMessages(null);
        if (subHandler != null) subHandler.removeCallbacksAndMessages(null);
    }
}
