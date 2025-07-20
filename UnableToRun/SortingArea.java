package UnableToRun;
// Sorting Area
// Task:
// - Boxes are sorted into batches of 6 boxes based on regional zones.
// - Batches are loaded into transport containers (30 boxes per container)

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class SortingArea implements Runnable {
    
    // Definition Section
    private static final int batches_size = 6;
    private BlockingQueue<BoxBatch> sortingQueue;
    private BlockingQueue<Container> loadingBayQueue;
    private BackEnd backend;
    private int stationId;

    // Constructor Section
    public SortingArea(BlockingQueue<BoxBatch> sortingQueue, BlockingQueue<Container> loadingBayQueue, BackEnd backend, int stationId) {
        this.sortingQueue = sortingQueue;
        this.loadingBayQueue = loadingBayQueue;
        this.backend =backend;
    }

    // Runnable Section
    @Override
    public void run() {
        try {
            while (true) { 

                List<Order> batch = new ArrayList<>();
                // Tries to take box from `sortingQueue` and wait maximum 1 second before give up
                // BoxBatch box = sortingQueue.poll(1, TimeUnit.SECONDS);

                // If batches of 6 boxes are not empty, then adds all orders from the box into the batch list
                int i=0;
                while (i < batches_size) {
                    BoxBatch box = sortingQueue.poll(1, TimeUnit.SECONDS);
                    if (box != null) {
                        batch.addAll(box.getOrders()); 
                        i++;
                        Logs.log("Sorter: Added Order #" + batch.size() + " to Batch #" + box + " (Thread: " + stationId + ")");
                        backend.orderProcessed();
                        // Test
                        if (backend.isDone()) {break;}
                        // Add safety precaution (Force all order end) // (Create New Object when finish) Container Loop, Batch Loop, Order Loop (End Task)
                    }
                }
                // for (int i = 0; i < batches_size; i++) {
                //     if (box != null) batch.addAll(box.getOrders());
                // }

                // If there is no more orders to take and all works are done, then break this loop
                if (backend.isDone()) {
                    break;
                }

                // Logs.log("Sorter: Added Order #" + batch.size() + " to Batch #" + box + " (Thread: " + stationId);
                Container container = new Container(backend.getContainers());
                container.addOrders(batch);
                loadingBayQueue.put(container);
                // Will do afterwards (BackEnd will be implemented here)
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Write a success description for Sorting Area
        System.out.println("Sorting Area Finish Successfully!");
    }
}
