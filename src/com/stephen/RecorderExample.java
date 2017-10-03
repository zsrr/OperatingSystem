package com.stephen;

public class RecorderExample {
    private boolean flag = false;
    private int i = 0;

    public synchronized void write() {
        i = 1;
        flag = true;
    }

    public synchronized void read() {
        if (flag) {
            int a = i * i;
        }
    }

    public static void main(String[] args) {
        RecorderExample re = new RecorderExample();
        new Thread(re::write).start();
        new Thread(re::read).start();
    }
}
