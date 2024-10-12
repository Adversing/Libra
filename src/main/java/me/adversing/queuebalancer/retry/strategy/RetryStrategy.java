package me.adversing.queuebalancer.retry.strategy;

import me.adversing.queuebalancer.core.Queueable;

import java.util.List;
import java.util.function.Consumer;

public interface RetryStrategy {
    <T extends Queueable> void handleFailedItems(List<T> items, Consumer<T> retryAction);
}
