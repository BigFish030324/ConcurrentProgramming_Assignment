public class PackingStation implements Runnable {
    private int packedCount = 0;
    private Box currentBox;
    
    @Override
    public void run() {
        Thread.currentThread().setName(ThreadHolder.PACKER_1);
        
        while (!Thread.currentThread().isInterrupted() && (!ThreadHolder.pickingFinished || !ThreadHolder.packingQueue.isEmpty())) {
            try {
                Order order = ThreadHolder.packingQueue.poll();
                if (order != null) {
                    // Create new box if current is null or full
                    if (currentBox == null || currentBox.isFull()) {
                        if (currentBox != null) {
                            // Send current box to labelling
                            ThreadHolder.labellingQueue.put(currentBox);
                        }
                        currentBox = new Box(ThreadHolder.boxIDCounter++);
                    }
                    
                    // Add order to current box
                    currentBox.addOrder(order);
                    packedCount++;
                    ThreadHolder.totalOrdersPacked++;
                    
                    System.out.println("PackingStation: Packed Order #" + order.getOrderID() + " (Thread: " + Thread.currentThread().getName() + ")");
                    
                    // Simulate packing time
                    Thread.sleep(50);
                    
                } else {
                    Thread.sleep(100); // Wait if no orders available
                }
                
                // Check if we need to mark picking as finished
                if (ThreadHolder.orderIntakeFinished && ThreadHolder.orderQueue.isEmpty() && ThreadHolder.packingQueue.isEmpty()) {
                    ThreadHolder.pickingFinished = true;
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Packing Station interrupted. Shutting down.");
                break;
            }
        }
        
        // Send the last box if it has orders
        if (currentBox != null && !currentBox.getOrders().isEmpty()) {
            try {
                ThreadHolder.labellingQueue.put(currentBox);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        ThreadHolder.packingFinished = true;
        System.out.println("Packing Station Finish Successfully!");
    }
}
