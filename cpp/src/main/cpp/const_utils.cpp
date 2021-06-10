#include <jni.h>
#include <cstring>  //#include <string.h>
#include <utility>
#include "com_actor_cpptest_ConstUtils.h"
#include "Log.c"

/**
 * Created by ldf on 2018/11/13.
 * 1.存放一些保密的数据,比如"服务端IP","端口","接口地址","授权的key"等等.
 * 2.顺便 so里面验证 app的签名 来防止别人盗用so文件
 * 3.使用:
 *      1.先在Application里初始化: ConstUtils.jniInit(applicationContext, isDebugMode);
 *      2.再调用: ConstUtils.getString(ConstUtils.IP);
 */

jboolean is_valid = JNI_FALSE;//是否验证通过
jboolean isDebugMode = JNI_FALSE;//可以被jboolean赋值, 是否是debug模式. bool isDebugMode = false;

//结构体变量(Object)
typedef struct {
    char *key;
    char *value;
} ListEntity;

static const ListEntity lists[] = {
        {"IP",   "127.0.0.1"},
        {"PORT", "8080"}
};

//本app的签名,只有和本签名一致的app 才会返回合法的 授权 key
const char *app_signature = "308203773082025fa003020102020414b72ba3300d06092a864"
                            "886f70d01010b0500306b310d300b0603550406130430303836"
                            "311230100603550408130963686f6e6771696e6731123010060"
                            "3550407130963686f6e6771696e67310e300c060355040a1305"
                            "6163746f72310e300c060355040b13056163746f72311230100"
                            "60355040313096163746f722e4c65653020170d313930393137"
                            "3031343932335a180f33303139303131383031343932335a306"
                            "b310d300b060355040613043030383631123010060355040813"
                            "0963686f6e6771696e67311230100603550407130963686f6e6"
                            "771696e67310e300c060355040a13056163746f72310e300c06"
                            "0355040b13056163746f7231123010060355040313096163746"
                            "f722e4c656530820122300d06092a864886f70d010101050003"
                            "82010f003082010a028201010085437bc4e58af23cb46b26b41"
                            "fd9a8b78014fc08f45aa4cc93897f7c520392bec13e2fcd8da9"
                            "0f6b5ac134069695a0b2c0bfc8b74d57daabbeeb2a25c800b34"
                            "b6c1b1b0bb1c1e1e399a4449e1754639bb50ee6bce3c0bf5365"
                            "241f8a95cdaf8dd779a91011cd25c5eb994a61b4bafb095fefb"
                            "8f2e385e66fd003cfb1f021a2997d7d42a213dbedd92bbbcb84"
                            "cdf17b19f33ddbf3ddb1109cad28d5fbb291fb1a31eca65b0e2"
                            "a951628c2b64ceadae433a2bce451219b522827479441918d3a"
                            "379b3e8c2f261e629bcf2fd9cbdb3cc4f1c1ae74269936023d5"
                            "f35132833b14ee2f743ce02f48ca6d05d0baf2be6d71a116c4e"
                            "5383ef07f08cce80d97f9e69a5d70203010001a321301f301d0"
                            "603551d0e041604145f6c84e4d17859d4d420fa846d73595f0f"
                            "f39ca3300d06092a864886f70d01010b050003820101001501c"
                            "2261e568644d22e5e9fbbbb02b7633262be87fef546c686d591"
                            "37b0066dd1679b3028c8c114d99703876027bd6c93fae1cb713"
                            "5f3dda8146205cde5081612797948803274817f871a74678e02"
                            "2f68d8d2d9126cb783c4bb33669a1475d15f38c6c83b787fa6f"
                            "9fe4b814335acba8d9cd850786ce4a9e7e3b710ee263967214b"
                            "3fcaa54af4a295c9bb0557f457133d4526ae6bb690903d1303f"
                            "c78a52381db7ef390b9e0b37a5bbdad17a229b965d196cccfde"
                            "e083a32bc2d49ca3fd26add5cbf2484a799bc53f95dbc037fe0"
                            "77f6fd6be1a5d808b1b02d32c1dfb1e833483fcf1552e3fce71"
                            "4e1f294c1ac4ff079cb9e87c20643b5bae3797b4ef5fa5b1";

/**
 * 在Application中初始化
 * @param env ★★★★★注意: 这里是一级结构体指针. C++有对象的概念★★★★★
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_actor_cpptest_ConstUtils_jniInit(JNIEnv *env, jclass clazz, jobject context,
                                          jboolean is_debug_mode) {
    isDebugMode = is_debug_mode;
    jclass native_class = env->GetObjectClass(context);
    jmethodID pm_id = env->GetMethodID(native_class, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject packageManager = env->CallObjectMethod(context, pm_id);
    jclass pm_clazz = env->GetObjectClass(packageManager);
    // 得到 getPackageInfo 方法的 ID
    jmethodID package_info_id = env->GetMethodID(pm_clazz, "getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jclass native_classs = env->GetObjectClass(context);
    jmethodID mId = env->GetMethodID(native_classs, "getPackageName", "()Ljava/lang/String;");
    jstring pkg_str = static_cast<jstring>(env->CallObjectMethod(context, mId));
// 获得应用包的信息
    jobject pi_obj = env->CallObjectMethod(packageManager, package_info_id, pkg_str, 64);
// 获得 PackageInfo 类
    jclass pi_clazz = env->GetObjectClass(pi_obj);
// 获得签名数组属性的 ID
    jfieldID signatures_fieldId = env->GetFieldID(pi_clazz, "signatures", "[Landroid/content/pm/Signature;");
    jobject signatures_obj = env->GetObjectField(pi_obj, signatures_fieldId);
    jobjectArray signaturesArray = (jobjectArray) signatures_obj;
    jsize size = env->GetArrayLength(signaturesArray);
    jobject signature_obj = env->GetObjectArrayElement(signaturesArray, 0);
    jclass signature_clazz = env->GetObjectClass(signature_obj);
    jmethodID string_id = env->GetMethodID(signature_clazz, "toCharsString", "()Ljava/lang/String;");
    jstring str = static_cast<jstring>(env->CallObjectMethod(signature_obj, string_id));
    char *c_msg = (char *) env->GetStringUTFChars(str, JNI_FALSE);

    if (strcmp(c_msg, app_signature) == 0)//签名一致  返回合法的 api key，否则返回错误
    {
//        return (env)->NewStringUTF(c_msg);
        is_valid = JNI_TRUE;//is_valid = true
    } else {
//        return (env)->NewStringUTF("error");
        is_valid = JNI_FALSE;//is_valid = false;
    }
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_actor_cpptest_ConstUtils_getString(JNIEnv *env, jclass clazz, jstring key) {
    if (key == nullptr) return nullptr;//if (key == NULL)
    if (is_valid) {
        int i = 0;
        const char *str = env->GetStringUTFChars(key, JNI_FALSE);//jstring --> char*
        while (lists[i].value != nullptr && strcmp(lists[i].key, str) != 0) {
            i++;
        }
        return (env)->NewStringUTF(lists[i].value);
    } else return (env)->NewStringUTF("碑燞鶔t€sGVV鴩顊p9%z匌,砜?歧e?fpE?0涒鴿J慎蚾幘");//验证失败,返回乱码
}
