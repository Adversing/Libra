package me.adversing.queuebalancer.ratelimiting.strategy.impl;

import me.adversing.queuebalancer.ratelimiting.strategy.RateLimitingStrategy;

import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucketStrategy implements RateLimitingStrategy {
    private final int permitsPerSecond;
    private final AtomicInteger availableTokens;
    private long lastRefillTime;

    public TokenBucketStrategy(int permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
        this.availableTokens = new AtomicInteger(permitsPerSecond);
        this.lastRefillTime = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean tryAcquire(int permits) {
        refillTokens();
        if (availableTokens.get() >= permits) {
            availableTokens.addAndGet(-permits);
            return true;
        }
        return false;
    }

    private void refillTokens() {
        long now = System.currentTimeMillis();
        long timePassed = now - lastRefillTime;
        int tokensToAdd = (int) (timePassed * permitsPerSecond / 1000);
        if (tokensToAdd > 0) {
            availableTokens.updateAndGet(current -> Math.min(current + tokensToAdd, permitsPerSecond));
            lastRefillTime = now;
        }
    }
}
