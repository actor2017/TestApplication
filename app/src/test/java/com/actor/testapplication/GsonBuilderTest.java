package com.actor.testapplication;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.DateFormat;

/**
 * description: GsonBuilder属性
 *
 * date       : 2020/7/30 on 16:11
 * @version 1.0
 */
public class GsonBuilderTest {

    public static void main(String[] args) {

        /**
         * https://www.jianshu.com/p/923a9fe78108
         * transient, static 关键字: (默认)将使得该字段既不能序列化也不能反序列化, 可通过 excludeFieldsWithModifiers 修改策略
         *  public transient String name;
         *  public static String address;
         *
         * @Expose(serialize = false, deserialize = false)
         *  指定某个字段不参与序列化/反序列化
         *
         * @SerializedName("class")
         * @SerializedName(value = "class", alternate = "class2")
         * @SerializedName(value = "class", alternate={"name2", "name3"})
         * String clazz;
         *  参1: 将clazz序列化成class, 将class反序列化成clazz
         *  参2: 仅仅作为反序列化中的代选项(如果json中有class2, 就将class2反序列化成clazz)
         *       如果有多个域匹配一个属性，Gson会使用最后一个遇到的域
         *
         * 某一"字段或类型"在这一特定的版本号之后才"启用": setVersion() >=这个值正常
         *  @Since(1.0) private String password;
         *  @Since(1.1) private Address address;
         *
         * 某一"字段或类型"在该版本号之前才存在, 之后被'弃用'了: setVersion() < 这个值正常(没有=)
         *  @Until(1.0) private String emailAddress;
         *  @Until(1.1) private String password;
         *
         * https://www.jianshu.com/p/3108f1e44155
         * @JsonAdapter(UserTypeAdapter.class), 优先级比 registerTypeAdapter() 的优先级更高
         */
        Gson gson = new GsonBuilder()
                /**
                 * https://www.jianshu.com/p/8d00a2c3198c
                 * 反序列的时候过滤指定 Class/字段, 让这些不参与反序列化
                 */
                .addDeserializationExclusionStrategy((ExclusionStrategy) null)

                /**
                 * 序列化的时候过滤指定字段(ExclusionStrategy: 排除策略), 让这些不参与序列化
                 */
                .addSerializationExclusionStrategy((ExclusionStrategy) null)

                .disableInnerClassSerialization()//禁止序列化内部类, 默认true可以序列化
                .disableHtmlEscaping()//默认为true, Gson可以把HTML转义

                /**
                 * 在 序列化/反序列化 过程中排除哪些被特定修饰词修饰的成员变量
                 * 默认: Modifier.TRANSIENT | Modifier.STATIC
                 * Modifier: 修饰语
                 */
                .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)

                /**
                 * 排除所有没有被@Expose注解的成员变量(强制你的应用程序模型必须每处都用@Expose注解)
                 */
                .excludeFieldsWithoutExposeAnnotation()//默认false

                //支持Map的key为复杂对象的形式, 能'序列化'Map<Object, ...> 的key是复杂类型, 默认调key的toString
                .enableComplexMapKeySerialization()

                //生成JS中不可执行的 Json（在前缀插入一些特殊字符）
                .generateNonExecutableJson()

                /**
                 * 自定义序列化/反序列化
                 */
                .registerTypeAdapter((Type) int.class, (TypeAdapter<Integer>) null)//自定义序列化/反序列化
                .registerTypeAdapter((Type) Integer.class, (JsonSerializer<Integer>) null)//"list.add"自定义序列化
                .registerTypeAdapter((Type) String.class, (JsonDeserializer<String>) null)//"list.add"自定义反序列化
                //"map.put"创建没有定义空参构造函数的类的实例。可用于库类(如JDK类)或没有源代码的第三方库
                .registerTypeAdapter((Type) Object.class, (InstanceCreator<Object>) null)

                // "list.add"注册Factory, 返回 TypeAdapter
                .registerTypeAdapterFactory((TypeAdapterFactory) null)

                /**
                 * registerTypeAdapter 与 registerTypeHierarchyAdapter 的区别：
                 * registerTypeAdapter   registerTypeHierarchyAdapter
                 * 支持泛型  是               否
                 * 支持继承  否               是      (传 Number 的 typeAdapter, 可处理Integer, Long...)
                 */
                .registerTypeHierarchyAdapter((Class<?> /*baseType*/) null, (TypeAdapter<Object>) null)
                .registerTypeHierarchyAdapter((Class<?> /*baseType*/) null, (JsonSerializer<Object>) null)
                .registerTypeHierarchyAdapter((Class<?> /*baseType*/) null, (JsonDeserializer<Object>) null)

