package UnableToRun;
// Loading Bay & Transport
// Task:
// - 3 autonomous loaders (AGVs) transfer containers to 2 outbound loading bays.
// - Trucks take up to 18 containers and leave for delivery hubs.
// - If both bays are occupied, incoming trucks must wait.

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class LoadingBayAndTransport {
    public static class Loader implements Runnable {

        // Definition Section
        private static final double breakdown_rate = 0.01;
        private BlockingQueue<Container> loadingBayQueue;
        private BackEnd backend;
        private Random random;
        private int loaderId;
        private int stationId;

        // Constructor Section
        public Loader(BlockingQueue<Container> loadingBayQueue, BackEnd backend, int loaderId, int stationId) {
            this.loadingBayQueue = loadingBayQueue;
            this.backend = backend;
            this.loaderId = loaderId;
            this.random = new Random();
        }

        // Runnable Section
        @Override
        public void run() {
            try {
                while (true) {
                    // Loading Bay = Space (Truck Holding, 2; Is Taken (True -> False), )
                    // Before Loading Starts, Check for Truck and LoadingBay (2)

                    // After Successfully Taken, Loading Process Start
                    // Tries to take container from `loadingBayQueue` and wait maximum 1 second before give up
                    Container container = loadingBayQueue.poll(1, TimeUnit.SECONDS);
                    
                    // If there is no more containers to take and all works are done, then break this loop
                    if (container == null && backend.isDone()) {
                        break;
                    }

                    if (container != null) {

                        // Simulate there is a 1% breakdown rate
                        if (random.nextDouble() < breakdown_rate) {
                            Logs.log("Loader-" + loaderId + ": Breakdown");     // Release Loader Holding Truck (Other Can Take Over)

                            // Logs a breakdown and pauses 2 seconds to simulate repair
                            Thread.sleep(2000);
                        }
                        Logs.log("Loader-" + loaderId + ": Moving Container #" + container + " to " + stationId);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static class Truck implements Runnable {
        private int truckId;
        private BlockingQueue<Container> loadingBayQueue;
        private BackEnd backend;

        public Truck(int truckId,BlockingQueue<Container> loadingBayQueue, BackEnd backend) {
            this.truckId = truckId;
            this.loadingBayQueue = loadingBayQueue;
            this.backend = backend;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            try {

                // Waiting Queue (When enter system, When Take into Bay, When Exit)
                Thread.sleep(100);
                // loadingBayQueue.put();
                // Will do afterwards (BackEnd will be implemented here)
                // Considering what will be added here
                // Logs.log("Truck-" + truckId + ": Fully loaded with " + containerCount + " containers. Departing to Distribution Centre.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Write a success description for Loading Bay & Transport
            System.out.println("Packing Station Finish Successfully!");
        }
    }
}
