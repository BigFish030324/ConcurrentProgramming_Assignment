// # Batch
// - Create batch's component
// - Each batch can have maximum 6 boxes

// =========================================================================================

import java.util.ArrayList;
import java.util.List;

public class Batch {
    private int batchID;
    private List<Box> boxes;
    private String region;
    
    public Batch(int batchID, String region) {
        this.batchID = batchID;
        this.region = region;
        this.boxes = new ArrayList<>();
    }
    
    public int getBatchID() {
        return batchID;
    }
    
    public String getRegion() {
        return region;
    }
    
    public List<Box> getBoxes() {
        return boxes;
    }
    
    public void addBox(Box box) {
        if (boxes.size() < 6) {
            boxes.add(box);
        }
    }
    
    public boolean isFull() {
        return boxes.size() >= 6;
    }
    
    @Override
    public String toString() {
        return "Batch #" + batchID + " (" + region + ")";
    }
}
