package com.stephen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StephenZhang on 2017/3/15.
 */
public class WaitingTest {

    private final static List<String> testObj = new ArrayList<>();

    static class WaitingThread implements Runnable {
        private volatile boolean isCancelled = false;

        @Override
        public void run() {
            while (!isCancelled) {
                synchronized (testObj) {
                    while (testObj.isEmpty()) {
                        System.out.println("Waiting");
                        try {
                            testObj.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
            System.out.println("Runnable cancelled");
        }

        void cancel() {
            isCancelled = true;
        }
    }

    static void addInstance() {
        synchronized (testObj) {
            testObj.add("This is a test");
            testObj.notifyAll();
        }
    }

    public static void main(String[] args) {
        WaitingThread wt = new WaitingThread();
        new Thread(wt, "Waiting thread").start();
        SleepUtils.sleep(2000);
        wt.cancel();
        addInstance();
    }

}
