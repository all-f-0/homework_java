package com.cxp.week04;

public class Demo2 {

    public static class Foo implements Runnable {
        private String result;

        @Override
        public void run() {
            try {
                this.result = Func.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();
        Thread thread = new Thread(foo);
        thread.start();
        thread.join();
        System.out.println(foo.result);
    }
}
