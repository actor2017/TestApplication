package com.actor.cpptest;

/**
 * Description: Java调C
 * Date       : 2018/10/15 on 18:04
 */

public class JavaCallC {
    private static final JavaCallC instance = new JavaCallC();

    // Used to load the 'native-lib' library on application startup.
    static {
        //libname: 就是 CMakeLists.txt 中add_library() 第一个参数
        System.loadLibrary("native-lib");
    }

    public static JavaCallC getInstance() {
        return instance;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     * 静态方法参2是jclass, 非静态是jobject
     */
    public native String stringFromJNI();

    //这是生成的Activity, 可以随便写一个方法, 然后Alt+Enter
    public static native int _add(int x, int y);

    public static native void arrayAdd10(int[] arr);//给数组每项加10
}
