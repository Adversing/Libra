package me.adversing.queuebalancer.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AdaptiveThreadPoolExecutor extends ThreadPoolExecutor {

    private final int minThreads;
    private final int maxThreads;

    public AdaptiveThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.minThreads = corePoolSize;
        this.maxThreads = maximumPoolSize;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        adjustThreadPool();
    }

    private void adjustThreadPool() {
        int currentPoolSize = getPoolSize();
        int activeThreads = getActiveCount();
        int queueSize = getQueue().size();

        if (queueSize > 0 && currentPoolSize < maxThreads) {
            int newPoolSize = Math.min(currentPoolSize + 1, maxThreads);
            setMaximumPoolSize(newPoolSize);
        } else if (queueSize == 0 && activeThreads < currentPoolSize && currentPoolSize > minThreads) {
            int newPoolSize = Math.max(currentPoolSize - 1, minThreads);
            setMaximumPoolSize(newPoolSize);
        }
    }
}
