package io.lwq.tutorial.thread;

import io.lwq.concurrent.threadsafe.Transaction;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Basic Synchronization Test
 */
public class SynchronizationTest {

    private Logger logger = Logger.getLogger(SynchronizationTest.class);

    private final static int TOTAL = 100000;

    private SynchronizedTransaction synchronizedTransaction = new SynchronizedTransaction(TOTAL, 0);

    @Test
    public void synchronizedMethod() throws InterruptedException {

        Runnable task = () -> {
            try {
                synchronizedTransaction.transfer(1);
            } catch (InterruptedException e) {
                logger.info("interrupted");
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(5);

        for(int i=0; i<100; i++){
            pool.submit(task);
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        Assert.assertEquals(TOTAL, synchronizedTransaction.getTotal());
    }




    public class SynchronizedTransaction {

        private int accountFrom;
        private int accountTo;

        public SynchronizedTransaction(int accountFrom, int accountTo) {
            this.accountFrom = accountFrom;
            this.accountTo = accountTo;
        }

        public synchronized int getTotal() {
            return accountFrom + accountTo;
        }

        public synchronized void transfer(int amount) throws InterruptedException {
            accountFrom -= amount;
            Thread.sleep(1);
            accountTo += amount;
        }
    }

}
