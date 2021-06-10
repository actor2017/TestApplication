#include <jni.h>
#include <stdlib.h>
#include <string.h>
#include "Log.c"

/**
 * C调用Java: 主要是利用反射, 这样就能调用Java代码了, Java的private方法也可以.
 * @param env 二级结构体(struct)指针, 里面很多方便的Api. 这个参数表示Java本地运行环境
 * @param thiz C函数的调用者对象, CCallJava. 静态方法是jclass, 非静态是jobject
 * @return 返回String类型
 */
JNIEXPORT void JNICALL
Java_com_actor_cpptest_CCallJava_callByC(JNIEnv *env, jobject thiz) {
    //反射获取class
    jclass class = (*env)->FindClass(env, "com/actor/cpptest/CCallJava");
    //参数3:方法名称, 参4:该函数的签名:(String参数类型)V是返回类型
    jmethodID methodId = (*env)->GetMethodID(env, class, "calledByC", "(Ljava/lang/String;)V");
    //实例化该class对应的实例
    jobject object = (*env)->AllocObject(env, class);
    jstring stringUTF = (*env)->NewStringUTF(env, "Java中的方法被C调用了");
    (*env)->CallVoidMethod(env, object, methodId, stringUTF);//调用方法
}
//JNIEXPORT void JNICALL
//Java_com_actor_cpptest_CCallJava_callByC(JNIEnv *env, jobject thiz, /*jclass clazz,*/
//                                         jstring callback_method_name, jstring params_signature) {
//    //反射获取class, 会反射回调这个class的方法
//    jclass clazz = (*env)->FindClass(env, "com/actor/cpptest/CCallJava");
//    LOGE("clazz");
////    jobject object11 = (*env)->AllocObject(env, clazz);
////    jclass clazz = (*env) -> GetObjectClass(env, jclass);
//
//    //参3: 代码是否复制一份, false: 直接使用java数组的内存地址
//    const jchar *methodName = (*env)->GetStringChars(env, callback_method_name, JNI_FALSE);
//    LOGE("methodName");
//    const jchar *paramSignature = (*env)->GetStringChars(env, params_signature, JNI_FALSE);
//    LOGE("paramSignature");
//    //参数3:方法名称, 参4:该函数的签名:(String参数类型)V是返回类型
//    jmethodID methodId = (*env)->GetMethodID(env, clazz, methodName, paramSignature);
////    jmethodID methodId = (*env)->GetMethodID(env, clazz, callback_method_name, params_signature);
//    LOGE("methodId");
//    //实例化该class对应的实例
////    jobject object = (*env)->AllocObject(env, clazz);
////    jobject object = (*env)->NewGlobalRef(env, thiz);
////    jobject object = (*env)->NewObject(env, clazz, methodId/*, ??*/);
//    LOGE("object");
//    jstring dst = "来自C函数: 回调Java方法 ";
////    jstring stringUTF = (*env)->NewStringUTF(env, dst/*strcat(dst, methodName)*/);
//    LOGE("stringUTF");
//    //要反射的方法返回类型是void, 所以 CallVoidMethod
//    (*env)->CallVoidMethod(env, thiz, methodId, dst);//调用方法
//}

/**
 * C调用Java的静态方法
 */
JNIEXPORT void JNICALL
Java_com_actor_cpptest_CCallJava_staticMethodCalledVoid(JNIEnv *env, jclass clazz) {
    jclass class = (*env)->FindClass(env, "com/actor/cpptest/CCallJava");
    jmethodID staticMethodId = (*env)->GetStaticMethodID(env, class, "StaticMethodCalledByC",
                                                         "(I)V");
    //    jobject object = (*env)->AllocObject(env, class);//静态方法就不需要实例化对象
    (*env)->CallStaticVoidMethod(env, class, staticMethodId, rand());
}
//JNIEXPORT void JNICALL
//Java_com_actor_cpptest_CCallJava_staticMethodCalledVoid(JNIEnv *env, jclass clazz, jclass clazz_1,
//                                                        jstring callback_method_name,
//                                                        jstring params_signature) {
//    jmethodID staticMethodId = (*env)->GetStaticMethodID(env, clazz_1, callback_method_name,
//                                                         params_signature);
//    (*env)->CallStaticVoidMethod(env, clazz_1, staticMethodId, rand());
//}