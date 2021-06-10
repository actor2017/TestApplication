package com.actor.cpptest;

import android.content.Context;

/**
 * description: 常量
 * 1.存放一些保密的数据,比如"服务端IP","端口","接口地址","授权的key"等等.
 * 2.顺便 so里面验证 app的签名 来防止别人盗用so文件
 * 3.使用:
 *      1.先在Application里初始化: ConstUtils.jniInit(applicationContext, isDebugMode);
 *      2.再调用: ConstUtils.getString(ConstUtils.IP);
 *
 * @author : ldf
 * date       : 2018/11/13 on 12
 * @version 1.0
 */
public class ConstUtils {

    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 在Application中初始化
     * @param isDebugMode 是否是debug模式
     */
    public static native void jniInit(Context context, boolean isDebugMode);

    /**
     * 获取c里面的内容
     * @param key 通过key获取
     */
    public static native String getString(String key);


    public static final String IP = "IP";
    public static final String PORT = "PORT";
}
