import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadHolder {
    // Queues
    public static BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Order> packingQueue = new LinkedBlockingQueue<>();
    
    // Counters for statistics
    public static volatile int totalOrdersPicked = 0;
    public static volatile int totalOrdersPacked = 0;
    
    // If thread is completed
    public static volatile boolean orderIntakeFinished = false;
    public static volatile boolean pickingFinished = false;
    public static volatile boolean packingFinished = false;
    
    // Thread Names
    public static final String PICKER_1 = "Picker-1";
    public static final String PICKER_2 = "Picker-2";
    public static final String PICKER_3 = "Picker-3";
    public static final String PICKER_4 = "Picker-4";
    
    // Counters for IDs
    public static volatile int boxIDCounter = 1;
}
