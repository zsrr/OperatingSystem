package com.stephen;

import static java.lang.System.out;

public class SynchronizedTest {

    public synchronized void method1() {
        out.println("Method 1 start");
        try {
            out.println("Method 1 execute");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("Method 1 end");
    }

    public synchronized void method2() {
        out.println("Method 2 start");
        try {
            out.println("Method 2 execute");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("Method 2 end");
    }

    public void method() {
        synchronized (this) {
            out.println("Start");
        }
    }

    public static void main(String[] args) {
        SynchronizedTest st = new SynchronizedTest();

        new Thread(st::method1).start();
        new Thread(st::method2).start();
    }

}
