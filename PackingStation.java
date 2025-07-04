// Packing Station
// Task:
// - Completed bins are packed into shipping boxes (1 order at a time)
// - A scanner checks each box to ensure contents match the order

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class PackingStation implements Runnable{

    // Definition Section
    private BlockingQueue<Order> packsQueue;
    private BlockingQueue<Order> labelsQueue;
    private BackEnd backend;
    private int stationId;

    // Constructor Section
    public PackingStation(BlockingQueue<Order> packsQueue, BlockingQueue<Order> labelsQueue, BackEnd backend, int stationId) {
        this.packsQueue = packsQueue;
        this.labelsQueue = labelsQueue;
        this.backend = backend;
        this.stationId = stationId;
    }

    // Runnable Section
    @Override
    public void run() {
        try {
            while (true) { 

                // Tries to take order from `packsQueue` and wait maximum 1 second before give up
                Order order = packsQueue.poll(1, TimeUnit.SECONDS);

                // If there is no more orders to take and all works are done, then break this loop
                // if (order == null && backend.isDone()) {
                //     break;
                // }

                // If an order is taken, prompt description and put order into `labelsQueue` for next process
                if (order != null) {
                    // Will do afterwards (BackEnd will be implemented here)
                    Logs.log("PackingStation: Packed Order #" + order.getId() + " (Thread: " + stationId + ")");
                    labelsQueue.put(order);
                }
            }
        } catch (InterruptedException e) {
            // Handle exception
            Thread.currentThread().interrupt();
        }

        // Write a success description for Packing System
        System.out.println("Packing Station Finish Successfully!");
    }
}
