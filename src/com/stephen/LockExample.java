package com.stephen;


public class LockExample {
    private int a;

    public synchronized void increase() {
        a++;
    }

    public synchronized void get() {
        int i = a;
    }

    public static void main(String[] args) {

    }
}
