package UnableToRun;
// Picking Station
// Task:
// - Robotic arms pick items from shelves and place them into order bins
// - Up to 4 orders can be picked at a time
// - Orders are verified for missing items

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class PickingStation implements Runnable{
    
    // Definition Section
    private static int pick_size = 4;
    private BlockingQueue<Order> intakeSystemBlockingQueue;
    private BlockingQueue<Order> packsQueue;
    private Random random;
    private BackEnd backend;
    private int stationId;

    // Constructor Section
    public PickingStation(BlockingQueue<Order> intakeSystemBlockingQueue, BlockingQueue<Order> packsQueue, BackEnd backend, int stationId) {
        this.intakeSystemBlockingQueue = intakeSystemBlockingQueue;
        this.packsQueue = packsQueue;
        this.backend = backend;
        this.random = new Random();
    }

    // Runnable Section
    @Override
    public void run() {
        try {
            while (true) { 
                List<Order> picks = new ArrayList<>();

                // Put orders from `intakeSystemBlockingQueue` to picks (put into the 4 places specified in `pick_size`)
                intakeSystemBlockingQueue.drainTo(picks, pick_size);
                
                // If there is no more orders and all works are done, then break this loop
                if (picks.isEmpty() && backend.isDone()) {
                    break;
                }

                // Loops a batch of picked orders and process them one by one
                for (int i = 0; i < picks.size(); i++) {
                    // Fetches orders from `picks`
                    Order pi = picks.get(i);

                    Logs.log("PickingStation: Picking Order #" + pi.getId() + " (Thread: " + stationId + ")");

                    // Simulate there is a 2% rejection rate regarding missing items
                    if (random.nextDouble() < 0.02) {
                        // Code to reject order
                        // Will do afterwards (BackEnd will be implemented here)
                        Logs.log("PickingStation: Picking Order Rejected (missing items) Order #" + pi.getId());
                    } else {
                        // Code to accept order and put into `packsQueue` for next process
                        packsQueue.put(pi);
                    }
                }
            }
        } catch (InterruptedException e) {
            // Handle exception
            Thread.currentThread().interrupt();
        }

        // Write a success description for Picking System
        System.out.println("Picking Station Finish Successfully!");
    }
}
