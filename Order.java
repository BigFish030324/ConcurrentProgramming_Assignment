// # Order
// - Create order's component

// =========================================================================================

public class Order {
    private int orderID;
    
    public Order(int orderID) {
        this.orderID = orderID;
    }
    
    public int getOrderID() {
        return orderID;
    }
    
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    
    @Override
    public String toString() {
        return "Order #" + orderID;
    }
}
