package me.adversing.queuebalancer.destination;

import me.adversing.queuebalancer.core.Queueable;

import java.util.List;

public interface Destination<T extends Queueable> {
    void processBatch(List<T> items) throws Exception;
}
