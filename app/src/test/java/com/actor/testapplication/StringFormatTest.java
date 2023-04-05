package com.actor.testapplication;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 1.新字符串使用本地语言环境,制定字符串格式和参数生成格式化的新字符串
 * {@link String#format(String, Object...)}
 * {@link System.out#printf(String, Object...)}
 * {@link java.util.Formatter#format(String, Object...)}
 *
 * 2.使用指定的语言环境,制定字符串格式和参数生成格式化的字符串
 * {@link String#format(java.util.Locale, String, Object...)}
 * {@link System.out#printf(java.util.Locale, String, Object...)}
 * {@link java.util.Formatter#format(java.util.Locale, String, Object...)}
 *
 * 3.更多资料在JDK API中搜索: Formatter, 或者参考百度, 示例:
 * https://www.jianshu.com/p/e9fd92fd0951
 * https://blog.csdn.net/wufen1103/article/details/7846691
 *
 * %n$ms：n: 第几个参数(从1开始, 不是0), 可省略
 *        m: 字符串长度至少m, 如果不够在输出之前放置空格(m>0, 否则报错), $m 可省略
 *        s: 字符串
 * %n$md：d: short/int/long, 其余同上
 * %n$mf：m: 控制小数位数，如m=5.2
 *                          5: 表示总长度(包括小数点)
 *                          2: 小数点后长度=2位
 *                          输出格式为00.00
 *        f: float/double
 *
 * n$m在java代码中可省略, 在strings.xml中没试过
 */
public class StringFormatTest {

    private static final char       CHAR    = 65;//Character
    private static final byte       BYTE    = 15;
    private static final short      SHORT   = 15;
    private static final float      FLOAT   = 0.23456789F;
    private static final float      FLOAT1   = 1.23456789F;
    private static final float      FLOAT2   = 1.234500000F;
    private static final Integer    INTEGER = 15;
    private static final BigInteger BIG_INTEGER = BigInteger.valueOf(15L);

    private static final BigDecimal BIG_DECIMAL = BigDecimal.valueOf(15L);//long/double

    @Test
    public void main() {
        println(null, "换行符: %n");             //换行符
        println(null, "百分号: %%");             //%
        println(null, "布尔: %b", 3 < 5);       //true
        println(null, "字符: %c", CHAR);         //'A'

        println(null, "字符串: %s", "abcDef");   //abcDef
        println(null, "字符串: %1$8s", "abcDef");//  abcDef
        println(null, "%4$2s %3$2s %2$2s %1$2s", "a", "b", "c", "d");//" d  c  b  a"

        //格式化成整数, byte、Byte、short、Short、int、Integer、long、Long 和 BigInteger
        println(null, "byte: %d", BYTE);         //15
        println(null, "short: %d", SHORT);       //15
        println(null, "Integer: %d", INTEGER);   //15
        println(null, "BigInteger: %d", BIG_INTEGER);//15
        println(null, "int: %d", 15);            //15
        println(null, "int: %+d", 15);           //+15 (输出带符号, 正号&负号)
        println(null, "int: %-3d", 15);           //"15 "     //左对齐
        println(null, "int: %1$3d", 15);         // 15
        println(null, "int: %03d", 15);          //015        (03d: 如果整数没有3位,前面补0)
        println(null, "long: %d", 15L);

        //浮点, float、Float、double、Double 和 BigDecimal
        println(null, "BIG_DECIMAL: %f", BIG_DECIMAL);//15.000000
        println(null, "15的单精数: %f", 15F);     //15.000000
        println(null, "15的单精数: %.0f", 15F);   //15        (小数后面显示0位, 即不要小数)
        println(null, "15的单精数: %.2f", 15F);   //15.00     (小数后面显示2位)
        println(null, "千位分隔符: %,8d", 15000); //  15,000  (包括小数点, 一共8位)
        println(null, "$ (%,.2f)", 6_217.58F);   //$ (6,217.58)
        println(null, "15的双精数: %6.2f", 15D);  // 15.00
        println(null, "15的双精数: %06.2f", 15D); //015.00

        println(null, "15的16进制: %x", 15);      //f
        println(null, "15的8进制: %o", 15);       //17

        println(null, "15的十六进制浮点: %a", 15F);//0x1.ep3
        println(null, "15的指数: %e", 15F);       //1.500000e+01
        /**
         * %g: 如果整数部分=0, 小数点后默认显示6位, 后面的四舍五入
         *     如果整数部分>0, 小数点后默认显示5位
         */
        println(null, "f和e类型中较短的: %g", FLOAT);//0.234568
        println(null, "f和e类型中较短的: %g", FLOAT1);//1.23457
        println(null, "f和e类型中较短的: %g", FLOAT2);//1.23450
        println(null, "f和e类型中较短的: %.17g", FLOAT);//0.23456789553165436
        println(null, "f和e类型中较短的: %.17g", FLOAT1);//1.2345678806304932
        println(null, "f和e类型中较短的: %.17g", FLOAT2);//1.2345000505447388

        println(null, "'A'的散列码: %h", 'A');     //41       Integer.toHexString(arg.hashCode())

        println(null, "\n不同国家的千位分隔符");
        println(Locale.FRANCE, "法国: %,.2f", Math.PI * 10000);//31?415,93
        println(Locale.US,     "美国: %,.2f", Math.PI * 10000);//31,415.93
        println(Locale.UK,     "英国: %,.2f", Math.PI * 10000);//31,415.93

        //时间, Calendar 和 Date
        println(null, "\n日期与时间类型(d:日, T:时分秒, Y:年, m:月, e:日, 还有其它)");
        println(null, "今天多少号: %td", System.currentTimeMillis());//03
        println(null, "现在时间: %tT", Calendar.getInstance());//现在时间: 20:48:30

        Calendar c = new GregorianCalendar(1995, Calendar.MAY, 3);
        println(null, "年月日: %1$tY %1$tm,%1$te", c);//年月日: 1995 05,3
    }

    private void println(Locale locale, String format, Object... args) {
        if (args == null || args.length == 0) {
            System.out.println(format);
            return;
        }
        if (locale == null) locale = Locale.getDefault();
        System.out.println(String.format(locale, format, args));
    }
}
