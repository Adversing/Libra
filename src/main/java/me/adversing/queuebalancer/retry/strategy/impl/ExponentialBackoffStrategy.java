package me.adversing.queuebalancer.retry.strategy.impl;

import me.adversing.queuebalancer.config.RetryConfig;
import me.adversing.queuebalancer.core.Queueable;
import me.adversing.queuebalancer.retry.strategy.RetryStrategy;

import java.util.List;
import java.util.function.Consumer;

public class ExponentialBackoffStrategy implements RetryStrategy {
    private final int maxRetries;
    private final long initialBackoff;
    private final long maxBackoff;

    public ExponentialBackoffStrategy(RetryConfig config) {
        this.maxRetries = config.getMaxRetries();
        this.initialBackoff = config.getInitialBackoff();
        this.maxBackoff = config.getMaxBackoff();
    }

    @Override
    public <T extends Queueable> void handleFailedItems(List<T> items, Consumer<T> retryAction) {
        for (T item : items) {
            if (item.getRetryCount() < maxRetries) {
                long delay = calculateBackoff(item.getRetryCount());
                item.incrementRetryCount();
                scheduleRetry(item, delay, retryAction);
            }
        }
    }

    private long calculateBackoff(int retryCount) {
        long backoff = initialBackoff * (long) Math.pow(2, retryCount);
        return Math.min(backoff, maxBackoff);
    }

    private <T extends Queueable> void scheduleRetry(T item, long delay, Consumer<T> retryAction) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                retryAction.accept(item);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
