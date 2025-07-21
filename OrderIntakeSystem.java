// # Order Intake System
// Tasks:
//    a. Orders arrive from the online platform at a rate of 1 order every 500ms.
//    b. Each order is verified for payment, inventory availability, and shipping address.

// *****************************Additional Requirements****************************

// # Defective Orders
// - Orders may be rejected at any stage (e.g., out-of-stock items, packing errors,
// mislabelling).
// - Defective orders are removed by a reject handler and logged.

// =========================================================================================

public class OrderIntakeSystem implements Runnable {
    private int targetOrders;
    
    public OrderIntakeSystem(int targetOrders) {
        this.targetOrders = targetOrders;
    }
    
    @Override
    public void run() {
        
        int ordersProcessed = 0;

        Thread.currentThread().setName(ThreadHolder.ORDER_THREAD);
        
        while (ordersProcessed < targetOrders) {
            try {
                // Generate order
                Order order;
                
                // Check inventory first
                if (ThreadHolder.inventoryOrders > 0) {
                    ThreadHolder.inventoryOrders--;
                    order = new Order(ThreadHolder.orderIDCounter++);
                } else {
                    order = new Order(ThreadHolder.orderIDCounter++);
                }
                
                // Verify payment, inventory, and shipping (5% rejection rate)
                if (Reject.shouldReject(0.05)) {
                    Reject.rejectOrder(order, "OrderIntake");
                } else {
                    ThreadHolder.orderQueue.put(order);
                    ThreadHolder.totalOrdersGenerated++;
                    System.out.println("OrderIntake: Order #" + order.getOrderID() + " received (Thread: " + Thread.currentThread().getName() + ")");
                }
                
                ordersProcessed++;
                
                // Wait 500ms between orders
                Thread.sleep(500);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        ThreadHolder.orderIntakeFinished = true;
        System.out.println("Order Intake System Finish Successfully!");
    }
}
