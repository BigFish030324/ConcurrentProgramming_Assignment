import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadHolder {
    // Counters for statistics
    public static BlockingQueue<Box> labellingQueue = new LinkedBlockingQueue<>();
    public static BlockingQueue<Box> sortingQueue = new LinkedBlockingQueue<>();
    
    // Counters for statistics
    public static volatile int totalBoxesLabelled = 0;
    
    // If thread is completed
    public static volatile boolean packingFinished = false;
    public static volatile boolean labellingFinished = false;
    
    // Counters for IDs
    public static final String LABELLER_1 = "Labeller-1";
}
