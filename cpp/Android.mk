# 在Eclipse项目中, 这个文件和.c文件一起放在 \jni 目录(Eclipse一般使用\jni 文件夹, 看视频)
# 作用：交叉编译的时候，告诉工具去项目中的\jni文件夹中找.c的源文件

# 要编译的C文件的根目录, 右边的赋值代表根目录即为 Android.mk 所在的目录
# $表示调用ndk中定义好的方法或者变量
# 加载本地的jni目录，当在dos窗口中执行ndk-build命令时，会找工程目录下中的jni目录；
LOCAL_PATH := $(call my-dir)

# 在使用NDK编译工具时, 对编译环境中所用到的全局变量清0
# 下次重新生成动态库时，清理掉上次生成的变量
include $(CLEAR_VARS)

# 在C代码中使用Logcat 日志(放到include $(CLEAR_VARS)下面), 然后添加Log.c里面的代码. (固定写法)
LOCAL_LDLIBS   += -llog

# 编译之后生成动态库.so的名称, 会在这个名字前加lib, 后面加.so   => libhello.so
LOCAL_MODULE    := hello

# 要被编译的C文件的文件名
LOCAL_SRC_FILES := hello.c  abc.c  def.c

# NDK编译时会生成一些共享库
# 指定了生成库的类型：BUILD_SHARED_LIBRARY表示动态库.so，BUILD_STATIC_LIBRARY表示静态库.a
# 动态库.so文件小，静态库.a文件大。
include $(BUILD_SHARED_LIBRARY)
