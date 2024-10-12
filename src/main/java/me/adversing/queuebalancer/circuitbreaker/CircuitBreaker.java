package me.adversing.queuebalancer.circuitbreaker;

import me.adversing.queuebalancer.circuitbreaker.strategy.CircuitBreakerStrategy;
import me.adversing.queuebalancer.circuitbreaker.strategy.impl.DefaultCircuitBreakerStrategy;
import me.adversing.queuebalancer.config.CircuitBreakerConfig;

public class CircuitBreaker {
    private final CircuitBreakerStrategy strategy;

    public CircuitBreaker(CircuitBreakerConfig config) {
        this.strategy = new DefaultCircuitBreakerStrategy(config);
    }

    public boolean isAllowed() {
        return strategy.isAllowed();
    }

    public void recordSuccess() {
        strategy.recordSuccess();
    }

    public void recordFailure() {
        strategy.recordFailure();
    }

    public State getState() {
        return strategy.getState();
    }

    public enum State {
        CLOSED, OPEN, HALF_OPEN
    }
}