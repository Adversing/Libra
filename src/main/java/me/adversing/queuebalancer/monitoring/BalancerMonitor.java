package me.adversing.queuebalancer.monitoring;

import me.adversing.queuebalancer.circuitbreaker.CircuitBreaker;
import me.adversing.queuebalancer.core.BalancerStatus;
import me.adversing.queuebalancer.monitoring.strategy.MonitoringStrategy;
import me.adversing.queuebalancer.monitoring.strategy.impl.DefaultMonitoringStrategy;

public class BalancerMonitor {
    private final MonitoringStrategy strategy;

    public BalancerMonitor() {
        this.strategy = new DefaultMonitoringStrategy();
    }

    public void recordSuccessfulBatch(int batchSize) {
        strategy.recordSuccessfulBatch(batchSize);
    }

    public void recordFailedBatch(int batchSize) {
        strategy.recordFailedBatch(batchSize);
    }

    public BalancerStatus getStatus(int queueSize, boolean isRunning, CircuitBreaker.State circuitBreakerState) {
        return strategy.getStatus(queueSize, isRunning, circuitBreakerState);
    }
}
