package Semaphores_12;

import java.util.concurrent.Semaphore;
public class Connection {

    private static Connection instance = new Connection();
    // [Semaphore] set to true: will guarantee FIFO granting of permits, make sure no server wait too long.
    private Semaphore sem = new Semaphore(10, true);   
    private int connections = 0;        // # of connections in any given time
    private Connection() {
    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire();  // sem value--, if 0 wait for release
            doConnect();    // might throw exception, so put sem.release in finally block to guarantee release
        } catch (InterruptedException ignored) {
        } finally { 
            sem.release();  // sem value++, activate waiting thread
        }
    }

    public void doConnect() {
        synchronized (this) { //atomic
            connections++;
            System.out.println("Current connections (max 10 allowed): " + connections);
        }
        try {
            System.out.println("Working on connections " + Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}
        //when exit doConnect method decrement number of connections
        synchronized (this) {
            connections--;
            System.out.println("I'm done " + Thread.currentThread().getName() + " Connection is released , connection count: " + connections);
        }
    }
}
