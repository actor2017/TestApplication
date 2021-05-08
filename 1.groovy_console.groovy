import groovy.transform.PackageScope

//1.黑马程序员精品教程|Java进阶教程之Gradle入门到精通
/**
 * https://www.bilibili.com/video/BV1iW411C7CV?p=1
 * 点击: Tools => Groovy Console...
 *
 * 1.gradle介绍
 * 项目管理工具:
 * 2000年: Ant
 * 2004: Maven
 * 2012: Gradle(基于 Groovy 的特定领域语言(DSL)来声明项目设置, 抛弃了基于 XML 的各种繁琐配置).面向Java应用为主.
 *         当前支持的语言: Java, Groovy, Scale, 计划未来将支持更多语言.
 *
 * 2.课程介绍
 *
 * 3.gradle的安装
 * 配置环境变量: (系统变量 -> 新建)
 * GRADLE_HOME:
 * C:\Users\actor\.gradle\wrapper\dists\gradle-6.0-all\cra2s42cvogxluqqpvbc5e9xd\gradle-6.0
 *
 * Path -> 新建 -> %GRADLE_HOME%\bin
 *
 * 验证grandle是否配置成功: cmd -> gradle -v
 *
 * 4.gradle介绍
 * idea 配置 gradle:
 * 主界面 -> Settings -> Build,Execution,Deployment -> Gradle -> OK(什么都不做...)
 *
 * idea 创建idea项目:
 * New Project -> Gradle -> Java ->
 * GroupId: com.google
 * Artifactld: 模块名   -> Next
 * Use auto-import(自动导入)
 * Use local gradle distribution(使用本地gradle)
 *
 * 5.gradle项目目录结构介绍
 * src/main/java      //正式代码
 * src/main/resources //正式配置文件
 * src/test/java      //测试代码
 * src/test/resources //测试配置文件
 * src/main/webapp    //防止页面元素: js, css, img, html...
 */

/**
 * 介绍groovy编程语言: https://www.bilibili.com/video/BV1iW411C7CV?p=6
 */
println("Hello groovy!");    //sout快捷键, ; 可省略
println "Hello groovy!"      //() 可省略

//变量
def i = 18
def s = "string"

//List
def list = ['a', 'b']
def list2 = ["a", "b"]      //和↑一致
list << 'c'                 //list 添加元素
list.add('d')               //也可以添加
println list
println list.get(2)

//Map
def map = ['k1':'v1', 'k2':'v2']
map.k3 = 'v3'               //Map 添加元素
map.put("k4", "v4")         //和↑一致
println map.get('k3')
println map.k4


/**
 * groovy中的闭包: https://www.bilibili.com/video/BV1iW411C7CV?p=7
 * 闭包: 其实是一段代码块. 在gradle中, 我们主要是把闭包当"参数"来使用.
 */
//定义一个闭包:
def b1 = {
    println "Hello 闭包!"
}
//定义一个闭包, 带参数
def b2 = {
    v -> println ("定义一个闭包, 带参数: ${v}")
}
//闭包有返回值
def b3Return = {
    param -> return param + 1
}
def b4Return = {
    it + 1    //可不写return, 最后一行结果就是返回值. it值参数
}

//定义个方法, 参数需要闭包类型
def method1(Closure c) {
    c()               //执行闭包里的代码
}
def method2Param(Closure c) {
    c("xiao ma")
}
def method3Param(int i, Closure c) {
    return c(i)
}
/**
 * Android Gradle中常见闭包
 * compileOptions {
 *     sourceCompatibility JavaVersion.VERSION_1_8
 *     targetCompatibility JavaVersion.VERSION_1_8
 * }
 */

//调用方法
b1()
println b3Return(2)         //3
println b4Return(3)         //4
method1(b1)
method2Param(b2)
println method3Param(3, { 2 * it }) //6
println method3Param(5) { 2 * it }  //10, 如果Closure是最后一个参数, 大括号放外面


/**
 * 8.gradle配置文件的介绍: https://www.bilibili.com/video/BV1iW411C7CV?p=8
 * build.gradle
 */
//Settings -> Build -> Gradle -> Service director path(jar包下载位置, 默认:C:/Users/actor/.gradle)

/**
 * 9.让gradle使用本地maven仓库: https://www.bilibili.com/video/BV1iW411C7CV?p=8
 */
//1. 配置环境变量"GRADLE_USER_HOME": D:\repository
//     Settings -> Build -> Gradle -> Service director path 变成了上方配置的目录
//2. repositories {
//      mavenLocal()    //第一行添加, 先在本地maven仓库找, 如果不配置, 只在mavenCentral()中下载?
//  }

/**
 * 10.gradle介绍
 *
 * 11.gradle开发web工程
 *      新建文件: src -> main -> webapp -> WEB-INF -> web.xml
 *      在build.gradle中添加: apply plugin: 'war'
 */

/**
 * 12.gradle工程拆分与聚合
 * allprojects {
 *      //父工程的配置, 子工程能用, javaEE项目中可这样配置
 *     group ..., version ..., apply ..., sourceCompatibility = 1.8, repositories {}, dependencies {}
 * }
 */



///////////////////////////////////////////////////////////////////////////
// https://www.bilibili.com/video/BV1DE411Z7nt?p=1
// 来自Gradle开发团队的Gradle入门教程
///////////////////////////////////////////////////////////////////////////
/**
 * https://www.bilibili.com/video/BV1DE411Z7nt?p=1
 * 1_Gradle与Groovy基础: 时间: 25:00
 */
public class Clazz1 {
    int anInt = 456

    @PackageScope   //方法默认是public, 如果要private, 需要加这个注解: 包: import groovy.transform.PackageScope
    /*public */void method1() {
        println "anInt = " + anInt
    }
}
new Clazz1().method1()          //456
new Clazz1(anInt: 123).method1()//123

List list1 = [1, 2, 3, 4, 5]
list1.findAll { it % 2 != 0 }   //返回所有奇数(包括负数)

/**
 * https://www.bilibili.com/video/BV1DE411Z7nt?p=2
 * _2Gradle构建
 * 1.生命周期
 *      Initialization  初始化
 *      Configuration   配置
 *      Execution       执行
 *
 * 2.在 Teminal 中执行: ./gradlew help
 */
//在"Gradle"中写代码!!!
//创建一个任务
task('helloworld', {//参2: 配置闭包
    println 'configure'

    doLast {
        println 'Executing task'
    }
})
//12.45
