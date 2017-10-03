package com.stephen;

/**
 * Created by StephenZhang on 2017/3/13.
 */
public class BooleanFlagThread {
    static class CounterRunnable implements Runnable {
        long i = 0L;
        private volatile boolean isCancelled = false;

        public void cancel() {
            isCancelled = true;
        }

        @Override
        public void run() {
            while (!isCancelled && !Thread.currentThread().isInterrupted()) {
                i++;
            }
        }
    }

    public static void main(String[] args) {
        CounterRunnable cr = new CounterRunnable();
        Thread countThread = new Thread(cr, "Count");
        countThread.start();

        SleepUtils.sleep(2000);
        cr.cancel();

        System.out.println("" + cr.i);
    }
}
