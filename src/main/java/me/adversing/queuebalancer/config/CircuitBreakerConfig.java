package me.adversing.queuebalancer.config;

import lombok.Data;

@Data
public class CircuitBreakerConfig {
    private final int failureThreshold;
    private final long resetTimeout;
}
