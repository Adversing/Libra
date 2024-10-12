package me.adversing.queuebalancer.config;

import lombok.Data;

@Data
public class RateLimitConfig {
    private final int permitsPerSecond;
}
