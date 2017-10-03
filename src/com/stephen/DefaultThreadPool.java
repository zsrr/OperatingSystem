package com.stephen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by StephenZhang on 2017/3/15.
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    private static final int MAX_WORKERS = 10;
    private static final int MIN_WORKERS = 1;
    private static final int DEFAULT_WORKERS = 5;

    private final AtomicInteger threadNum = new AtomicInteger(0);
    private int workersNum = DEFAULT_WORKERS;

    private final LinkedList<Job> jobs = new LinkedList<>();
    //保证各个线程的同步
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());

    public DefaultThreadPool(int initNum) {
        workersNum = initNum > MAX_WORKERS ? MAX_WORKERS : initNum < MIN_WORKERS ? MIN_WORKERS : initNum;
        initializeWorkers(DEFAULT_WORKERS);
    }

    public DefaultThreadPool() {
        initializeWorkers(DEFAULT_WORKERS);
    }

    @Override
    public void execute(Job job) {
        synchronized (jobs) {
            jobs.addLast(job);
            //此时用notify比较好，保证有线程被唤醒
            jobs.notify();
        }
    }

    @Override
    public void shutdown() {
        synchronized (jobs) {
            for (Worker worker : workers) {
                worker.shutDown();
            }
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            if (num + workersNum > MAX_WORKERS) {
                throw new IllegalArgumentException("Num is too big!");
            }

            initializeWorkers(num);
            workersNum += num;
        }
    }

    @Override
    public void removeWorkers(int num) {
        synchronized (jobs) {
            if (num > workersNum) {
                throw new IllegalArgumentException("Num is too big");
            }

            List<Worker> deferredRemovingWorkers = new ArrayList<>(num);
            for (int i = 0; i < num; i++) {
                deferredRemovingWorkers.add(workers.get(i));
            }

            for (Worker worker : deferredRemovingWorkers) {
                worker.shutDown();
            }

            workers.removeAll(deferredRemovingWorkers);
            workersNum -= num;
        }
    }

    public int getJobSize() {
        return jobs.size();
    }

    private void initializeWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    private class Worker implements Runnable {
        //用此标记来关闭整个线程
        private volatile boolean isShutDown = false;

        void shutDown() {
            isShutDown = true;
        }

        @Override
        public void run() {
            while (!isShutDown) {
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            shutDown();
                        }
                    }

                    if (!jobs.isEmpty()) {
                        Job job = jobs.removeFirst();
                        job.run();
                    }
                }
            }
        }
    }
}
