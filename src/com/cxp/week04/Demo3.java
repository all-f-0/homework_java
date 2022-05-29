package com.cxp.week04;

import java.util.concurrent.CountDownLatch;

public class Demo3 {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static class Foo implements Runnable {
        private String result;

        @Override
        public void run() {
            try {
                this.result = Func.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();
        new Thread(foo).start();
        countDownLatch.await();
        System.out.println(foo.result);
    }
}
