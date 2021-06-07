package com.actor.cpptest;

import android.content.Context;

/**
 * description: 常量
 *
 * @author : ldf
 * date       : 2021/6/7 on 12
 * @version 1.0
 */
public class ConstUtils {

    static {
        System.loadLibrary("native-lib");
    }
    public static native void jniInit(Context context, boolean isDebugMode);
    public static native String getString(String key);

}
