package me.adversing.queuebalancer.prioritization.strategy;

import me.adversing.queuebalancer.core.Queueable;

import java.util.List;
import java.util.Queue;

public interface PrioritizationStrategy<T extends Queueable> {
    List<T> getNextBatch(Queue<T> queue);
    void reprioritize(T item, int newPriority);
}
