#include <jni.h>
#include <stdlib.h>
#include <unistd.h>

//监测状态
jboolean monitorState;

/**
 * 获取锅炉压力值
 */
JNIEXPORT void JNICALL
Java_com_actor_cpptest_GuoLuPress__1getPress(JNIEnv *env, jclass clazz) {
    monitorState = JNI_TRUE;
    while (monitorState) {
        int press = rand() % 101;
        //1.获取方法
        jmethodID method = (*env)->GetMethodID(env, clazz, "onGetProgress", "(I)V");
        //2.实例化该class对应的实例
        jobject object = (*env)->AllocObject(env, clazz);
        //3.调用方法
        (*env)->CallVoidMethod(env, object, method, press);
        sleep(1);
    }
}

/*
 * 设置监测状态
 */
JNIEXPORT void JNICALL
Java_com_actor_cpptest_GuoLuPress_setMonitorState(JNIEnv *env, jclass clazz,
                                                  jboolean is_start_monitor) {
    monitorState = is_start_monitor;
}

