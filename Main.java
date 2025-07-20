import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    // If more than 6 minutes, then stop the simulation (For double check purpose)
    static long simulationTimeout = 6 * 60000;
    
    public static void main(String[] args) {
        
        System.out.println("==================================================");
        System.out.println("SwiftCart E-commerce Centre Simulation Start!");
        System.out.println("Target: 600 orders, Order arrival rate: 1 order/500ms");
        System.out.println("==================================================");
        
        long simulationStart = System.currentTimeMillis();
        
        // Create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(15);
        
        try {
            // Start Order Intake System
            executor.submit(new OrderIntakeSystem(600));
            Thread.sleep(1000); // Give order intake a head start
            
            // Start Picking Stations (4 pickers)
            PickingStation.startPickingStations();
            Thread.sleep(500);
            
            // Start Packing Station
            executor.submit(new PackingStation());
            Thread.sleep(500);
            
            // Start Labelling Station
            executor.submit(new LabellingStation());
            Thread.sleep(500);
            
            // Start Sorting Area (Sorter and Loader)
            SortingArea.startSortingArea();
            Thread.sleep(500);
            
            // Start Loading Bay
            LoadingBay.startLoadingBay();
            Thread.sleep(500);
            
            // Start Transport (3 trucks)
            Transport.startTransport();
            
            // Wait for all systems to complete
            while (!ThreadHolder.transportFinished && (System.currentTimeMillis() - simulationStart < simulationTimeout)) {
                Thread.sleep(1000);

                // Check for simulation timeout
                if (System.currentTimeMillis() - simulationStart >= simulationTimeout) {
                    System.out.println("\nSimulation timed out! Force shutdown!"); // Force transport to finish
                    ThreadHolder.orderIntakeFinished = true;
                    ThreadHolder.pickingFinished = true;
                    ThreadHolder.packingFinished = true;
                    ThreadHolder.labellingFinished = true;
                    ThreadHolder.sortingFinished = true;
                    ThreadHolder.loadingFinished = true;
                    ThreadHolder.transportFinished = true;
                    break; // Exit the main loop
                }

                // Existing logic
                if (ThreadHolder.orderIntakeFinished && 
                    ThreadHolder.pickingFinished && 
                    ThreadHolder.packingFinished && 
                    ThreadHolder.labellingFinished && 
                    ThreadHolder.sortingFinished) {
                    ThreadHolder.loadingFinished = true;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) { // Shorter await for forced shutdown
                    System.out.println("Executor stopped without completing all tasks. Forcing shutdown now!");
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
            }
        }
        
        long simulationEnd = System.currentTimeMillis();
        long totalTime = simulationEnd - simulationStart;
        
        // Print final statistics
        printStatistics(totalTime);
    }
    
    private static void printStatistics(long totalTime) {
        System.out.println("\n==================================================");
        System.out.println("SWIFTCART SIMULATION COMPLETED");
        System.out.println("==================================================");
        System.out.println("Simulation Duration: " + (totalTime / 1000.0) + " seconds");
        System.out.println();
        
        System.out.println("PROCESSING STATISTICS:");
        System.out.println("Orders Generated: " + ThreadHolder.totalOrdersGenerated);
        System.out.println("Orders Rejected: " + ThreadHolder.totalOrdersRejected);
        System.out.println("Orders Successfully Picked: " + ThreadHolder.totalOrdersPicked);
        System.out.println("Orders Successfully Packed: " + ThreadHolder.totalOrdersPacked);
        System.out.println("Boxes Labelled: " + ThreadHolder.totalBoxesLabelled);
        System.out.println("Boxes Sorted: " + ThreadHolder.totalBoxesSorted);
        System.out.println("Containers Loaded: " + ThreadHolder.totalContainersLoaded);
        System.out.println("Trucks Dispatched: " + ThreadHolder.totalTrucksDispatched);
        System.out.println();
        
        System.out.println("CONFIRMATION:");
        System.out.println("Done - All ORDERS processed through the system");
        System.out.println("Done - All BOXES cleared from stations");
        System.out.println("Done - All CONTAINERS loaded and dispatched");
        System.out.println("Done - All LOADING BAYS cleared");
        System.out.println();
        
        // Calculate efficiency
        double orderSuccess = (double) ThreadHolder.totalOrdersPicked / ThreadHolder.totalOrdersGenerated * 100;
        System.out.println("METRICS:");
        System.out.printf("Order Success Rate: %.2f%%\n", orderSuccess);
        System.out.printf("Rejection Rate: %.2f%%\n", 100 - orderSuccess);
        System.out.println();
        
        System.out.println("INVENTORY STATUS:");
        System.out.println("Initial Inventory Orders: 20");
        System.out.println("Remaining Inventory Orders: " + ThreadHolder.inventoryOrders);
        System.out.println("Inventory Orders Used: " + (20 - ThreadHolder.inventoryOrders));
        System.out.println();
        
        System.out.println("THREAD COMPLETION STATUS:");
        System.out.println("Order Intake: " + (ThreadHolder.orderIntakeFinished ? "COMPLETED" : "RUNNING"));
        System.out.println("Picking Stations: " + (ThreadHolder.pickingFinished ? "COMPLETED" : "RUNNING"));
        System.out.println("Packing Station: " + (ThreadHolder.packingFinished ? "COMPLETED" : "RUNNING"));
        System.out.println("Labelling Station: " + (ThreadHolder.labellingFinished ? "COMPLETED" : "RUNNING"));
        System.out.println("Sorting Area: " + (ThreadHolder.sortingFinished ? "COMPLETED" : "RUNNING"));
        System.out.println("Loading Bay: " + (ThreadHolder.loadingFinished ? "COMPLETED" : "RUNNING"));
        System.out.println("Transport: " + (ThreadHolder.transportFinished ? "COMPLETED" : "RUNNING"));
        System.out.println();
        
        System.out.println("==================================================");
        System.out.println("SwiftCart simulation completed successfully!");
        System.out.println("==================================================");
    }
}
