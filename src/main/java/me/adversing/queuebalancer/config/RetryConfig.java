package me.adversing.queuebalancer.config;

import lombok.Data;

@Data
public class RetryConfig {
    private final int maxRetries;
    private final long initialBackoff;
    private final long maxBackoff;
}
