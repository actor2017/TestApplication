package com.actor.testapplication.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description: 类的描述
 *
 * @author : 李大发
 * date       : 2020/7/30 on 18:13
 * @version 1.0
 *
 * TODO: 2020/7/30 未完成
 * @Retention(RetentionPolicy.RUNTIME)
 * @Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
 * public @interface JSONField {
 *     /**
 *      * config encode/decode ordinal
 *      * @since 1.1.42
 *      * @return
 *      * /
 *      int ordinal()default 0;
 *
 *      String name()default "";
 *
 *       String format()default "";
 *
 *       boolean serialize()default true;
 *
 *       boolean deserialize()default true;
 *
 *       SerializerFeature[]serialzeFeatures()default {};
 *
 *       Feature[]parseFeatures()default {};
 *
 *       String label()default "";
 * }
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GsonField {
    String dateSeriseFormat() default "yyyy-MM-dd HH:mm:ss";
    String dateDeSeriseFormat() default "yyyy-MM-dd HH:mm:ss";
}
