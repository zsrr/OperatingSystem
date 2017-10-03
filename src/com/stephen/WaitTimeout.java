package com.stephen;

/**
 * Created by StephenZhang on 2017/3/14.
 */
public class WaitTimeout {
    Object obj;

    public synchronized Object getObject(long millionSeconds) throws InterruptedException {
        long future = System.currentTimeMillis() + millionSeconds;
        long remainingTime = millionSeconds;
        while (obj == null && remainingTime > 0) {
            wait(remainingTime);
            remainingTime = future - System.currentTimeMillis();
        }

        return obj;
    }

    public static void main(String[] args) {
        WaitTimeout wt = new WaitTimeout();

    }
}
