import java.util.List;

/**
 * Source: https://howtodoinjava.com/java/multi-threading/wait-notify-and-notifyall-methods/
 */
public class Consumer implements Runnable {

    private final List<Integer> taskQueue;

    Consumer(List<Integer> sharedQueue) {
        this.taskQueue = sharedQueue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                consume();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void consume() throws InterruptedException {
        synchronized (taskQueue) {
            while (taskQueue.isEmpty()) {
                System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting , size: " + taskQueue.size());
                taskQueue.wait();
            }
            Thread.sleep(1000);
            int i = taskQueue.remove(0);
            System.out.println("Consumed: " + i);
            taskQueue.notifyAll();
        }
    }
}
