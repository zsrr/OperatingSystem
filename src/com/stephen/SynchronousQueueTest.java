package com.stephen;

import java.util.concurrent.*;

/**
 * Created by StephenZhang on 2017/5/17.
 */
public class SynchronousQueueTest {
    private static final SynchronousQueue<String> sq = new SynchronousQueue<>();
    private static String[] data = new String[]{ "1", "2", "3", "4" };

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 4; i++) {
            final int index = i;
            service.execute(() -> {
                try {
                    System.out.println("Trying to insert " + data[index]);
                    sq.put(data[index]);
                    System.out.println(data[index] + " inserted");
                } catch (InterruptedException ignore) {
                }
            });
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 4; i++) {
            service.execute(() -> {
                try {
                    System.out.println(sq.take() + "received");
                } catch (InterruptedException ignore) {
                }
            });
        }

        service.shutdown();
    }
}
