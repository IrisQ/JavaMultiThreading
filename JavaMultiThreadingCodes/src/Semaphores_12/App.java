package Semaphores_12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * {@link java.util.concurrent.Semaphore}s
 * are mainly used to limit the number of simultaneous threads that
 * can access a resources, but you can also use them to implement deadlock
 * recovery systems since a semaphore with one permit is basically a lock that
 * can unlock from other threads.

 * [Mutex] (or a semaphore initialized to 1; meaning there's only one resource)
 * is basically a mutual exclusion; Only one thread can acquire the resource
 * at once, and all other threads trying to acquire the resource are blocked
 * until the thread owning the resource releases.

 * [Semaphore] is used to control the number of threads executing. There will be
 * fixed set of resources. The resource count will gets decremented every time
 * when a thread owns the same. When the semaphore count reaches 0 then no other
 * threads are allowed to acquire the resource. The threads get blocked till
 * other threads owning resource releases.

 * In short, the main difference is how many threads are allowed to acquire the
 * resource at once.
 * TODO -- go a little more in depth explaining that
 * Mutex --its ONE. Semaphore -- its DEFINED_COUNT, ( as many as semaphore
 * count)
 */
public class App {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 20; i++) {          //create 200 threads
            executor.submit(new Runnable() {
                public void run() {
                    Connectionn.getInstance().connect();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
