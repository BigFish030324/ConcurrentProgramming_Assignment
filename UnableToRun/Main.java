package UnableToRun;
// Orchestrates thread creation and final reporting.

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int total_order = 10;
        BlockingQueue<Order> intakeSystemBlockingQueue = new ArrayBlockingQueue<>(total_order);
        BlockingQueue<Order> packsQueue   = new ArrayBlockingQueue<>(total_order);
        BlockingQueue<Order> labelsQueue  = new ArrayBlockingQueue<>(total_order);
        BlockingQueue<BoxBatch> sortingQueue= new ArrayBlockingQueue<>(total_order);
        BlockingQueue<Container> loadingBayQueue= new ArrayBlockingQueue<>(2);

        BackEnd backend = new BackEnd(total_order);
        ExecutorService exec = Executors.newCachedThreadPool();

        exec.submit(new OrderIntakeSystem(intakeSystemBlockingQueue, total_order, backend));
        for (int i = 1; i <= 3; i++) exec.submit(new PickingStation(intakeSystemBlockingQueue, packsQueue, backend, i));
        for (int i = 1; i <= 2; i++) exec.submit(new PackingStation(packsQueue, labelsQueue, backend, i));
        exec.submit(new LabellingStation(labelsQueue, sortingQueue, backend, 1, 1));
        exec.submit(new SortingArea(sortingQueue, loadingBayQueue, backend, 1));
        for (int i = 1; i <= 3; i++) exec.submit(new LoadingBayAndTransport.Loader(loadingBayQueue, backend, i, i));
        for (int i = 1; i <= 18; i++) exec.submit(new LoadingBayAndTransport.Truck(i, loadingBayQueue, backend));

        Thread a = new Thread(new SortingArea(sortingQueue, loadingBayQueue, backend, 1));
        a.setName("POS");
        a.run();

        exec.shutdown();
        exec.awaitTermination(10, TimeUnit.MINUTES);
        // Final reporting logic can be added here
    }
}
