package UnableToRun;
// Holds a collection of orders batched into a shipping container.

import java.util.ArrayList;
import java.util.List;

public class Container {
    private final int id;
    private final List<Order> contents = new ArrayList<>();
    
    public Container (int id) {this.id = id;}

    public void addOrders(List<Order> orders) {
        contents.addAll(orders);
    }

    @Override
    public String toString(){return "Container-"+id;}

}