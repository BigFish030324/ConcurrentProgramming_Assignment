// # Loading Bay & Trasport
// Tasks:
//    a. 3 autonomous loaders (AGVs) transfer containers to 2 outbound loading bays.
//    b. Trucks take up to 18 containers and leave for delivery hubs.
//    c. If both bays are occupied, incoming trucks must wait.

// *****************************Additional Requirements****************************

// # Autonomous Loaders
// - Trucks can only be loaded when a loader and bay are free.

// # Concurrent Activities
// - TLoaders and outbound trucks operate concurrently.
// - Simulate congestion: e.g., 1 truck is waiting while both loading bays are in use.

// =========================================================================================

import java.util.concurrent.Semaphore;

public class LoadingBay {
    
    // Semaphore to limit parking slots to 2
    private static Semaphore parkingSlots = new Semaphore(2);

    private static Thread bayWorkerThreadRef;
    private static Thread truckWorkerThreadRef;

    public static class LoadingBayWorker implements Runnable {
        private int loadedToBayCount = 0;
        
        @Override
        public void run() {
            Thread.currentThread().setName(ThreadHolder.LOADING_BAY_1);
            
            while (!ThreadHolder.sortingFinished || !ThreadHolder.loadingParkingQueue.isEmpty()) {
                try {
                    Container container = ThreadHolder.loadingParkingQueue.poll();
                    if (container != null) {
                        loadedToBayCount++;
                        System.out.println(Thread.currentThread().getName() + ": Moving Container #" + 
                                            container.getContainerID() + " to Loading Bay-1");
                        
                        // Wait for parking slot availability
                        parkingSlots.acquire();
                        ThreadHolder.loadingTruckQueue.put(container);
                        
                        // Simulate loading bay time
                        Thread.sleep(100);
                        
                    } else {
                        // Wait if no containers available
                        Thread.sleep(100);
                    }
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
            System.out.println("Loading Bay Finish Successfully!");
        }
    }
    
    public static class LoadingToTruckWorker implements Runnable {
        private int loadedToTruck = 0;
        
        @Override
        public void run() {
            Thread.currentThread().setName(ThreadHolder.LOADING_TO_TRUCK_1);
            
            while (!Thread.currentThread().isInterrupted() && (!ThreadHolder.sortingFinished || !ThreadHolder.loadingParkingQueue.isEmpty())) {
                try {
                    Container container = ThreadHolder.loadingTruckQueue.poll();
                    if (container != null) {
                        loadedToTruck++;
                        System.out.println(Thread.currentThread().getName() + ": Moving Container #" + 
                                            container.getContainerID() + " to Truck Parking");
                        
                        // Release parking slot when container is loaded to truck
                        parkingSlots.release();
                        Thread.sleep(150); // Simulate truck loading time
                        
                    } else {
                        Thread.sleep(100); // Wait if no containers available
                    }
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Loading Bay Worker interrupted. Shutting down.");
                    break;
                }
            }
        }
    }
    
    public static void startLoadingBay() {
        bayWorkerThreadRef = new Thread(new LoadingBayWorker());
        truckWorkerThreadRef = new Thread(new LoadingToTruckWorker());

        bayWorkerThreadRef.start();
        truckWorkerThreadRef.start();
    }

    public static Semaphore getParkingSlots() {
        return parkingSlots;
    }

    public static Thread getBayWorkerThreadRef() { return bayWorkerThreadRef; }
    public static Thread getTruckWorkerThreadRef() { return truckWorkerThreadRef; }
}
