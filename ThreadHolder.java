import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadHolder {
    // Queues
    public static BlockingQueue<Order> packingQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Box> labellingQueue = new LinkedBlockingQueue<>();
    
    // Counters for statistics
    public static volatile int totalOrdersPacked = 0;
    
    // If thread is completed
    public static volatile boolean pickingFinished = false;
    public static volatile boolean packingFinished = false;
    
    // Thread Names
    public static final String PACKER_1 = "Packer-1";
}
