public class Reject {
    
    public static void rejectOrder(Order order, String stage) {
        ThreadHolder.totalOrdersRejected++;
        System.out.println(stage + ": Order #" + order.getOrderID() + " rejected (Thread: " + Thread.currentThread().getName() + ")");
    }
    
    public static void rejectBox(Box box, String stage) {
        System.out.println(stage + ": Box #" + box.getBoxID() + " rejected (Thread: " + Thread.currentThread().getName() + ")");
    }
    
    public static boolean shouldReject(double rejectChance) {
        return Math.random() < rejectChance;
    }
}
