// # Labelling Station
// Tasks:
//    a. Each box is assigned a shipping label with destination and tracking.
//    b. Boxes pass through a quality scanner (1 at a time).
// =========================================================================================

public class LabellingStation implements Runnable {
    private int labelledCount = 0;
    private String[] regions = {"Malaysia", "Singapore", "Thailand", "Indonesia"};
    
    @Override
    public void run() {
        Thread.currentThread().setName(ThreadHolder.LABELLER_1);
        
        while (!Thread.currentThread().isInterrupted() && (!ThreadHolder.packingFinished || !ThreadHolder.labellingQueue.isEmpty())) {
            try {
                Box box = ThreadHolder.labellingQueue.poll();
                if (box != null) {
                    // Assign random region
                    String region = regions[(int) (Math.random() * regions.length)];
                    box.setRegion(region);
                    
                    labelledCount++;
                    ThreadHolder.totalBoxesLabelled++;
                    
                    // Print for each order in the box
                    for (Order order : box.getOrders()) {
                        System.out.println("LabellingStation: Labelled Order #" + order.getOrderID() + 
                                            " with Tracking ID #" + box.getTrackingID() + 
                                            " (Thread: " + Thread.currentThread().getName() + ")");
                    }
                    
                    ThreadHolder.sortingQueue.put(box);
                    
                    // Simulate labelling time
                    Thread.sleep(100);
                    
                } else {
                    Thread.sleep(100); // Wait if no boxes available
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Labelling Station interrupted. Shutting down.");
                break;
            }
        }
        
        ThreadHolder.labellingFinished = true;
        System.out.println("Labelling Station Finish Successfully!");
    }
}
