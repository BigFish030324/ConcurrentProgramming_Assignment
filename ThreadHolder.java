import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadHolder {
    // Queues
    public static BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Order> packingQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Box> labellingQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Box> sortingQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Container> loadingBayQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Container> loadingParkingQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Container> loadingTruckQueue = new LinkedBlockingQueue<>();
    
    // Counters for statistics
    public static volatile int totalOrdersGenerated = 0;
    public static volatile int totalOrdersRejected = 0;
    public static volatile int totalOrdersPicked = 0;
    public static volatile int totalOrdersPacked = 0;
    public static volatile int totalBoxesLabelled = 0;
    public static volatile int totalBoxesSorted = 0;
    public static volatile int totalContainersLoaded = 0;
    public static volatile int totalTrucksDispatched = 0;
    
    // Inventory
    public static volatile int inventoryOrders = 20;
    
    // If thread is completed
    public static volatile boolean orderIntakeFinished = false;
    public static volatile boolean pickingFinished = false;
    public static volatile boolean packingFinished = false;
    public static volatile boolean labellingFinished = false;
    public static volatile boolean sortingFinished = false;
    public static volatile boolean loadingFinished = false;
    public static volatile boolean transportFinished = false;
    
    // Thread Names
    public static final String ORDER_THREAD = "OrderThread-1";
    public static final String PICKER_1 = "Picker-1";
    public static final String PICKER_2 = "Picker-2";
    public static final String PICKER_3 = "Picker-3";
    public static final String PICKER_4 = "Picker-4";
    
    // Counters for IDs
    public static volatile int boxIDCounter = 1;
    public static final String PACKER_1 = "Packer-1";
    public static final String LABELLER_1 = "Labeller-1";
    public static final String SORTER_1 = "Sorter-1";
    public static final String LOADER_1 = "Loader-1";
    public static final String LOADING_BAY_1 = "Loading Bay-1";
    public static final String LOADING_TO_TRUCK_1 = "Loading To Truck-1";
    public static final String TRUCK_1 = "Truck-1";
    public static final String TRUCK_2 = "Truck-2";
    public static final String TRUCK_3 = "Truck-3";
    
    // Counters for IDs
    public static volatile int orderIDCounter = 1;
    public static volatile int boxIDCounter = 1;
    public static volatile int containerIDCounter = 1;
    public static volatile int batchIDCounter = 1;
    
    // Tracking ID
    public static volatile char trackingLetter = 'A';
    public static volatile int trackingNumber = 0; // Will increment to 1 on first use
}
