import java.util.ArrayList;
import java.util.List;
public class Main {

    private static List<Long> truckLoadingTimes = new ArrayList<>();
    private static List<Long> truckWaitingTimes = new ArrayList<>();
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

            // Interrupt manually started threads if they are still alive
            if (Transport.getTruck1ThreadRef() != null && Transport.getTruck1ThreadRef().isAlive()) {
                Transport.getTruck1ThreadRef().interrupt();
            }
            if (Transport.getTruck2ThreadRef() != null && Transport.getTruck2ThreadRef().isAlive()) {
                Transport.getTruck2ThreadRef().interrupt();
            }
            if (Transport.getTruck3ThreadRef() != null && Transport.getTruck3ThreadRef().isAlive()) {
                Transport.getTruck3ThreadRef().interrupt();
            }
            if (LoadingBay.getBayWorkerThreadRef() != null && LoadingBay.getBayWorkerThreadRef().isAlive()) {
                LoadingBay.getBayWorkerThreadRef().interrupt();
            }
            if (LoadingBay.getTruckWorkerThreadRef() != null && LoadingBay.getTruckWorkerThreadRef().isAlive()) {
                LoadingBay.getTruckWorkerThreadRef().interrupt();
            }
        }
        
        long simulationEnd = System.currentTimeMillis();
        long totalTime = simulationEnd - simulationStart;
    }
    }
}
