package me.adversing.queuebalancer.monitoring.strategy.impl;

import me.adversing.queuebalancer.circuitbreaker.CircuitBreaker;
import me.adversing.queuebalancer.core.BalancerStatus;
import me.adversing.queuebalancer.monitoring.strategy.MonitoringStrategy;

import java.util.concurrent.atomic.AtomicInteger;

public class DefaultMonitoringStrategy implements MonitoringStrategy {
    private final AtomicInteger processedItems;
    private final AtomicInteger failedItems;

    public DefaultMonitoringStrategy() {
        this.processedItems = new AtomicInteger(0);
        this.failedItems = new AtomicInteger(0);
    }

    @Override
    public void recordSuccessfulBatch(int batchSize) {
        processedItems.addAndGet(batchSize);
    }

    @Override
    public void recordFailedBatch(int batchSize) {
        failedItems.addAndGet(batchSize);
    }

    @Override
    public BalancerStatus getStatus(
            int queueSize,
            boolean isRunning,
            CircuitBreaker.State circuitBreakerState
    ) {
        return new BalancerStatus(
                isRunning,
                queueSize,
                processedItems.get(),
                failedItems.get(),
                circuitBreakerState
        );
    }
}
