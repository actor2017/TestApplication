package com.actor.testapplication;

/**
 * description: 测试 final
 * author     : 李大发
 * date       : 2020/4/4 on 11:29
 *
 * @version 1.0
 */
public class FinalTest {
    public static void main(String[] args) {
        System.out.println(Son.A);
    }
}

class Son extends Parent {
    static {
        System.out.println("Son init");
    }
}

class Parent {
    static {
        System.out.println("Parent init");
    }
    public static /*final*/ String A = "a";//有没有 final 的区别
}
