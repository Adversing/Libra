package me.adversing.queuebalancer.config;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class BalancerConfig {
    private final ThreadPoolConfig threadPoolConfig;
    private final RateLimitConfig rateLimitConfig;
    private final CircuitBreakerConfig circuitBreakerConfig;
    private final RetryConfig retryConfig;
    private final PrioritizationConfig prioritizationConfig;
    private final MonitoringConfig monitoringConfig;
}
