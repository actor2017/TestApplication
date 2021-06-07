#include <jni.h>
#include <stdlib.h>

/**
 * C调用Java: 主要是利用反射, 这样就能调用Java代码了, Java的private方法也可以.
 * @param env 二级结构体(struct)指针, 里面很多方便的Api. 这个参数表示Java本地运行环境
 * @param clazz C函数的调用者class, CCallJava.class. 静态方法是jclass, 非静态是jobject
 * @return 返回String类型
 */
JNIEXPORT void JNICALL
Java_com_actor_cpptest_CCallJava_callVoid(JNIEnv *env, jclass clazz) {
    //反射获取class
    jclass class = (*env) -> FindClass(env, "com/actor/cpptest/CCallJava");
    //参数3:方法名称, 参4:该函数的签名:(String参数类型)V是返回类型
    jmethodID methodId = (*env) -> GetMethodID(env, class, "calledByC", "(Ljava/lang/String;)V");
    //使用AllocObject方法,实例化该class对应的实例
    jobject object = (*env) -> AllocObject(env, class);
    jstring stringUTF = (*env) -> NewStringUTF(env, "Java中的方法被C调用了");
    (*env)->CallVoidMethod(env, object, methodId, stringUTF);//调用方法
}

/**
 * C调用Java的静态方法
 */
JNIEXPORT void JNICALL
Java_com_actor_cpptest_CCallJava_staticMethodCalledVoid(JNIEnv *env, jclass clazz) {
    jclass class = (*env)->FindClass(env, "com/actor/cpptest/CCallJava");
    jmethodID staticMethodId = (*env)->GetStaticMethodID(env, class, "StaticMethodCalledByC", "(I)V");
    //    jobject object = (*env)->AllocObject(env, class);//静态方法就不需要实例化对象
    (*env)->CallStaticVoidMethod(env, class, staticMethodId, rand());
}
