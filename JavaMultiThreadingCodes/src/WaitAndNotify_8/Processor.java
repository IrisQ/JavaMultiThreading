package WaitAndNotify_8;

import java.util.Scanner;

/**
 * Some background knowledge: <em>http://www.programcreek.com/2009/02/notify-and-wait-example/</em>
 * synchronized keyword is used for exclusive accessing. 
 * no two invocations of synchronized methods on the same object can
 * interleave with each other.
 * Synchronized statements must specify the object that
 * provides the intrinsic lock. When {@code synchronized(this)} is used, you
 * have to avoid to synchronizing invocations of other objects' methods.

 * {@link Object#wait()} tells
 * the calling thread to give up the lock and go to sleep (not polling) until
 * some other thread enters the same lock and calls {@link Object#notify()}.

 * {@link Object#notify()} wakes up the first thread that called wait() on
 * the same object.
 */
public class Processor {

    /*
     * public synchronized void getSomething(){ this.hello = "hello World"; }
     * public void getSomething(){ synchronized(this){ this.hello = "hello
     * World"; } }
     * two code blocks by specification, functionally identical.
     */


    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running ....");
            wait();                             // more resource efficient than simply use while loop.
            System.out.println("Resumed.");
        }
    }

    public void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);         // make sure produce() before comsume()
        synchronized (this) {
            System.out.println("Waiting for return key.");
            scanner.nextLine();     // wait till press return key on keyboard
            System.out.println("Return key pressed.");
            notify();               // can only be called in synchronized, call all other threads, if they are waiting, they can wake up
            Thread.sleep(5000);     // this will finish first, before another thread can access
            System.out.println("Consumption done.");
        }
    }
}
