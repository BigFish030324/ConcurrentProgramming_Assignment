public class PickingStation implements Runnable {
    private String pickerName;
    private int pickedCount = 0;
    
    public PickingStation(String pickerName) {
        this.pickerName = pickerName;
    }
    
    @Override
    public void run() {
        Thread.currentThread().setName(pickerName);
        
        while (!ThreadHolder.orderIntakeFinished || !ThreadHolder.orderQueue.isEmpty()) {
            try {
                Order order = ThreadHolder.orderQueue.poll();
                if (order != null) {
                    // Simulate picking time
                    Thread.sleep(100);
                    
                    // 2% chance to reject order
                    if (Reject.shouldReject(0.02)) {
                        System.out.println("PickingStation: Picking Order Rejected #" + order.getOrderID() + " (Thread: " + Thread.currentThread().getName() + ")");
                    } else {
                        ThreadHolder.packingQueue.put(order);
                        pickedCount++;
                        ThreadHolder.totalOrdersPicked++;
                        System.out.println("PickingStation: Picking Order #" + order.getOrderID() + " (Thread: " + Thread.currentThread().getName() + ")");
                    }
                } else {
                    Thread.sleep(100); // Wait if no orders available
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.println("Picking Station Finish Successfully! (Thread: " + Thread.currentThread().getName() + ")");
    }
    
    public static void startPickingStations() {
        Thread picker1 = new Thread(new PickingStation(ThreadHolder.PICKER_1));
        Thread picker2 = new Thread(new PickingStation(ThreadHolder.PICKER_2));
        Thread picker3 = new Thread(new PickingStation(ThreadHolder.PICKER_3));
        Thread picker4 = new Thread(new PickingStation(ThreadHolder.PICKER_4));
        
        picker1.start();
        picker2.start();
        picker3.start();
        picker4.start();
    }
}
