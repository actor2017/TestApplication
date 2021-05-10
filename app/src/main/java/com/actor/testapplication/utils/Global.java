package com.actor.testapplication.utils;

import android.content.Context;

import com.blankj.utilcode.util.AppUtils;

/**
 * Description: 全局变量
 * Date       : 2019-9-17 on 15:08
 *
 * @version 1.0
 */
public class Global {

    public static final String BASE_URL = "https://api.github.com";

    //GET/POST, 这个也可以检测更新
//    public static final String CHECK_UPDATE = "https://github.com/actor20170211030627/" +
//            "TestApplication" +
//            "/raw/master/" +
//            "app" +
//            "/build/outputs/apk/debug/output-metadata.json";

    //github.com, GET请求
//    public static final String CHECK_UPDATE = "https://raw.githubusercontent.com/" +
//            "actor20170211030627/" +
//            "TestApplication" +//项目名
//            "/master/" +
//            "app" +//模块名
//            "/build/outputs/apk/debug/output-metadata.json";

    //gitee.com码云, GET请求
    public static final String CHECK_UPDATE = "https://gitee.com/actor20170211030627/" +
            "TestApplication" + //项目名
            "/raw/master/" +
            "app" +             //模块名
            "/build/outputs/apk/debug/output-metadata.json";


    //这个也可以下载
//    public static final String DOWNLOAD_URL = "https://github.com/actor20170211030627/" +
//            "TestApplication" +
//            "/raw/master/" +
//            "app" +
//            "/build/outputs/apk/debug/Test_" + AppUtils.getAppVersionName() + ".apk";
    //github.com(国内网速慢)
    public static final String DOWNLOAD_URL = "https://raw.githubusercontent.com/" +
            "actor20170211030627/" +
            "TestApplication" + //项目名
            "/master/" +
            "app" +             //模块名
            "/build/outputs/apk/debug/" +
            "Test_" +             //模块名
            AppUtils.getAppVersionName() + ".apk";

    //gitee.com码云(大于1M要登录后才能下载)
//    public static final String DOWNLOAD_URL = "https://gitee.com/actor20170211030627/" +
//            "TestApplication" +
//            "/raw/master/" +
//            "app" +
//            "/build/outputs/apk/debug/" +
//            "Test_" +
//            AppUtils.getAppVersionName() + ".apk";


    //thanks www.baidu.com百度
    public static final String BAIDU_VIDEO = "http://tb-video.bdstatic.com/tieba-smallvideo-transcode/8_4871b1e9218ec13f03131176197ef53d_1.mp4";


    public static final String JNI_IP = "IP";
    public static final String JNI_PORT = "PORT";

    static {System.loadLibrary("native-lib");}
    public static native void jniInit(Context context, boolean isDebugMode);
    public static native String getString(String key);
}
