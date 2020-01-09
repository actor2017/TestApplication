package com.actor.testapplication;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegexUnitTest {

    private static final String sMyQQ = "我的QQ是:456456我的电话是:0532214我的邮箱是:aaa123@aaa.com";

    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);

        /**
         * flags
         */
//        Pattern.CANON_EQ;//128 启用规范等价
//        Pattern.CASE_INSENSITIVE;//2 启用不区分大小写的匹配
//        Pattern.COMMENTS;//4 模式中允许空白和注释
//        Pattern.DOTALL;//32 启用 dotall 模式
//        Pattern.LITERAL;//16 启用模式的字面值解析
//        Pattern.MULTILINE;//8 启用多行模式
//        Pattern.UNICODE_CASE;//64 启用 Unicode 感知的大小写折叠
//        Pattern.UNICODE_CHARACTER_CLASS;//256
//        Pattern.UNIX_LINES;//1 启用 Unix 行模式

        /**
         * 1.Pattern(模式器对象) 静态方法
         */
        println("编号   返回        方法                  说明");
        println("1.1. Pattern   Pattern.compile(int flags);");
        println("1.1. Pattern   Pattern.compile(String regex, int flags);");
        Pattern patternNum = Pattern.compile("\\d+");//将给定的正则表达式编译到模式中
        Pattern patternNum1 = Pattern.compile("\\d+", 0);//将给定的正则表达式编译到具有给定标志的模式中, flags: 匹配标志

        println("1.2. boolean   Pattern.matches(String regex, CharSequence input);");
        boolean matches = Pattern.matches("a*b", "aaaaab");//true
        boolean matches1 = Pattern.matches("\\d+", "2223");//true,编译给定正则表达式并尝试将给定输入与其匹配
        boolean matches2 = Pattern.matches("\\d+", "2223a");//false

        println("1.3. String    Pattern.quote(String s);   //将任何字符串(包括正则表达式)都转换成字符串常量，不具有任何匹配功能");
        println("Pattern.quote(sss): " + Pattern.quote("sss"));//\Qsss\E
        testQuote();
        println();

        /**
         * 2.Pattern 普通方法
         */
        println("2.1. Matcher   pattern.matcher(CharSequence input);");
        Matcher matcherNum = patternNum.matcher("22bb23cc");//创建匹配给定输入与此模式的匹配器

        println("2.2. int       pattern.flags();          //返回此模式的匹配标志");
        int flags = patternNum.flags();//返回此模式的匹配标志

        println("2.3. String    pattern.pattern();          //regex, 返回在其中编译过此模式的正则表达式");
        String regex = patternNum.pattern();

        println("2.4. String[]  pattern.split(CharSequence input);          //切割");
        println("2.4. String[]  pattern.split(CharSequence input, int limit);//参2 limit: 结果阈值");
        String[] splits = patternNum.split(sMyQQ);//切割
        println("patternNum.split:" + Arrays.toString(splits));//打印数字
        println();
        Pattern patternColon = Pattern.compile(":");//冒号
        println("patternColon.split, limit=0(默认):" + Arrays.toString(patternColon.split("boo:and:foo")));//[boo, and, foo]
        println("patternColon.split, limit=2:" + Arrays.toString(patternColon.split("boo:and:foo", 2)));   //[boo, and:foo]
        println("patternColon.split, limit=5:" + Arrays.toString(patternColon.split("boo:and:foo", 5)));   //[boo, and, foo]
        println("patternColon.split, limit=-2:" + Arrays.toString(patternColon.split("boo:and:foo", -2))); //[boo, and, foo]
        Pattern compileO = Pattern.compile("o");//o
        println("compileO.split, limit=0(默认):" + Arrays.toString(compileO.split("boo:and:foo")));//[b, , :and:f]
        println("compileO.split, limit=5:" + Arrays.toString(compileO.split("boo:and:foo", 5)));   //[b, , :and:f, , ]
        println("compileO.split, limit=-2:" + Arrays.toString(compileO.split("boo:and:foo", -2))); //[b, , :and:f, , ]
        println("compileO.split, limit=0:" + Arrays.toString(compileO.split("boo:and:foo", 0)));   //[b, , :and:f]
        println();

        println("2.5. String    pattern.toString();          //regex 返回在其中编译过此模式的正则表达式");
        String regex1 = patternNum.toString();



        /**
         * 3.Matcher(匹配器对象) 静态方法
         */
        println("3.1. String    Matcher.quoteReplacement();          //在 '$' 和 '\\' 前面加上 '\\'");
        String str = "123$78\\9";
        println("Matcher.quoteReplacement(" + str + "): " + Matcher.quoteReplacement(str));//123\$78\\9
        println(str.replaceAll("3$7", "5$5"));//123$78\9, 没替换
        println(str.replaceAll(Matcher.quoteReplacement("3$7"), Matcher.quoteReplacement("5$5")));//125$58\9能替换
        println();

        /**
         * 4.Matcher 普通方法
         */
        println("4.1. Matcher   matcher.appendReplacement(StringBuffer sb, String replacement);//将查找到的第一个替换, 可一直查找替换(replaceAll)");
        StringBuffer sb = new StringBuffer();
        if (matcherNum.find()) {//必须find后才能替换
            Matcher matcher = matcherNum.appendReplacement(sb, "replacement");
        }
        testAppendReplacement(sb);
        println();

        println("4.2. StringBuffer  matcher.appendTail(StringBuffer sb);//把最后一次匹配到内容之后的字符串追加到StringBuffer中(见replaceAll)");
        testAppendTail(sb);
        println();

        println("4.3. int       matcher.end();                  //返回匹配到的子字符串的最后一个字符在字符串中的索引位置");
        println("4.3. int       matcher.end(int group);");      //返回第'group'组匹配到的子字符串的最后一个字符在字符串中的索引位置
        println("4.3. int       matcher.end(String name);");
        //matches(),lookingAt(),find()其中任意一个方法返回true匹配操作成功,才可以使用(每次执行匹配操作后start(),end(),group()三个方法的值都会改变)
        testGroup$Start$End();
        println();

        println("4.4. boolean   matcher.find();       //对字符串进行匹配");
        println("4.4. boolean   matcher.find(int start);//start: 在String的length的哪个位置开始匹配");
        boolean b1 = matcherNum.find();
        boolean b2 = matcherNum.find(6);

        println("4.5. String    matcher.group();       //返回匹配到的子字符串组(可多个组)");
        println("4.5. String    matcher.group(int group);");
        println("4.5. String    matcher.group(String name);");
        //matches(),lookingAt(),find()其中任意一个方法返回true时,匹配操作成功,才可以使用(每次执行匹配操作后start(),end(),group()三个方法的值都会改变)
        /** @see #testGroup$Start$End() */

        println("4.6. int       matcher.groupCount();       //返回匹配到的子字符串组个数, 可以理解为()的个数");
        /** @see #testGroup$Start$End() */

        println("4.7. boolean   matcher.hasAnchoringBounds();//查询此匹配器区域界限的定位");
        boolean b3 = matcherNum.hasAnchoringBounds();/** @see Matcher#useAnchoringBounds(boolean) */

        println("4.8. boolean   matcher.hasTransparentBounds();");
        boolean b4 = matcherNum.hasTransparentBounds();/** @see Matcher#useTransparentBounds(boolean) */

        println("4.9. boolean   matcher.hitEnd();           //?");
        boolean b5 = matcherNum.hitEnd();

        println("4.10. boolean  matcher.lookingAt();        //对字符串的'前面'进行匹配, 只有匹配到的字符串在最前面才返回true");
        boolean b6 = matcherNum.lookingAt();
