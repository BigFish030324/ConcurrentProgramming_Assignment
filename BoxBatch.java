// Wraps an order into an unable to modify list for batching in the sorting area

import java.util.Collections;
import java.util.List;

public class BoxBatch {

    // Definition Section
    private final List<Order> orders;

    // Constructor Section
    // Accept an order and make it unable to modify
    public BoxBatch(Order order) { 
        this.orders = Collections.singletonList(order);
    }

    // Getter Function
    // Allow access to the "unable to modify" list
    public List<Order> getOrders() { 
        return orders; 
    }
}
