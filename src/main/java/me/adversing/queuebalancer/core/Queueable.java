package me.adversing.queuebalancer.core;

public interface Queueable {
    String getId();
    int getPriority();
    void setPriority(int priority);
    int getRetryCount();
    void incrementRetryCount();
}

