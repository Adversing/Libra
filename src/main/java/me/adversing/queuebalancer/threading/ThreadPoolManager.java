package me.adversing.queuebalancer.threading;

import lombok.Getter;
import me.adversing.queuebalancer.config.ThreadPoolConfig;

import java.util.concurrent.TimeUnit;

@Getter
public class ThreadPoolManager {
    private final AdaptiveThreadPoolExecutor executorService;

    public ThreadPoolManager(ThreadPoolConfig config) {
        this.executorService = new AdaptiveThreadPoolExecutor(
                config.getCorePoolSize(),
                config.getMaxPoolSize(),
                config.getKeepAliveTime(),
                TimeUnit.SECONDS,
                config.getWorkQueue()
        );
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
