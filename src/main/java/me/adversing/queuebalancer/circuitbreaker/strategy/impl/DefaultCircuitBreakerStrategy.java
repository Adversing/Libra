package me.adversing.queuebalancer.circuitbreaker.strategy.impl;

import me.adversing.queuebalancer.circuitbreaker.CircuitBreaker;
import me.adversing.queuebalancer.circuitbreaker.strategy.CircuitBreakerStrategy;
import me.adversing.queuebalancer.config.CircuitBreakerConfig;

public class DefaultCircuitBreakerStrategy implements CircuitBreakerStrategy {
    private CircuitBreaker.State state;
    private int failureCount;
    private long lastFailureTime;
    private final int failureThreshold;
    private final long resetTimeout;

    public DefaultCircuitBreakerStrategy(CircuitBreakerConfig config) {
        this.state = CircuitBreaker.State.CLOSED;
        this.failureCount = 0;
        this.failureThreshold = config.getFailureThreshold();
        this.resetTimeout = config.getResetTimeout();
    }

    @Override
    public boolean isAllowed() {
        switch (state) {
            case OPEN -> {
                if (System.currentTimeMillis() - lastFailureTime > resetTimeout) {
                    state = CircuitBreaker.State.HALF_OPEN;
                    return true;
                }
                return false;
            }
            case null, default -> {
                return true;
            }
        }
    }

    @Override
    public void recordSuccess() {
        if (state == CircuitBreaker.State.HALF_OPEN) {
            state = CircuitBreaker.State.CLOSED;
            failureCount = 0;
        }
    }

    @Override
    public void recordFailure() {
        failureCount++;
        lastFailureTime = System.currentTimeMillis();
        if (failureCount >= failureThreshold) {
            state = CircuitBreaker.State.OPEN;
        }
    }

    @Override
    public CircuitBreaker.State getState() {
        return state;
    }
}
