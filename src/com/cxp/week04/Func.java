package com.cxp.week04;

public class Func {

    public static String run() throws InterruptedException {
        System.out.println("方法执行中...");
        Thread.sleep(3 * 1000);
        return "方法执行结果...";
    }
}
