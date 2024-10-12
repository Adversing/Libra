package me.adversing.queuebalancer.ratelimiting.strategy;

public interface RateLimitingStrategy {
    boolean tryAcquire(int permits);
}
