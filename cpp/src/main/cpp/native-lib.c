#include <jni.h>
#include "Log.c"

 /**
  * Java调用C, 方法名称: Java_包名(.替换成_)_调用类名_方法名
  * @param env 二级结构体(struct)指针, 里面很多方便的Api. 这个参数表示Java本地运行环境
  * @param thiz C函数的调用者对象, JavaCallC. 静态方法是jclass, 非静态是jobject
  * @return 返回String类型
  */
//JNIEXPORT: AndroidStudio生成
//JNICALL  : AndroidStudio生成, 表示Java本地函数调用这个方法
JNIEXPORT jstring JNICALL
Java_com_actor_cpptest_JavaCallC_stringFromJNI(JNIEnv *env, jobject thiz) {
//    char* hello = "im from c";
    jstring hello = "Hello from JNI ! Compiled with ABI ";
    //1.拿到结构体, 掉方法
//    return (**env).NewStringUTF(env, hello);//
    //2.用一级结构体指针 -> 调方法
    return (*env)-> NewStringUTF(env, hello);
}

//如果方法有'_', 用'_1'替换
JNIEXPORT jint JNICALL
Java_com_actor_cpptest_JavaCallC__1add(JNIEnv *env, jclass clazz, jint x, jint y) {
    LOGD("x + y =%d\n", x + y);
    LOGI("也可以只写一个参数");
    LOGE("x + y =%s\n", "y + z");
    return x + y;
}

JNIEXPORT void JNICALL
Java_com_actor_cpptest_JavaCallC_arrayAdd10(JNIEnv *env, jclass clazz, jintArray arr) {
    //1. 获取数组的大小
    jsize length = (*env) -> GetArrayLength(env, arr);

    //2. 获取数组首元素的地址. 参3: 代码是否复制一份,false表示不复制,直接使用java数组的内存地址
    jint* address = (*env)->GetIntArrayElements(env, arr, JNI_FALSE);

    //3. 给每项元素+10
//    int i;
    for(int i = 0; i < length; i++){
        *(address+i) += 10;
    }
    /**
     * 释放本地数组内存
     * 0表示将值修改到java数组中, 然后释放本地数组, 这个参数还有两个可选值:
     *      JNI_COMMIT: 修改值到java数组，但是不释放本地数组内存
     *      JNI_ABORT: 不修改值到java数组，但是会释放本地数组内存
     */
    //关键代码啊,否则数组的值不会改变!!!
    (*env)-> ReleaseIntArrayElements(env, arr, address, 0);
}