                //Long类型的"序列化"类型
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)

                /**
                 * https://www.jianshu.com/p/995c47f52543
                 * 仁慈的, "反序列化"JSON的过程中拥有更大的容忍度, 默认false
                 * 默认情况下，Gson是严格的，只接受RFC 4627指定的JSON。此选项使解析器在接受的内容中更加自由
                 * 造成原因:
                 * Gson串不标准，甚至是返回乱码，这些都需要后台去处理
                 * 不知名字符
                 */
                .setLenient()

                /**
                 * 序列化/反序列化 策略, 一下策略都可反序列化
                 * public class UserNaming {
                 *     String Name;
                 *     int _ageOfDeveloper;
                 *     String email_of_developer;
                 *     boolean isDeveloper;
                 * }
                 * IDENTITY: 默认值.
                 *           在序列化一个对象时，IDENTITY域命名策略将会使用和Java模型完全一样的名称。
                 *           不论你使用什么样的命名标准设置你的Java模型，JSON会使用相同的
                 *           UserNaming user = new UserNaming("Norman", "norman@futurestud.io", true, 26);
                 *           String usersJson = gson.toJson(user);
                 *           结果完全正常:
                 *              {"Name":"Norman", "_ageOfDeveloper":26,
                 *              "email_of_developer":"norman@futurestud.io", "isDeveloper":true}
                 *
                 * LOWER_CASE_WITH_UNDERSCORES:
                 *           按照大写字母分离每一个属性名称，并且使用一个对应的小写字母和一个'_'符号代替, 结果:
                 *              {"name":"Norman", "_age_of_developer":26,
                 *              "email_of_developer":"norman@futurestud.io", "is_developer": true}
                 *
                 * LOWER_CASE_WITH_DASHES:
                 *           按照大写字母分离每一个属性名称，并且使用一个对应的小写字母和一个'-'符号代替, 结果:
                 *              {"name": "Norman", "_age-of-developer": 26,
                 *              "email_of_developer": "norman@futurestud.io", "is-developer": true}
                 *
                 * LOWER_CASE_WITH_DOTS:
                 *           按照大写字母分离每一个属性名称，并且使用一个对应的小写字母和一个'.'符号代替, 结果:
                 *              {"name":"Norman", "_age.of.developer":26,
                 *              "email_of_developer":"norman@futurestud.io", "is.developer":true}
                 *
                 * UPPER_CAMEL_CASE: 第一个字母大写, 结果:
                 *              {"Name": "Norman", "_AgeOfDeveloper": 26,
                 *              "Email_of_developer": "norman@futurestud.io", "IsDeveloper": true}
                 *
                 * UPPER_CAMEL_CASE_WITH_SPACES:
                 *           按照大写字母分离每一个属性名称, 每个单词首字母大写 & 单词以' '隔开, 结果:
                 *              {"Name": "Norman", "_Age Of Developer": 26,
                 *              "Email_of_developer": "norman@futurestud.io", "Is Developer": true}
                 *
                 * FieldNamingPolicy implements FieldNamingStrategy
                 */
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)//@SerializedName 优先于 FieldNamingPolicy

                // https://www.jianshu.com/p/3cad3fd0946d
                // 已有的 FieldNamingPolicy 策略以及 @SerializedName 可能还不能满足你的用例的需求, 可自定义
                // 注意: Gson仅仅只能接受一种策略（Strategy）。
                .setFieldNamingStrategy((FieldNamingStrategy) null)


                /**
                 * https://www.jianshu.com/p/8d00a2c3198c
                 * 某种Class or Field是否不参与序列化/反序列化. 排除策略, 比transient和@Expose更牛逼的排除策略
                 */
                .setExclusionStrategies((ExclusionStrategy/*...*/[]) null)

                /**
                 * 序列化/反序列化 日期格式: java.util.Date, java.sql.Timestamp, java.sql.Date
                 * public Date birthDay;
                 *
                 * java.text.DateFormat: https://blog.csdn.net/sinat_41233888/article/details/86691814
                 * DateFormat.LONG: 格式化样式较长, 如 May 12，2016 或 11:15:32am
                 */
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setDateFormat(DateFormat.LONG) //May 12，2016 或 11:15:32am ??
                .setDateFormat(DateFormat.LONG, DateFormat.LONG) //2019年1月28日 CST 下午6:02:49 ??

                /**
                 * https://www.jianshu.com/p/03dcbda9ae95
                 * 可序列化特殊的值:
                 * @see Float.NEGATIVE_INFINITY
                 * @see Float.POSITIVE_INFINITY
                 * @see Float.NaN
                 * @see Double.NEGATIVE_INFINITY
                 * @see Double.POSITIVE_INFINITY
                 * @see Double.NaN
                 */
                .serializeSpecialFloatingPointValues()
                .serializeNulls()//序列化value=null的字段, 默认false
                .setPrettyPrinting()//漂亮格式, 一般用于输出查看

                /**
                 * setVersion(1.0D): 设置当前版本, 激活 @Since 注解以及 @Until
                 *
                 * 某一"字段或类型"在这一特定的版本号之后才"启用": setVersion() >=这个值正常
                 * @Since(1.0) private String password;
                 * @Since(1.1) private Address address; //将被忽略(不参与序列化/反序列化)
                 *
                 * 某一"字段或类型"在该版本号之前才存在, 之后被'弃用'了: setVersion() < 这个值正常(没有=)
                 * @Until(1.0) private String emailAddress; //将被忽略(不参与序列化/反序列化)
                 * @Until(1.1) private String password;
                 */
                .setVersion(1.0D)

                .create();
    }
}
