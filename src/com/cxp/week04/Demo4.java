package com.cxp.week04;

public class Demo4 {

    public static class Foo implements Runnable {
        private String result;

        @Override
        public void run() {
            synchronized (this) {
                try {
                    this.result = Func.run();
                } catch (Exception e) {
                    this.result = "方法执行出错";
                } finally {
                    this.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();
        new Thread(foo).start();
        synchronized (foo) {
            while (foo.result == null) {
                System.out.println("主线程等待中...");
                foo.wait(3 * 1000);
            }
        }
        System.out.println(foo.result);
    }
}
