package InterruptingThreads14;

import java.util.concurrent.*;

/**
 * <b>How to interrupt running threads in Java using the built-in thread interruption mechanism.</b>
 * Source:
 * <a href="http://www.javamex.com/tutorials/threads/thread_interruption.shtml">
 * http://www.javamex.com/tutorials/threads/thread_interruption.shtml</a>
 
 * Thread vs software vs hardware interruption:
 * Software: CPU automatically interrupts the current instruction flow in order to call a registered piece of code periodically
 * as in fact happens to drive the thread scheduler
 * Hardware: CPU automatically performs a similar task in response to some hardware signal.
 */
public class App {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting.");
        ExecutorService executor = Executors.newCachedThreadPool();
 
        Future<?> fu = executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (int i = 0; i < 1E8; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.printf("Interrupted at %d !!!", i);
                        break;
                    }
                    // instead of using Thread.currentThread().isInterrupted() to detect interrupt, 
                    // can also use sleep, which may throw InterruptException:
                    /*try {
                        Thread.sleep(1);
                    } catch (InterruptException e) {
                        System.out.println("Interrupted!");
                        break;
                    }*/                    
                }                
                return null;
            }
        });

        executor.shutdown();
        Thread.sleep(500);
        // t1.interrupt() won't stop thread, will only set flag that thread is interrupted

        //JavaDoc: http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Future.html#cancel-boolean-
        //fu.cancel(true);  

        //JavaDoc: http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html#shutdownNow--
        executor.shutdownNow();

        executor.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("Finished.");
    }

}
