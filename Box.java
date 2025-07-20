import java.util.ArrayList;
import java.util.List;

public class Box {
    private int boxID;
    private String trackingID;
    private String region;
    private List<Order> orders;
    
    public Box(int boxID) {
        this.boxID = boxID;
        this.orders = new ArrayList<>();
        generateTrackingID();
    }
    
    private void generateTrackingID() {
        this.trackingID = generateNextTrackingID();
    }
    
    public static synchronized String generateNextTrackingID() {
        ThreadHolder.trackingNumber++;
        
        if (ThreadHolder.trackingNumber > 999) {
            ThreadHolder.trackingNumber = 1;
            ThreadHolder.trackingLetter++;
            
            if (ThreadHolder.trackingLetter > 'Z') {
                ThreadHolder.trackingLetter = 'A'; // Reset to A if it goes beyond Z
            }
        }
        
        return ThreadHolder.trackingLetter + String.format("%03d", ThreadHolder.trackingNumber);
    }
    
    public int getBoxID() {
        return boxID;
    }
    
    public String getTrackingID() {
        return trackingID;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public List<Order> getOrders() {
        return orders;
    }
    
    public void addOrder(Order order) {
        if (orders.size() < 20) {
            orders.add(order);
        }
    }
    
    public boolean isFull() {
        return orders.size() >= 20;
    }
    
    @Override
    public String toString() {
        return "Box #" + boxID + " (Tracking: " + trackingID + ", Region: " + region + ")";
    }
}
