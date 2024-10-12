package me.adversing.queuebalancer.circuitbreaker.strategy;

import me.adversing.queuebalancer.circuitbreaker.CircuitBreaker;

public interface CircuitBreakerStrategy {
    boolean isAllowed();
    void recordSuccess();
    void recordFailure();
    CircuitBreaker.State getState();
}
