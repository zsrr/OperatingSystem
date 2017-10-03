package com.stephen;


/**
 * Created by StephenZhang on 2017/3/13.
 */

public class NotifyWaitTest {
    static Object lock = new Object();
    static boolean flag = true;

    static class Wait implements Runnable {
        @Override
        public void run() {
            //获取对象的锁
            synchronized (lock) {
                while (flag) { // 2
                    System.out.println("不满足相应的条件");
                    try {
                        //进入wait状态，同时释放对象的锁
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("条件满足");
            }
        }
    }

    static class Notify implements Runnable {
        @Override
        public void run() {
            //获取对象的锁
            synchronized (lock) {
                if (flag) {
                    flag = false; // 1
                    //通知改变
                    lock.notify();
                }

                System.out.println("条件已改变");
            }
        }
    }

    public static void main(String[] args) {
        Thread waitThread = new Thread(new Wait(), "Wait");
        Thread notifyThread = new Thread(new Notify(), "Notify");
        waitThread.start();
        //保证wait先执行的方式
        SleepUtils.sleep(2000);
        notifyThread.start();
    }
}
