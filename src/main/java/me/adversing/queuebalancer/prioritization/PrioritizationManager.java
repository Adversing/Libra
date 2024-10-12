package me.adversing.queuebalancer.prioritization;

import me.adversing.queuebalancer.config.PrioritizationConfig;
import me.adversing.queuebalancer.core.Queueable;
import me.adversing.queuebalancer.prioritization.strategy.PrioritizationStrategy;
import me.adversing.queuebalancer.prioritization.strategy.impl.DefaultPrioritizationStrategy;

import java.util.List;
import java.util.Queue;

public class PrioritizationManager<T extends Queueable> {
    private final PrioritizationStrategy<T> strategy;

    public PrioritizationManager(PrioritizationConfig config) {
        this.strategy = new DefaultPrioritizationStrategy<>(config);
    }

    public List<T> getNextBatch(Queue<T> queue) {
        return strategy.getNextBatch(queue);
    }

    public void reprioritize(T item, int newPriority) {
        strategy.reprioritize(item, newPriority);
    }
}
