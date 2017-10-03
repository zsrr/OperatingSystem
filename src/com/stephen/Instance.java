package com.stephen;

public class Instance {
    private static class InstanceHolder {
        private static Instance instance = new Instance();
    }

    public static Instance getInstance() {
        return InstanceHolder.instance;
    }
}
