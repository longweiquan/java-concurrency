package io.lwq.tutorial.thread;

import org.junit.Test;

/**
 * Basic Thread test.
 */
public class ThreadTest {

    @Test
    public void start(){

        // Runnable is the interface of thread
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " : Hello!");
        };

        // A Runnable runs in main thread by default
        // same as thread.run()
        task.run();

        // A Runnable can run in a specific thread
        Thread thread = new Thread(task);

        // Start Thread
        thread.start();

        // This out runs after the Runnable because they are in the same thread
        System.out.println("main : Done!");
    }

    @Test
    public void sleepAndJoin() throws InterruptedException {

        // A Runnable with sleep
        Runnable runnable = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println(name + " : before sleep");
                Thread.sleep(1000);
                System.out.println(name + " : after sleep");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Start Thread
        Thread thread = new Thread(runnable);
        thread.start();

        // Main thread wait for other thread's termination
        thread.join();
    }

    @Test
    public void interrupt(){

        Thread thread = new Thread(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " : sleep");
            try {
                Thread.sleep(5000);
                System.out.println(name + " : not interrupted");
            } catch (InterruptedException e) {
                System.out.println(name + " : interrupted");
            }
        });
        thread.start();

        // Interrupt Thread
        thread.interrupt();
    }
}
