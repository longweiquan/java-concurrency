package io.lwq.tutorial.thread;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Basic Synchronization Test
 */
public class SynchronizationTest {

    private Logger logger = Logger.getLogger(SynchronizationTest.class);

    private int TOTAL = 100;

    @Test
    public void notSynchronizedMethod() throws InterruptedException {

        Counter counter = new Counter();

        Runnable task = () -> {
            try {
                counter.inc();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(5);

        for(int i=0; i<TOTAL; i++){
            pool.submit(task);
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        Assert.assertNotEquals(TOTAL, counter.getCount());
    }

    @Test
    public void synchronizedMethod() throws InterruptedException {

        Counter counter = new Counter();

        Runnable task = () -> {
            try {
                counter.incSyncMethod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(5);

        for(int i=0; i<TOTAL; i++){
            pool.submit(task);
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        Assert.assertEquals(TOTAL, counter.getCount());
    }

    @Test
    public void synchronizedSate() throws InterruptedException {

        Counter counter = new Counter();

        Runnable task = () -> {
            try {
                counter.incSyncState();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(5);

        for(int i=0; i<TOTAL; i++){
            pool.submit(task);
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        Assert.assertEquals(TOTAL, counter.getCount());
    }

    public class Counter {

        private Integer count = 0;
        private Object lock;

        public int getCount(){
            return count;
        }

        public synchronized void incSyncMethod() throws InterruptedException {
            inc();
        }

        public void incSyncState() throws InterruptedException {
            synchronized (lock){
                inc();
            }
        }

        public void inc() throws InterruptedException {
            int temp = count;
            Thread.sleep(1);
            count = temp + 1;
        }
    }

}
