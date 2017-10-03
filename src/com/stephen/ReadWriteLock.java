package com.stephen;

// 用wait/notify来实现一个简单的读写锁
public class ReadWriteLock {

    private static class ReadWriteMutex {
        int flag = 0;
    }

    private final ReadWriteMutex mutex = new ReadWriteMutex();

    public void acquireReadLock() throws InterruptedException {
        synchronized (mutex) {
            while (mutex.flag == -1) {
                wait();
            }
            mutex.flag++;
        }
    }

    public void releaseReadLock() {
        synchronized (mutex) {
            if (mutex.flag <= 0) {
                throw new IllegalStateException();
            }
            mutex.flag--;
            notifyAll();
        }
    }

    public void acquireWriteLock() throws InterruptedException {
        synchronized (mutex) {
            while (mutex.flag != 0) {
                wait();
                mutex.flag = -1;
            }
        }
    }

    public void releaseWriteLock() {
        synchronized (mutex) {
            if (mutex.flag != -1) {
                throw new IllegalStateException();
            }
            mutex.flag = 0;
            notifyAll();
        }
    }

}
