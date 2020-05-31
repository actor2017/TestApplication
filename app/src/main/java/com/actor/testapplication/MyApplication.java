package com.actor.testapplication;

import android.support.annotation.NonNull;

import com.actor.myandroidframework.application.ActorApplication;
import com.zhouyou.http.EasyHttp;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Description: 类的描述
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-9-4 on 09:14
 *
 * @version 1.0
 */
public class MyApplication extends ActorApplication {

    @Override
    protected void configEasyHttp(EasyHttp easyHttp) {
        //配置张鸿洋的OkHttpUtils
        OkHttpUtils.initClient(EasyHttp.getOkHttpClient());
    }

    @NonNull
    @Override
    protected String getBaseUrl() {
        return "https://api.github.com";
    }


    @Override
    protected void onUncaughtException(Thread thread, Throwable e) {
//        System.exit(-1);//退出
    }
}
