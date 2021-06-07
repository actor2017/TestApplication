package com.actor.cpptest;

/**
 * Description: C调用Java,主要是利用反射，这样就能调用Java代码了
 * Date       : 2017/1/8 on 20:17
 */

public class CCallJava {

    static {
        //libname: 就是 CMakeLists.txt 中add_library() 第一个参数
        System.loadLibrary("native-lib");
    }

    /**
     * 这个方法调用C函数, 然后C调用下面的方法{@link #calledByC(String)}
     */
    public static native void callVoid();

    //供C语言调用
    public void calledByC(String msg) {
        System.out.println("calledByC: msg=" + msg);
//        ToastUtils.showShort("calledByC: msg=" + msg);
    }



    /**
     * 这个方法调用C函数, 然后C调用下面的静态方法{@link #StaticMethodCalledByC(int)}
     */
    public static native void staticMethodCalledVoid();

    /**
     * C调用静态方法
     */
    public static void StaticMethodCalledByC(int randValue){
        System.out.println("StaticMethodCalledByC: Java中的<静态>方法被C调用了,randValue=" + randValue);
//        ToastUtils.showShort("StaticMethodCalledByC: Java中的<静态>方法被C调用了,randValue=" + randValue);
    }
}
