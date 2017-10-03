package com.stephen;

/**
 * Created by StephenZhang on 2017/3/13.
 */

public class InterruptTest {
    static class SleepThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.sleep(3000);
            }
        }
    }

    static class NormalThread implements Runnable {
        @Override
        public void run() {
            while (true);
        }
    }

    public static void main(String[] args) {
        Thread sleepThread = new Thread(new SleepThread(), "Sleep");
        Thread normalThread = new Thread(new NormalThread(), "Normal");

        sleepThread.start();
        normalThread.start();

        SleepUtils.sleep(2000);

        sleepThread.interrupt();
        normalThread.interrupt();

        System.out.println("Sleep thread interrupted is " + sleepThread.isInterrupted());
        System.out.println("Normal thread interrupted is " + normalThread.isInterrupted());
    }
}
