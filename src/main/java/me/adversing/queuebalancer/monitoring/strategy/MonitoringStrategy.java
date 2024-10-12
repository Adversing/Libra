package me.adversing.queuebalancer.monitoring.strategy;

import me.adversing.queuebalancer.circuitbreaker.CircuitBreaker;
import me.adversing.queuebalancer.core.BalancerStatus;

public interface MonitoringStrategy {
    void recordSuccessfulBatch(int batchSize);
    void recordFailedBatch(int batchSize);
    BalancerStatus getStatus(int queueSize, boolean isRunning, CircuitBreaker.State circuitBreakerState);
}
