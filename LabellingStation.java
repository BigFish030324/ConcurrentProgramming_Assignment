// Labelling Station
// Task:
// - Each box is assigned a shipping label with destination and tracking
// - Boxes pass through a quality scanner (1 at a time)

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class LabellingStation implements Runnable {

    // Definition Section
    private BlockingQueue<Order> labelsQueue;
    private BlockingQueue<BoxBatch> sortingQueue;
    private BackEnd backend;
    private int trackingId;
    private int stationId;

    // Constructor Section
    public LabellingStation(BlockingQueue<Order> labelsQueue, BlockingQueue<BoxBatch> sortingQueue, BackEnd backend, int trackingId, int stationId) {
        this.labelsQueue = labelsQueue;
        this.sortingQueue = sortingQueue;
        this.backend = backend;
        this.trackingId = trackingId;
        this.stationId = stationId;
    }

    @Override
    public void run() {
        try {
            while (true) { 

                // Tries to take order from `labelsQueue` and wait maximum 1 second before give up
                Order order = labelsQueue.poll(1, TimeUnit.SECONDS);

                // If there is no more orders to take and all works are done, then break this loop
                // if (order == null && backend.isDone()) {
                //     break;
                // }

                // If an order is taken, prompt description and put order into `sortingQueue` for next process
                if (order != null) {
                    // Will do afterwards (BackEnd will be implemented here)
                    Logs.log("LabellingStation: Labelled Order #" + order.getId() + " with Tracking ID #" + trackingId + " (Thread: " + stationId + ")");
                    sortingQueue.put(new BoxBatch(order));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
