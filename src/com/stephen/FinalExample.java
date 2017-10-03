package com.stephen;

public class FinalExample {
    final int[] finalArrayReference;
    static FinalExample fe;

    public FinalExample() {
        finalArrayReference = new int[10];
        finalArrayReference[0] = 1;
    }

    public static void writer() {
        fe = new FinalExample();
    }
}
