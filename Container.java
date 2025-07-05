// Holds a collection of orders batched into a shipping container.

import java.util.ArrayList;
import java.util.List;

public class Container {
    private final List<Order> contents = new ArrayList<>();
    
    public void addOrders(List<Order> orders) {
        contents.addAll(orders);
    }
}