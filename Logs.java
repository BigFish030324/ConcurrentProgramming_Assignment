// Centralized logging utility class

public class Logs {
    // Implement `synchronized` to ensure ONLY one thread at a time
    public static synchronized void log(String message) {
        System.out.println(message);
    }
}
