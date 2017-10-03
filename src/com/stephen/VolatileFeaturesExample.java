package com.stephen;


public class VolatileFeaturesExample {
    private int a;

    public synchronized void setA(int a) {
        this.a = a;
    }

    public synchronized int getA() {
        return a;
    }
}
