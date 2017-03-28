package CallableAndFuture_13;

import java.util.Random;
import java.util.concurrent.*;

/**
 * {@link java.util.concurrent.Callable} and
 * {@link java.util.concurrent.Future}
 * in Java to get results from your threads and to allow
 * your threads to throw exceptions. Plus, Future allows you to control your
 * threads, checking to see if they’re running or not, waiting for results and
 * even interrupting them or de-scheduling them.

 * {@link java.lang.Runnable}
 * is the default abstraction for creating a task in Java. It has a single
 * method {@link Runnable#run()}
 * that accepts no arguments and returns no value, nor it can throw
 * any checked exception. To overcome these limitations, Java 5 introduced a new
 * task abstraction through {@link java.util.concurrent.Callable} interface.
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        // Callable instead of Runnable, can return result instead of void
        // if don't want to use return value now, but may use it in the future:
        // Future<?> future = executor.submit(new Callable<Void>() {  
        Future<Integer> future = executor.submit(new Callable<Integer>() {  
        
            @Override
            public Integer call() throws TimeoutException {     
                Random random = new Random();
                int duration = random.nextInt(4000);
                if (duration > 2000) {
                    throw new TimeoutException ("Sleeping for too long.");  
                }

                System.out.println("Starting ...");
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException ignored) {}
                System.out.println("Finished.");
                return duration;
            }
        });

        executor.shutdown();
        // can wait for threads to finish, if not, when use future.get() it will block till threads finish
        // executor.awaitTermination(1, TimeUnit.DAYS);    
        try {            
            System.out.println("Result is: " + future.get());   //get returned value from call()
        } catch (InterruptedException ignored) {
        } catch (ExecutionException e) {
            TimeoutException ex = (TimeoutException) e.getCause();
            System.out.println(ex.getMessage());                // Will print "sleeping for too long"
        }
    }

}
