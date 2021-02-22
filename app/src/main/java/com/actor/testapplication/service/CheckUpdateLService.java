package com.actor.testapplication.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.actor.myandroidframework.utils.SPUtils;
import com.actor.myandroidframework.utils.okhttputils.BaseCallback;
import com.actor.myandroidframework.utils.okhttputils.MyOkHttpUtils;
import com.google.gson.JsonObject;

import okhttp3.Call;

/**
 * 检查更新并Lock
 * 1.添加权限
 * <uses-permission android:name="android.permission.INTERNET" />
 *
 * 2.在AndroidManifest.xml中注册
 *
 * 3.修改请求地址
 *
 * 4.开启服务
 * startService(new Intent(this, CheckUpdateLService.class));
 *
 * 5.使用
 * @Override
 * public void startActivity(Intent intent) {
 *     if (SPUtils.getBoolean(CheckUpdateLService.Eenable, true)) {
 *         super.startActivity(intent);
 *     } else toast(CheckUpdateLService.ERR_MSG);
 * }
 * @Override
 * public void startActivityForResult(Intent intent, int requestCode) {
 *     if (SPUtils.getBoolean(CheckUpdateLService.Eenable, true)) {
 *         super.startActivityForResult(intent, requestCode);
 *     } else toast(CheckUpdateLService.ERR_MSG);
 * }
 *
 * @version 1.0
 */
public class CheckUpdateLService extends Service {

//    private static final String  E       = "https://raw.githubusercontent.com/actor20170211030627/TestApplication/master/app/build/outputs/apk/debug/yunweipei";
    private static final String  E       = "https://gitee.com/actor20170211030627/TestApplication/raw/master/app/build/outputs/apk/debug/yunweipei";
    private static final long    DELAY   = 9 * 60 * 1000L;
    private static int           times   = 1;
    public static final  String  Eenable = "Eenable";
    public static        String  ERR_MSG = "";//错误信息
    @SuppressLint("HandlerLeak")
    private final        Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            check();
            sendEmptyMessageDelayed(0, DELAY * times ++);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!SPUtils.getBoolean(Eenable, true)) check();
        handler.sendEmptyMessageDelayed(0, DELAY * times ++);
    }

    private void check() {
        MyOkHttpUtils.get(E, null, new BaseCallback<JsonObject>(null) {
            @Override
            public void onOk(@NonNull JsonObject info, int id) {
                boolean enabled = info.getAsJsonObject("enabled").getAsBoolean();
                SPUtils.putBoolean(Eenable, enabled);
                ERR_MSG = info.getAsJsonObject("message").getAsString();
            }

            @Override
            public void onError(int id, Call call, Exception e) {
            }
        });
    }
}
