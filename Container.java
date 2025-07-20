import java.util.ArrayList;
import java.util.List;

public class Container {
    private int containerID;
    private List<Box> boxes;
    
    public Container(int containerID) {
        this.containerID = containerID;
        this.boxes = new ArrayList<>();
    }
    
    public int getContainerID() {
        return containerID;
    }
    
    public List<Box> getBoxes() {
        return boxes;
    }
    
    public void addBox(Box box) {
        if (boxes.size() < 30) {
            boxes.add(box);
        }
    }
    
    public boolean isFull() {
        return boxes.size() >= 30;
    }
    
    @Override
    public String toString() {
        return "Container #" + containerID;
    }
}
