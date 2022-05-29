package com.cxp.week04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo5 {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static class Foo implements Runnable {
        private String result;

        @Override
        public void run() {
            lock.lock();
            try {
                this.result = Func.run();
            } catch (InterruptedException e) {
                this.result = "方法执行出错";
            } finally {
                condition.signal();
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        new Thread(foo).start();
        lock.lock();
        try {
            while (foo.result == null) {
                System.out.println("主线程等待中...");
                condition.await(3, TimeUnit.SECONDS);
            }
            System.out.println(foo.result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
