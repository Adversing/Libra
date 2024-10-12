package me.adversing.queuebalancer.ratelimiting;

import me.adversing.queuebalancer.config.RateLimitConfig;
import me.adversing.queuebalancer.ratelimiting.strategy.RateLimitingStrategy;
import me.adversing.queuebalancer.ratelimiting.strategy.impl.TokenBucketStrategy;

public class RateLimiter {
    private final RateLimitingStrategy strategy;

    public RateLimiter(RateLimitConfig config) {
        this.strategy = new TokenBucketStrategy(config.getPermitsPerSecond());
    }

    public boolean tryAcquire(int permits) {
        return strategy.tryAcquire(permits);
    }
}
