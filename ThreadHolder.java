import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadHolder {
    // Queues
    public static BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    
    // Counters for statistics
    public static volatile int totalOrdersGenerated = 0;
    public static volatile int totalOrdersRejected = 0;
    
    // Inventory
    public static volatile int inventoryOrders = 20;
    
    // If thread is completed
    public static volatile boolean orderIntakeFinished = false;
    
    // Thread Names
    public static final String ORDER_THREAD = "OrderThread-1";
    
    // Counters for IDs
    public static volatile int orderIDCounter = 1;
}
