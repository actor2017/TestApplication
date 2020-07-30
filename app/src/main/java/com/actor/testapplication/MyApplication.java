package com.actor.testapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.actor.myandroidframework.application.ActorApplication;
import com.actor.testapplication.utils.GsonField;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

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
    protected OkHttpClient.Builder configOkHttpClientBuilder(OkHttpClient.Builder builder) {
        return null;
    }

    @NonNull
    @Override
    protected String getBaseUrl() {
        return "https://api.github.com";
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Gson gson = GsonUtils.getGson().newBuilder()
//                .registerTypeAdapterFactory(DateJsonDeserializer.FACTORY)
//                .create();
//        GsonUtils.setGsonDelegate(gson);

        User user = new User(11, new Date(System.currentTimeMillis()));
        String json = GsonUtils.toJson(user);
        System.out.println("json: " + json);
        json = "{\"age\":\"11\",\"date\":\"2020-01-02 03:04:05\"}";//Jul 30, 2020 4:26:16 PM

        User user1 = GsonUtils.fromJson(json, User.class);
        System.out.println(1);
    }



    @Override
    protected void onUncaughtException(Thread thread, Throwable e) {
//        System.exit(-1);//退出
    }
}

class User {

    @SerializedName("age")
    public int age;
    //    public Integer age;

    @GsonField
    public Date date;

    public User() {}
    public User(int age, Date date) {
//    public User(Integer age, Date date) {
        this.age = age;
        this.date = date;
    }
}
