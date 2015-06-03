package io.lwq.tutorial.concurrency;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * Basic Executor test
 */
public class ExecutorTest {

    @Test
    public void submit(){

        // Init a ExecutorService
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submit a Runnable
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        });
    }

    @Test
    public void shutdown(){

        // Init a ExecutorService
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            System.out.println("attempt to shutdown executor");

            // initiate orderly shutdown
            executor.shutdown();

            // wait termination with timeout
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }

            // force shutdown
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    @Test
    public void testScheduledExecutor() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1500);

        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("Remaining Delay: %sms", remainingDelay);
    }

    @Test
    public void testScheduledExecutorFixedRate(){
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            }
            catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };

        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
    }
}
