package CountDownLatch_6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Any thread, usually main thread of application, which calls
 * latch.await() will wait until count reaches zero or interrupted
 * by another thread. All other thread are required to do count down by calling
 * CountDownLatch#countDown() once they are completed or ready.

 * As soon as count reaches zero, Thread awaiting starts running.

 * Classical example usage is, when using services architecture, where multiple services
 * are provided by multiple threads, application can not start processing
 * until all services have started successfully.
 */
class Processor implements Runnable {

    private CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        System.out.println("Started.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}
        latch.countDown();  // latch--
    }
}

public class App {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executor.submit(new Processor(latch));
        }
        executor.shutdown();

        try {
            latch.await();      // waits till CountDownLatch to 0 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed.");
    }

}
