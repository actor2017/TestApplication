package com.actor.testapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.ThreadUtils;
import com.actor.testapplication.databinding.ActivityTestBinding;

/**
 * Description: Test测试页面
 * Date       : 2019/12/30 on 11:38
 */
public class TestActivity extends BaseActivity<ActivityTestBinding> {

    private TextView    tvResult;//显示结果
    private EditText    editText;
    private Button      btn;

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
        tvResult = viewBinding.tvResult;
        btn = viewBinding.btn;
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

    @Override
    public void onViewClicked(View view) {
        Message message = new Message();
        message.what = 3;
        message.arg1 = 4;
        message.arg2 = 5;
        message.obj = "主线程 -> 子线程: 发消息!";
        subHandler.sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainHandler.removeCallbacksAndMessages(null);
        if (subHandler != null) subHandler.removeCallbacksAndMessages(null);
    }
}
