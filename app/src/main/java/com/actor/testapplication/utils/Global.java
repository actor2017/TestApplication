package com.actor.testapplication.utils;

/**
 * Description: 全局变量
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-9-17 on 15:08
 *
 * @version 1.0
 */
public class Global {

    //github.com, GET请求
//    public static final String CHECK_UPDATE = "https://raw.githubusercontent.com/" +
//            "actor20170211030627/" +
//            "TestApplication" +//项目名
//            "/master/" +
//            "app" +//模块名
//            "/build/outputs/apk/debug/output.json";

    //gitee.com码云, GET请求
    public static final String CHECK_UPDATE = "https://gitee.com/actor2017/" +
            "TestApplication" +//项目名
            "/raw/master/" +
            "app" +//模块名
            "/build/outputs/apk/debug/output.json";


    //github.com
//    public static final String DOWNLOAD_URL = "https://raw.githubusercontent.com/" +
//            "actor20170211030627/" +
//            "TestApplication" +//项目名
//            "/master/" +
//            "app" +//模块名
//            "/build/outputs/apk/debug/app-debug.apk";

    //gitee.com码云
    public static final String DOWNLOAD_URL = "https://gitee.com/actor2017/" +
            "TestApplication" +
            "/raw/master/" +
            "app" +
            "/build/outputs/apk/debug/app-debug.apk";
}
