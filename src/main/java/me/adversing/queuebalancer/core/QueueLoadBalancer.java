package me.adversing.queuebalancer.core;

import me.adversing.queuebalancer.circuitbreaker.CircuitBreaker;
import me.adversing.queuebalancer.config.BalancerConfig;
import me.adversing.queuebalancer.destination.Destination;
import me.adversing.queuebalancer.monitoring.BalancerMonitor;
import me.adversing.queuebalancer.prioritization.PrioritizationManager;
import me.adversing.queuebalancer.ratelimiting.RateLimiter;
import me.adversing.queuebalancer.retry.RetryManager;
import me.adversing.queuebalancer.threading.ThreadPoolManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class QueueLoadBalancer<T extends Queueable> {
    private final PriorityBlockingQueue<T> queue;
    private final Destination<T> destination;
    private final ThreadPoolManager threadPoolManager;
    private final RateLimiter rateLimiter;
    private final CircuitBreaker circuitBreaker;
    private final RetryManager<T> retryManager;
    private final PrioritizationManager<T> prioritizationManager;
    private final BalancerMonitor monitor;
    private final AtomicBoolean isRunning;

    public QueueLoadBalancer(List<T> items, Destination<T> destination, BalancerConfig config) {
        this.destination = destination;
        this.queue = new PriorityBlockingQueue<>(items);
        this.threadPoolManager = new ThreadPoolManager(config.getThreadPoolConfig());
        this.rateLimiter = new RateLimiter(config.getRateLimitConfig());
        this.circuitBreaker = new CircuitBreaker(config.getCircuitBreakerConfig());
        this.retryManager = new RetryManager<>(config.getRetryConfig());
        this.prioritizationManager = new PrioritizationManager<>(config.getPrioritizationConfig());
        this.monitor = new BalancerMonitor();
        this.isRunning = new AtomicBoolean(false);
    }

    public CompletableFuture<Void> start() {
        if (isRunning.compareAndSet(false, true)) {
            return CompletableFuture.runAsync(this::processItems, threadPoolManager.getExecutorService());
        } else {
            return CompletableFuture.completedFuture(null);
        }
    }

    private void processItems() {
        while (isRunning.get() && !queue.isEmpty()) {
            List<T> batch = prioritizationManager.getNextBatch(queue);

            if (!batch.isEmpty()) {
                CompletableFuture.runAsync(() -> processBatch(batch), threadPoolManager.getExecutorService())
                        .exceptionally(e -> {
                            batch.forEach(queue::offer);
                            return null;
                        });
            }
        }
    }

    private void processBatch(List<T> batch) {
        if (!rateLimiter.tryAcquire(batch.size()) || !circuitBreaker.isAllowed()) {
            batch.forEach(queue::offer);
            return;
        }

        try {
            destination.processBatch(batch);
            circuitBreaker.recordSuccess();
            monitor.recordSuccessfulBatch(batch.size());
        } catch (Exception e) {
            circuitBreaker.recordFailure();
            monitor.recordFailedBatch(batch.size());
            retryManager.handleFailedItems(batch, queue::offer);
        }
    }

    public void stop() {
        isRunning.set(false);
        threadPoolManager.shutdown();
    }

    public BalancerStatus getStatus() {
        return monitor.getStatus(queue.size(), isRunning.get(), circuitBreaker.getState());
    }
}
