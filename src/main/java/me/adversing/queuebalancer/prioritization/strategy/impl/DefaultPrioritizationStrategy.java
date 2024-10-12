package me.adversing.queuebalancer.prioritization.strategy.impl;

import me.adversing.queuebalancer.config.PrioritizationConfig;
import me.adversing.queuebalancer.core.Queueable;
import me.adversing.queuebalancer.prioritization.strategy.PrioritizationStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DefaultPrioritizationStrategy<T extends Queueable> implements PrioritizationStrategy<T> {
    private final int batchSize;

    public DefaultPrioritizationStrategy(PrioritizationConfig config) {
        this.batchSize = config.getBatchSize();
    }

    @Override
    public List<T> getNextBatch(Queue<T> queue) {
        List<T> batch = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize && !queue.isEmpty(); i++) {
            batch.add(queue.poll());
        }
        return batch;
    }

    @Override
    public void reprioritize(T item, int newPriority) {
        item.setPriority(newPriority);
    }
}
