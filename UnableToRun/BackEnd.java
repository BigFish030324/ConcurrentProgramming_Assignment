package UnableToRun;
import java.util.concurrent.atomic.AtomicInteger;

public class BackEnd {
    private final int totalOrders;
    private final AtomicInteger processedOrders = new AtomicInteger(0);
    private final AtomicInteger rejectedOrders = new AtomicInteger(0);
    private final AtomicInteger packedBoxes = new AtomicInteger(0);
    private final AtomicInteger containers = new AtomicInteger(0);

    // Add Processed for Every Station
    private final AtomicInteger labelOrderProcessed = new AtomicInteger(0);

    public BackEnd(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    // Call when an order is accepted and processed
    public void orderProcessed() {
        processedOrders.incrementAndGet();
    }

    public void logLabel() {
        labelOrderProcessed.incrementAndGet();
    }

    public AtomicInteger getLabel() {return labelOrderProcessed;}

    // Call when an order is rejected
    public void orderRejected() {
        processedOrders.incrementAndGet();
        rejectedOrders.incrementAndGet();
    }

    // Call when a box is packed
    public void boxPacked() {
        packedBoxes.incrementAndGet();
    }

    // Call when a container is created
    public void containerCreated() {
        containers.incrementAndGet();
    }

    // Returns true if all orders have been processed (accepted or rejected)
    public boolean isDone() {
        return processedOrders.get() == totalOrders;
    }

    // Getters for reporting
    public int getProcessedOrders() {
        return processedOrders.get();
    }

    public int getRejectedOrders() {
        return rejectedOrders.get();
    }

    public int getPackedBoxes() {
        return packedBoxes.get();
    }

    public int getContainers() {
        return containers.get();
    }
}
