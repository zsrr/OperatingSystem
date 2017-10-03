package com.stephen;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * Created by StephenZhang on 2017/3/14.
 */

public class ConnectionPool {
    private final LinkedList<Connection> connections = new LinkedList<>();

    public ConnectionPool(int initSize) {
        if (initSize <= 0)
            throw new IllegalArgumentException("Init size must > 0");
        for (int i = 0; i < initSize; i++) {
            connections.addLast(ConnectionDriver.getConnection());
        }
    }

    public void releaseConnection(Connection connection) {
        synchronized (connections) {
            connections.addLast(connection);
            connections.notifyAll();
        }
    }

    public Connection getConnection(long millionSeconds) throws InterruptedException {
        synchronized (connections) {
            if (millionSeconds <= 0) {
                while (connections.isEmpty()) {
                    connections.wait();
                }
                return connections.removeFirst();
            } else {
                long future = System.currentTimeMillis() + millionSeconds;
                long remaining = millionSeconds;

                while (connections.isEmpty() && remaining > 0) {
                    connections.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }

                Connection connection = null;
                if (!connections.isEmpty()) {
                    connection = connections.removeFirst();
                }

                return connection;
            }
        }
    }
}