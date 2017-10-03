package com.stephen;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by StephenZhang on 2017/6/22.
 */
public class ConcurrentLinkedQueue<E> {
    private static class Node<E> {
        final E item;
        final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<>(next);
        }
    }

    private final Node<E> dummy = new Node<>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

    private void push(E item) {
        Node<E> newNode = new Node<>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();

            if (curTail == tail.get()) {
                //当前处于中间状态，此时其余线程正在修改数据结构
                if (tailNext != null) {
                    //尝试完成其他线程的工作
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    //尝试插入新节点
                    if (curTail.next.compareAndSet(null, newNode)) {
                        //推进旧节点
                        tail.compareAndSet(curTail, newNode);
                        return;
                    }
                }
            }
        }
    }
}
