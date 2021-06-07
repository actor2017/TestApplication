# 这个文件相当于buidle.gradle, 是CMake的构建脚本, 用来构建原生库, 和src同一层级目录
# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

# https://blog.csdn.net/b2259909/article/details/58591898
# Android Studio NDK CMake 指定so输出路径以及生成多个so的案例与总结_zhangbh的专栏-CSDN博客.html

# 编译本地库时我们需要的最小的cmake版本
cmake_minimum_required(VERSION 3.4.1)

# 1.设置生成的so动态库最后输出的路径, 一定要在add_library之前设置.
#   生成的so库在和CMakeLists.txt同级目录下的 jniLibs 文件夹下
#   ABI: 各种平台名称, 例: arm64-v8a, x86...
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/jniLibs/${ANDROID_ABI})

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds it for you.
# Gradle automatically packages shared libraries with your APK.

# 如果想打包第二个.so文件, 把这个add_library再写一个
add_library( # Sets the name of the library.
        #[[
            1.c/cpp代码将要编译成为so库的名称(libnative-lib.so)
            2.java代码加载库文件要用这个名称: System.loadLibrary("native-lib");
            3.这个名称和 native-lib.c 没有任何关系!
        ]]
        native-lib

        # Sets the library as a shared library.
        # 指定了生成库的类型：SHARED表示动态库.so，STATIC表示静态库.a
        SHARED

        # Provides a relative path to your source file(s).
        # Associated headers in the same location as their source
        # file are automatically included.
        # 2.生成的.so文件对应的.c/.cpp文件, src/main/cpp/native-lib.c
        src/main/cpp/plasma.c
        src/main/cpp/Log.c
        src/main/cpp/native-lib.c
        src/main/cpp/const_utils.cpp
        src/main/cpp/c-call-java.c)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because system libraries are included in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

# 添加一些我们在编译本地库的时候需要依赖的一些库，这里是用来打log的库
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in the
# build script, prebuilt third-party libraries, or system libraries.

# 关联自己生成的库和一些第三方库或者系统库
# 如果想打包第二个.so文件, 把这个target_link_libraries再写一个
target_link_libraries( # Specifies the target library.
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
