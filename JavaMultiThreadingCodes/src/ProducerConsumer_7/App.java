package ProducerConsumer_7;

/**
 * Producer-Consumer pattern in Java using the java.util.concurrent.ArrayBlockingQueue
 * Producer-Consumer is the situation where one or more threads are producing
 * data items and adding them to a shared data store of some kind while one or
 * more other threads process those items, removing them from the data store.
 */
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("InfiniteLoopStatement")
public class App {

    /**
     * Thread safe implementation of java.util.Queue
     * java.util.concurrent package are all thread-safe
     */
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    producer();
                } catch (InterruptedException ignored) {}
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException ignored) {}
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // Pause for 30 seconds and force quitting the app (because we're
        // looping infinitely)
        Thread.sleep(30000);
        System.exit(0);
    }
    
    /** producer add integer into queue, and consumer take from queue and send into destination 
     */
    private static void producer() throws InterruptedException {
        Random random = new Random();
        while (true) {                          
            queue.put(random.nextInt(100));         //if queue is full (10 here) waits
        }
    }
    
    private static void consumer() throws InterruptedException {
        Random random = new Random();
        while (true) {
            Thread.sleep(100);
            if (random.nextInt(10) == 0) {
                Integer value = queue.take();       // if queue is empty waits
                System.out.println("Taken value: " + value + "; Queue size is: " + queue.size());
            }
        }
    }
}
