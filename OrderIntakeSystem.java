// Order Intake System
// Task:
// - Order arrive from the online platform at a rate of 1 order every 500ms
// - Each order is verified for payment, inventory availability and shipping address

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class OrderIntakeSystem implements Runnable {

    // Defination Section
    private static int order_ms = 10; // Will change to 500 to meet the question requirement, currently put 10 for testing
    private BlockingQueue<Order> intakeSystemBlockingQueue;
    private int totalOrder;
    private Random random;
    private Statistics statistics;

    // Constructor Section
    public OrderIntakeSystem(BlockingQueue<Order> intakeSystemBlockingQueue, int totalOrder, Statistics statistics) {
        this.intakeSystemBlockingQueue = intakeSystemBlockingQueue;
        this.totalOrder = totalOrder;
        this.statistics = statistics;
        this.random = new Random();
    }

    // Runnable Section
    @Override
    public void run() {
        // For loop to simulate 1 to totalOrder number (Question: 500 orders)
        for(int id = 1; id <= totalOrder; id++) {
            try {
                // Delays each order based on order_ms (Question: 500ms) miliseconds
                Thread.sleep(order_ms);
                Order order = new Order(id);

                // Simulate there is a 5% rejection rate regarding to the total orders
                if (random.nextDouble() <= 0.05) {
                    // Code to reject order
                    // Will do afterwards (Statistics will be implemented here)
                    Logs.log("OrderIntake: Order #" + id + " rejected (Thread: " + Thread.currentThread().getName() + ")");
                } else {
                    // Thread wait for their turns using put() which helps in ensuring one thread inserts in a time
                    intakeSystemBlockingQueue.put(order);
                    Logs.log("OrderIntake: Order #" + id + " received (Thread: " + Thread.currentThread().getName() + ")");
                }
            } catch (InterruptedException e) {
                // Handle exception
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
