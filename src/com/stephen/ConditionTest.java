package com.stephen;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by StephenZhang on 2017/3/28.
 */
public class ConditionTest {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    private static void await() {
        lock.lock();

        try {
            System.out.println("开始等待");
            //还是要重新获取到锁的
            condition.awaitNanos(1000 * 1000);
            System.out.println("等待结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void signal() {
        lock.lock();

        try {
            Thread.sleep(3000);
            System.out.println("开始通知");
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(ConditionTest::await).start();
        new Thread(ConditionTest::signal).start();
    }
}
