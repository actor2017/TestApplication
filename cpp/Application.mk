# 作用：支持多个平台

#           all         全部平台
# ARMv5     armeabi     32位，从2010年
# ARMv7     armeabi-v7a 32位，从2010年
# x86       x86         32位，从2011年
# MIPS      mips        32位，从2012年
# ARMv8     arm64-v8a   64位，从2014年
# MIPS64    mips64      64位，从2014年
# x86_64    x86_64      64位，从2014年


APP_ABI := armeabi x86



# 07 常见错误 & 添加X86支持
# 1. java.lang.UnsatisfiedLinkError:
#     Couldn't load hello: **findLibrary returned null**
#
#     解决方案：
#         * 如果处理器平台不匹配,返回的lib就是空
#             在Application.mk文件中编写APP_ABI := all
#         * 检查lib的名字是否拼写错误
#
# 2. java.lang.UnsatisfiedLinkError: Native method not found:     com.actor.hello2.MainActivity.add:(II)I
#
#     * C函数名写错了
#          检查c语言里面编写的方法名是否符合规范 Java_包名_类名_方法名(参数)
#     * 忘记加载动态链接库
#
# 3. C文件含有中文的话，要把C文件改成UTF-8的格式
