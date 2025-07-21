// # Sorting Area
// Tasks:
//    a. Boxes are sorted into batches of 6 boxes based on regional zones.
//    b. Batches are loaded into transport containers (30 boxes per container).
// =========================================================================================

import java.util.HashMap;
import java.util.Map;

public class SortingArea {
    
    public static class Sorter implements Runnable {
        private int sortedCount = 0;
        private Map<String, Batch> regionBatches = new HashMap<>();
        
        @Override
        public void run() {
            Thread.currentThread().setName(ThreadHolder.SORTER_1);
            
            while (!ThreadHolder.labellingFinished || !ThreadHolder.sortingQueue.isEmpty()) {
                try {
                    Box box = ThreadHolder.sortingQueue.poll();
                    if (box != null) {
                        String region = box.getRegion();
                        
                        // Get or create batch for region
                        Batch batch = regionBatches.get(region);
                        if (batch == null || batch.isFull()) {
                            batch = new Batch(ThreadHolder.batchIDCounter++, region);
                            regionBatches.put(region, batch);
                        }
                        
                        batch.addBox(box);
                        sortedCount++;
                        ThreadHolder.totalBoxesSorted++;
                        
                        // Print for each order in the box
                        for (Order order : box.getOrders()) {
                            System.out.println("Sorter: Added Order #" + order.getOrderID() + 
                                                " to Batch #" + batch.getBatchID() + 
                                                " (Thread: " + Thread.currentThread().getName() + ")");
                        }
                        
                        // If batch is full, send to loader
                        if (batch.isFull()) {
                            // Create container and add batches
                            Container container = new Container(ThreadHolder.containerIDCounter++);
                            for (Box b : batch.getBoxes()) {
                                container.addBox(b);
                            }
                            ThreadHolder.loadingBayQueue.put(container);
                            regionBatches.remove(region); // Remove full batch
                        }
                        
                        Thread.sleep(50); // Simulate sorting time
                        
                    } else {
                        Thread.sleep(100); // Wait if no boxes available
                    }
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
            // Handle remaining incomplete batches
            for (Map.Entry<String, Batch> entry : regionBatches.entrySet()) {
                Batch batch = entry.getValue();
                if (!batch.getBoxes().isEmpty()) {
                    try {
                        Container container = new Container(ThreadHolder.containerIDCounter++);
                        for (Box box : batch.getBoxes()) {
                            container.addBox(box);
                        }
                        ThreadHolder.loadingBayQueue.put(container);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            
            ThreadHolder.sortingFinished = true;
            System.out.println("Sorting Area Finish Successfully!");
        }
    }
    
    public static class Loader implements Runnable {
        @Override
        public void run() {
            Thread.currentThread().setName(ThreadHolder.LOADER_1);
            
            while (!ThreadHolder.sortingFinished || !ThreadHolder.loadingBayQueue.isEmpty()) {
                try {
                    Container container = ThreadHolder.loadingBayQueue.poll();
                    if (container != null) {
                        ThreadHolder.totalContainersLoaded++;
                        System.out.println(Thread.currentThread().getName() + ": Moving Container #" + 
                                            container.getContainerID() + " to Loading Bay-1");
                        
                        ThreadHolder.loadingParkingQueue.put(container);
                        Thread.sleep(200); // Simulate loading time
                        
                    } else {
                        Thread.sleep(100); // Wait if no containers available
                    }
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
    
    public static void startSortingArea() {
        Thread sorter = new Thread(new Sorter());
        Thread loader = new Thread(new Loader());
        
        sorter.start();
        loader.start();
    }
}
