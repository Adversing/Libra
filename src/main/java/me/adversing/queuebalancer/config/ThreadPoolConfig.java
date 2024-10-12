package me.adversing.queuebalancer.config;

import lombok.Data;
import lombok.NonNull;

import java.util.concurrent.BlockingQueue;

@Data
public class ThreadPoolConfig {
    private final int corePoolSize;
    private final int maxPoolSize;
    private final long keepAliveTime;
    private final int queueCapacity;
    @NonNull private final BlockingQueue<Runnable> workQueue;
}
