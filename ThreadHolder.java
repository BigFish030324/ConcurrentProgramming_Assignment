import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadHolder {
    
    // Counters for statistics
    public static BlockingQueue<Box> sortingQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Container> loadingBayQueue = new LinkedBlockingQueue<>();
    
    // Counters for statistics
    public static volatile int totalBoxesSorted = 0;
    
    // If thread is completed
    public static volatile boolean labellingFinished = false;
    public static volatile boolean sortingFinished = false;
    
    // Thread Names
    public static final String SORTER_1 = "Sorter-1";
    
    // Counters for IDs
    public static volatile int containerIDCounter = 1;
    public static volatile int batchIDCounter = 1;
    
}
