package com.actor.testapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.actor.myandroidframework.application.ActorApplication;
import com.actor.testapplication.utils.database.GreenDaoUtils;
import com.greendao.gen.ItemEntityDao;

import java.util.concurrent.TimeUnit;

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

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * @param context application
         * @param isDebug 如果是debug模式, 数据库操作会打印日志
         * @param daoClasses 数据库表对应的实体(ItemEntity.java)的dao, 示例:
         *                   ItemEntityDao.class(由'Build -> Make Project'生成), ...
         */
        GreenDaoUtils.init(this, isDebugMode, ItemEntityDao.class/*, ...*/);
    }

    @Nullable
    @Override
    protected OkHttpClient.Builder getOkHttpClientBuilder(OkHttpClient.Builder builder) {
        return builder.connectTimeout(60_000L, TimeUnit.MILLISECONDS)//默认10s, 可不设置
                .readTimeout(60_000L, TimeUnit.MILLISECONDS)//默认10s, 可不设置
                .writeTimeout(60_000L, TimeUnit.MILLISECONDS);//默认10s, 可不设置
    }

    @NonNull
    @Override
    protected String getBaseUrl() {
        return "https://api.github.com";
    }


    @Override
    protected void onUncaughtException(Thread thread, Throwable e) {
        e.printStackTrace();
    }
}
