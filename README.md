# ⚖️ Libra: Advanced Queue Load Balancer

Libra is a powerful and flexible queue load balancing library for Java applications. It provides a robust solution for managing and processing large volumes of queued items with advanced features like prioritization, rate limiting, circuit breaking, and retry mechanisms.

## Features

- **Priority Queue**: Efficiently manage items based on priority.
- **Load Balancing**: Distribute processing across multiple threads.
- **Rate Limiting**: Control the rate of item processing.
- **Circuit Breaking**: Prevent system overload with automatic circuit breaking.
- **Retry Mechanism**: Automatically retry failed items with configurable policies.
- **Prioritization**: Dynamically prioritize items for processing.
- **Monitoring**: Built-in monitoring for queue status and performance metrics.

## Usage

```java
import me.adversing.queuebalancer.config.*;
import me.adversing.queuebalancer.core.QueueLoadBalancer;
import me.adversing.queuebalancer.destination.Destination;

// Define your batch of items
Collection<YourItemType> items = ...;

// Create your custom Destination implementation
Destination<YourItemType> destination = new YourCustomDestination();

// Configure the load balancer
BalancerConfig config = BalancerConfig.builder()
    .threadPoolConfig(...)
    .rateLimitConfig(...)
    .circuitBreakerConfig(...)
    .retryConfig(...)
    .prioritizationConfig(...)
    .monitoringConfig(...)
    .build();

// Create and start the load balancer
QueueLoadBalancer<YourItemType> balancer = new QueueLoadBalancer<>(items, destination, config);
CompletableFuture<Void> balancerFuture = balancer.start();

// Monitor the balancer status
while (!balancerFuture.isDone()) {
    BalancerStatus status = balancer.getStatus();
    // ...
    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

// Stop the balancer when done
balancer.stop();
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.