//        boolean b = Pattern.compile("\\d+").matcher("22bb23").lookingAt();//true,因为\d+匹配到了前面的22

        println("4.11. boolean  matcher.matches();");
        boolean b7 = matcherNum.matches();

        println("4.12. Pattern  matcher.pattern();");
        Pattern pattern = matcherNum.pattern();

        println("4.13. Matcher  matcher.region(int start, int end); //设置此匹配器区域的索引限制");
//        Matcher region = matcherNum.region(0, 1);

        println("4.14. int      matcher.regionEnd();            //返回上方region()设置的start");
        int i1 = matcherNum.regionEnd();

        println("4.15. int      matcher.regionStart();          //返回上方region()设置的end");
        int i2 = matcherNum.regionStart();

        println("4.16. String   matcher.replaceFirst(String replacement);//将第一个匹配到的替换成replacement");
//        String replacement = matcherNum.replaceFirst("replacement");

        println("4.17. boolean  matcher.requireEnd();          //只有匹配成功有意义,为true,表示更多输入数据可能会导致失败,false 输入更多,可能改变匹配文本,但不会失败");
        boolean b8 = matcherNum.requireEnd();

        println("4.18. Matcher  matcher.reset();                //重新开始匹配");
        println("4.18. Matcher  matcher.reset(String input);");
//        Matcher reset = matcherNum.reset();
//        Matcher input = matcherNum.reset("input");

        println("4.19. int      matcher.start();                //现在已经匹配到的group的开始位置");//返回匹配到的子字符串在字符串中的索引位置
        println("4.19. int      matcher.start(int group);");
        println("4.19. int      matcher.start(String name);");
        //matches(),lookingAt(),find()其中任意一个方法返回true时,匹配操作成功,才可以使用(每次执行匹配操作后start(),end(),group()三个方法的值都会改变)
//        int start = matcherNum.start();
//        int start1 = matcherNum.start(1);
//        int name2 = matcherNum.start("name");
        /** @see #testGroup$Start$End() */

        println("4.20. MatchResult matcher.toMatchResult();     //匹配结果");
