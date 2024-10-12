package me.adversing.queuebalancer.retry;

import me.adversing.queuebalancer.config.RetryConfig;
import me.adversing.queuebalancer.core.Queueable;
import me.adversing.queuebalancer.retry.strategy.RetryStrategy;
import me.adversing.queuebalancer.retry.strategy.impl.ExponentialBackoffStrategy;

import java.util.List;
import java.util.function.Consumer;

public class RetryManager<T extends Queueable> {
    private final RetryStrategy strategy;

    public RetryManager(RetryConfig config) {
        this.strategy = new ExponentialBackoffStrategy(config);
    }

    public void handleFailedItems(List<T> items, Consumer<T> retryAction) {
        strategy.handleFailedItems(items, retryAction);
    }
}
