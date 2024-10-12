package me.adversing.queuebalancer.core;

import lombok.Getter;
import me.adversing.queuebalancer.circuitbreaker.CircuitBreaker;

@Getter
public class BalancerStatus {
    private final boolean running;
    private final int remainingItems;
    private final int processedItems;
    private final int failedItems;
    private final CircuitBreaker.State circuitBreakerState;

    public BalancerStatus(boolean running, int remainingItems, int processedItems, int failedItems, CircuitBreaker.State circuitBreakerState) {
        this.running = running;
        this.remainingItems = remainingItems;
        this.processedItems = processedItems;
        this.failedItems = failedItems;
        this.circuitBreakerState = circuitBreakerState;
    }


}