//        MatchResult matchResult = matcherNum.toMatchResult();

        println("4.21. String   matcher.toString();");
//        String s2 = matcherNum.toString();

        println("4.22. Matcher  matcher.useAnchoringBounds(boolean);//true使用定位界限，此匹配器区域的边界与定位点（如 ^ 和 $）匹配");
//        Matcher matcher2 = matcherNum.useAnchoringBounds(true);

        println("4.23. Matcher  matcher.usePattern(Pattern newPattern); //设置新的pattern");
//        Matcher matcher3 = matcherNum.usePattern(pattern);

        println("4.24. Matcher  matcher.useTransparentBounds(boolean);  //false允许正则regex=\"\\\\bcar\\\\b\"中忽视一个类似\\b的东西");
//        Matcher matcher4 = matcherNum.useTransparentBounds(true);
    }

    //测试 quote
    private void testQuote() {
        List<String> list = new ArrayList<>();
        list.add("Hi123HitianHiyan");//position = 0
        list.add("Hiaaa");           //1
        list.add("aaa");
        list.add("Hi\\/*");
        list.add("Hi\\w*123");
        list.add("Hi\\w*");          //5

        String regex = "Hi\\w*";
        for (String str : list){
            if(Pattern.matches(regex, str)){//正则匹配
                System.out.println(str);//position: 0, 1
            }
        }
        String quote = Pattern.quote(regex);
        //\Q 代表字面内容的开始
        //\E 代表字面内容的结束
        System.out.println("quote: " + quote);//\QHi\w*\E
        for (String str : list){
            if(Pattern.matches(quote, str)){
                System.out.println(str);//position: 5
            }
        }
    }

    //测试 appendReplacement
    private void testAppendReplacement(StringBuffer sb) {
        sb = new StringBuffer();
        String input = "The java book is java program book c";
        Pattern pattern = Pattern.compile("java");
        Matcher matcher = pattern.matcher(input);
        println("matcher.appendReplacement(sb, replacement): pattern = '%s', matcher = '%s'", pattern.pattern(), input);
        if(matcher.find()) {
            matcher.appendReplacement(sb, "python");
            println(sb);//The python
        }
        if(matcher.find()) {
            matcher.appendReplacement(sb, "python");
            println(sb);//The python book is python
        }
        if(matcher.find()) {//false
            matcher.appendReplacement(sb, "python");
        }
        println(sb);//The python book is python, ★注意★: 这儿没有把最后的' program book c'加上

        //replaceAll调用的是 appendReplacement()方法 + appendTail()方法
        println(matcher.replaceAll("c++"));//The c++ book is c++ program book c
    }

    //测试 appendTail
    private void testAppendTail(StringBuffer sb) {
        sb = new StringBuffer();
        String input = "The java book is java program book c";
        Pattern pattern = Pattern.compile("java");
        Matcher matcher = pattern.matcher(input);
        println("matcher.appendTail(sb): pattern = '%s', matcher = '%s'", pattern.pattern(), input);
        if (matcher.find()) {
            matcher.appendReplacement(sb, "python");
        }
        println(sb);//The python
        matcher.appendTail(sb);
        println(sb);//The python book is java program book c
        println("---------sb 和 matcher 重置再试---------------");
        sb = new StringBuffer();
        matcher.reset(input);
        while (matcher.find()) {
            matcher.appendReplacement(sb, "python");
            println(sb);
        }
        matcher.appendTail(sb);
        println(sb);
    }

    //测试 matcher.groupCount, group, start, end
    private void testGroup$Start$End() {
        String str = "Hello,World! in Java.";
        Pattern pattern = Pattern.compile("W(or)(ld!)");
        Matcher matcher = pattern.matcher(str);
        println("matcher.groupCount(): %d", matcher.groupCount());//2 可以理解为()的个数
        while(matcher.find()){
            println("Group 0: %s", matcher.group(0));//World!   得到第0组匹配
            println("Group 1: %s", matcher.group(1));//or       得到第一组匹配
            println("Group 2: %s", matcher.group(2));//ld!      得到第二组匹配
            println("Start 0: %d, End 0: %d", matcher.start(0), matcher.end(0));//6, 12总匹配的索引
            println("Start 1: %d, End 1: %d", matcher.start(1), matcher.end(1));//7, 9 第一组匹配的索引
            println("Start 2: %d, End 2: %d", matcher.start(2), matcher.end(2));//9, 12第二组匹配的索引
            println(str.substring(matcher.start(0), matcher.end(1)));//Wor 从总匹配开始索引到第1组匹配的结束索引之间子串
        }
    }

    private void println() {
        System.out.println();
    }
    private void println(CharSequence s) {
        System.out.println(s);
    }
    private void println(CharSequence s, Object... args) {
        System.out.printf(s.toString(), args);
        System.out.println();
    }
}