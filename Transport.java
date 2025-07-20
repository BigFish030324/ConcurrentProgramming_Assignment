import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;

public class Transport implements Runnable {
    private String truckName;
    private int containersLoaded = 0;
    private long waitingTime = 0;
    private static AtomicInteger trucksDispatched = new AtomicInteger(0);
    
    private static Thread truck1ThreadRef;
    private static Thread truck2ThreadRef;
    private static Thread truck3ThreadRef;

    public Transport(String truckName) {
        this.truckName = truckName;
    }
    
    @Override
    public void run() {
        Thread.currentThread().setName(truckName);

        while (!Thread.currentThread().isInterrupted() && (!ThreadHolder.loadingFinished || containersLoaded > 0)) {
            try {
                // Try to acquire parking slot with a timeout (5 seconds)
                boolean slotAcquired = LoadingBay.getParkingSlots().tryAcquire(5, TimeUnit.SECONDS);

                if (slotAcquired) {
                    // Load containers until truck is full (1 truck can get 18 containers ONLY)
                    while (containersLoaded < 18) {
                        Container container = ThreadHolder.loadingTruckQueue.poll();
                        if (container != null) {
                            containersLoaded++;
                        } else {
                            // No more containers available or simulation finishing
                            if (ThreadHolder.loadingFinished && ThreadHolder.loadingTruckQueue.isEmpty()) {
                                break;
                            }

                            // Wait for awhile before rechecking queue
                            Thread.sleep(100);
                        }
                    }

                    // Truck is loaded or no more containers
                    if (containersLoaded > 0) {
                        System.out.println(Thread.currentThread().getName() + 
                                            ": Fully loaded with " + containersLoaded + " containers. Departing to Distribution Centre.");

                        ThreadHolder.totalTrucksDispatched++;
                        trucksDispatched.incrementAndGet();

                        // Release parking slot
                        LoadingBay.getParkingSlots().release();

                        // Simulate departure time
                        Thread.sleep(1000);
                        containersLoaded = 0; // Reset for next load
                    } else {
                        // If no containers to load, release slot and consider to break if simulation finished
                        LoadingBay.getParkingSlots().release();
                        if (ThreadHolder.loadingFinished && ThreadHolder.loadingTruckQueue.isEmpty()) {
                            break; 
                        }
                        Thread.sleep(500); // Pause for awhile if no containers and not finished
                    }

                } else {
                    // Parking slot not available within timeout
                    System.out.println(Thread.currentThread().getName() + ": Waited too long for loading bay.");
                    // If simulation is ending and no more work then break
                    if (ThreadHolder.loadingFinished && ThreadHolder.loadingTruckQueue.isEmpty()) {
                        break;
                    }

                    // Small delay before retrying needed
                    Thread.sleep(500);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " interrupted. Shutting down.");
                break;
            }
        }

        // Check if all trucks are done
        if (ThreadHolder.loadingFinished && ThreadHolder.loadingTruckQueue.isEmpty() && trucksDispatched.get() == 3) {
            ThreadHolder.transportFinished = true;
        } else if (ThreadHolder.loadingFinished && ThreadHolder.loadingTruckQueue.isEmpty()) {
            // If loading is finished and queue is empty, but not all 3 trucks dispatched then mark transport finished to allow the simulation to complete.
            ThreadHolder.transportFinished = true;
        }
    }
    
    public long getWaitingTime() {
        return waitingTime;
    }
    
    public static void startTransport() {
        truck1ThreadRef = new Thread(new Transport(ThreadHolder.TRUCK_1));
        truck2ThreadRef = new Thread(new Transport(ThreadHolder.TRUCK_2));
        truck3ThreadRef = new Thread(new Transport(ThreadHolder.TRUCK_3));

        truck1ThreadRef.start();
        truck2ThreadRef.start();
        truck3ThreadRef.start();
    }

    public static Thread getTruck1ThreadRef() { return truck1ThreadRef; }
    public static Thread getTruck2ThreadRef() { return truck2ThreadRef; }
    public static Thread getTruck3ThreadRef() { return truck3ThreadRef; }
}
