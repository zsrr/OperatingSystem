package com.stephen;

import java.util.concurrent.atomic.AtomicReference;

//用来实现自旋锁的算法
public class CLHLock {
    private final ThreadLocal<Node> pre;
    private final ThreadLocal<Node> node;
    private final AtomicReference<Node> tail = new AtomicReference<>(new Node());

    private static class Node {
        volatile boolean isLocked;
    }

    public CLHLock() {
        node = ThreadLocal.withInitial(Node::new);
        pre = ThreadLocal.withInitial(() -> null);
    }

    public void lock() {
        Node node = this.node.get();
        node.isLocked = true;
        Node pre = tail.getAndSet(node);
        this.pre.set(pre);
        while (pre.isLocked) {}
    }

    public void unlock() {
        Node node = this.node.get();
        node.isLocked = false;
        this.node.set(pre.get());
    }
}
