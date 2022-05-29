package com.cxp.week04;

import java.util.concurrent.*;

public class Demo1 {

    public static class CB implements Callable<String> {

        @Override
        public String call() throws Exception {
            return Func.run();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            Future<String> future = executorService.submit(new CB());
            System.out.println(future.get());
        } finally {
            executorService.shutdown();
        }
    }
}
