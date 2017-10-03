package com.stephen;

/**
 * Created by StephenZhang on 2017/3/13.
 */

class SleepUtils {
    public static void sleep(int millionSeconds) {
        try {
            Thread.sleep(millionSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ThreadStateTest {

    static class TimeWaitingThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.sleep(100);
            }
        }
    }

    static class PauseThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                long current = System.currentTimeMillis();
                while (System.currentTimeMillis() - current <= 3000);
            }
        }
    }

    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Block implements Runnable {
        @Override
        public void run() {
            synchronized (Block.class) {
                while (true) {
                    SleepUtils.sleep(4000);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new TimeWaitingThread(), "TimedWaitingThread").start();
        new Thread(new PauseThread(), "PauseThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        new Thread(new Block(), "Block1").start();
        new Thread(new Block(), "Block2").start();
    }
}
