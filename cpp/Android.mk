# 在Eclipse项目中, 这个文件和.c文件一起放在 \jni 目录(Eclipse一般使用\jni 文件夹, 看视频)
# 作用：交叉编译的时候，告诉工具去项目中的\jni文件夹中找.c的源文件

# 编译时找项目里jni文件夹
LOCAL_PATH := $(call my-dir)

# 清空上一次编译的
include $(CLEAR_VARS)

# 编译之后生成.so库文件的名字(libhello.so)
LOCAL_MODULE    := hello

# 要编译的c的源文件
LOCAL_SRC_FILES := hello.c  abc.c  def.c

# 生成库文件的后缀名 .so
include $(BUILD_SHARED_LIBRARY)
