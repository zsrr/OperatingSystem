package com.stephen;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by StephenZhang on 2017/3/13.
 */
public class ThreadTest {
    public static void main(String[] args) {
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfoArray = mxBean.dumpAllThreads(false, false);

        for (ThreadInfo info : threadInfoArray) {
            System.out.println("Thread id: " + info.getThreadId() + " Thread name: " +
                    info.getThreadName() + " Thread state: " + info.getThreadState());
        }
    }
}
