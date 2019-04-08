package com.wzb.visual.test;

/**
 * @author Satsuki
 * @time 2019/4/2 16:55
 * @description:
 */
public class SplitTest {
    public static void main(String[] args) {
        String a = "a.jpg";
        System.out.println(a.split("\\.")[1]);
        for (String b : a.split("\\.")) {
//            System.out.println(a.split("."));
            System.out.println(b);
        }

    }
}
