package com.stephen;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by StephenZhang on 2017/3/28.
 */
public class AtomicIntegerArrayTest {
    static int[] array = new int[]{1, 3};
    static final AtomicIntegerArray aia = new AtomicIntegerArray(array);

    public static void main(String[] args) {
        int result = aia.addAndGet(0, 3);
        System.out.println(result);
        System.out.println(aia.get(0));
    }
}
