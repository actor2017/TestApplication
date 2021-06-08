package com.actor.cpptest;

/**
 * Description: C调用Java,主要是利用反射，这样就能调用Java代码了
 * Date       : 2017/1/8 on 20:17
 */

public class CCallJava {
    private static final CCallJava INSTANCE = new CCallJava();
    public static CCallJava getInstance() {
        return INSTANCE;
    }

    static {
        //libname: 就是 CMakeLists.txt 中add_library() 第一个参数
        System.loadLibrary("native-lib");
    }

    /**
     * 这个方法调用C函数, 然后C回调 clazz.callbackMethodName(paramsSignature)
     * @param clazz 回调那个class里的方法
     * @param callbackMethodName 回调方法名称: {@link #calledByC(String)}
     * @param paramsSignature 该函数的签名, 可通过 javap -s 全类名 获取签名
     *                        ()V                       //括号内是参数类型, V: 返回类型是void
     *                        (I)V                      //参数int
     *                        (Landroid/os/Bundle;)V    //Activity的onCreate方法
     *                        (Ljava/lang/String;)V     //参数String, 返回void
     */
    public native void callByC();
    //下方方法崩溃
//    public native void callByC(Class<?> clazz, String callbackMethodName, String paramsSignature);

    //供C语言调用
    public void calledByC(String msg) {
        System.out.println("calledByC: msg=" + msg);
//        ToastUtils.showShort("calledByC: msg=" + msg);
    }



    /**
     * 这个方法调用C函数, 然后C调用下面的静态方法{@link #StaticMethodCalledByC(int)}
     */
    public static native void staticMethodCalledVoid();
    //下方方法崩溃
//    public static native void staticMethodCalledVoid(Class<?> clazz, String callbackMethodName, String paramsSignature);

    /**
     * C调用静态方法
     */
    public static void StaticMethodCalledByC(int randValue) {
        System.out.println("StaticMethodCalledByC: Java中的<静态>方法被C调用了,randValue=" + randValue);
//        ToastUtils.showShort("StaticMethodCalledByC: Java中的<静态>方法被C调用了,randValue=" + randValue);
    }
}
