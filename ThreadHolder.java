import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadHolder {
    
    // Counters for statistics
    public static BlockingQueue<Container> loadingBayQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Container> loadingParkingQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Container> loadingTruckQueue = new LinkedBlockingQueue<>();
    
    // Counters for statistics
    public static volatile int totalContainersLoaded = 0;
    public static volatile int totalTrucksDispatched = 0;
    
    // If thread is completed
    public static volatile boolean loadingFinished = false;
    public static volatile boolean transportFinished = false;
    
    // Thread Names
    public static final String LOADING_BAY_1 = "Loading Bay-1";
    public static final String LOADING_TO_TRUCK_1 = "Loading To Truck-1";
    public static final String TRUCK_1 = "Truck-1";
    public static final String TRUCK_2 = "Truck-2";
    public static final String TRUCK_3 = "Truck-3";
    
}
