package com.stephen;

/**
 * Created by StephenZhang on 2017/3/15.
 */
public interface ThreadPool<Job extends Runnable> {
    void execute(Job job);
    void shutdown();
    void addWorkers(int num);
    void removeWorkers(int num);
}
