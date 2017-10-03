package com.stephen;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by StephenZhang on 2017/6/22.
 */
public class CASCounter {
    private AtomicInteger counter = new AtomicInteger(0);

    public int getValue() {
        return counter.get();
    }

    public void increase() {
        int v;
        do {
            v = counter.get();
        } while (!counter.compareAndSet(v, v + 1));
    }
}
