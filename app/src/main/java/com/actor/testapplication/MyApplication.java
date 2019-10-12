package com.actor.testapplication;

import android.support.annotation.Nullable;

import com.actor.myandroidframework.application.ActorApplication;
import com.actor.testapplication.utils.Global;

import okhttp3.OkHttpClient;

/**
 * Description: 类的描述
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-9-4 on 09:14
 *
 * @version 1.0
 */
public class MyApplication extends ActorApplication {

    @Nullable
    @Override
    protected OkHttpClient.Builder getOkHttpClientBuilder(OkHttpClient.Builder builder) {
        return builder;
    }

    @Override
    protected String getBaseUrl() {
        return Global.BASE_URL;
    }
    @Override
    protected void onUncaughtException(Thread thread, Throwable e) {
        System.exit(-1);
    }
}
