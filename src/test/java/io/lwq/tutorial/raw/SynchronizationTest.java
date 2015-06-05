package io.lwq.tutorial.raw;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Basic Synchronization Test.
 *
 * Synchronized code relies implicitly on ReentrantLock
 *
 * @see LockTest
 */
public class SynchronizationTest {

    private Logger logger = Logger.getLogger(SynchronizationTest.class);

    private int TOTAL = 100;

    @Test
    public void notSynchronizedMethod() throws InterruptedException {

        Counter counter = new Counter();

        counter.incInParallel(() -> {
            try {
                counter.inc();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }, TOTAL);
        Assert.assertNotEquals(TOTAL, counter.getCount());
    }

    @Test
    public void synchronizedMethod() throws InterruptedException {

        Counter counter = new Counter();
        counter.incInParallel(() -> {
            try {
                counter.incSyncMethod();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }, TOTAL);
        Assert.assertEquals(TOTAL, counter.getCount());
    }

    @Test
    public void synchronizedBlock() throws InterruptedException {

        Counter counter = new Counter();
        counter.incInParallel(() -> {
            try {
                counter.incSyncBlock();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }, TOTAL);

        Assert.assertEquals(TOTAL, counter.getCount());
    }

    public class Counter {

        private Integer count = 0;
        private Object lock = new Object();

        public int getCount(){
            return count;
        }

        public synchronized void incSyncMethod() throws InterruptedException {
            inc();
        }

        public void incSyncBlock() throws InterruptedException {
            synchronized (lock){
                inc();
            }
        }

        public void inc() throws InterruptedException {
            int temp = count;
            Thread.sleep(1);
            count = temp + 1;
        }

        public void incInParallel(Runnable task, int total) throws InterruptedException {
            ExecutorService pool = Executors.newFixedThreadPool(5);

            for(int i=0; i<total; i++){
                pool.submit(task);
            }
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.MINUTES);
        }
    }

}
