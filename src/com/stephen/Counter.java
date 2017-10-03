package com.stephen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger i = new AtomicInteger(0);
    private AtomicInteger j = new AtomicInteger(0);

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>(1000);
        Counter counter = new Counter();

        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(counter::safeCount));
        }

        for (Thread t : threads)
            t.start();

        while (true) {
            int count = counter.i.get();
            if (count == 1000) {
                System.out.println("Done");
                break;
            }
        }
    }

    //线程安全的
    private void safeCount() {
        while (true) {
            int i = this.i.get();
            boolean whetherISuccess = this.i.compareAndSet(i, ++i);
            int j = this.j.get();
            boolean whetherJSuccess = this.j.compareAndSet(j, ++j);

            if (whetherISuccess) {
                break;
            }
        }
    }

}
