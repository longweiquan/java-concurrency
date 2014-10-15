package io.longweiquan.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import io.longweiquan.concurrency.threadsafe.NotThreadSafeTransaction;
import io.longweiquan.concurrency.threadsafe.ThreadSafeImmutableTransaction;
import io.longweiquan.concurrency.threadsafe.ThreadSafeSynchronizedTransaction;
import io.longweiquan.concurrency.threadsafe.ThreadSafeWrapperTransaction;
import io.longweiquan.concurrency.threadsafe.Transaction;



public class TransactionTest {
	
	private Logger logger = Logger.getLogger(TransactionTest.class);
	
	private final static int TOTAL = 100000;
	
	private Transaction notThreadSafeTransaction = new NotThreadSafeTransaction(TOTAL, 0);
	
	private Transaction threadSafeImmutableTransaction = new ThreadSafeImmutableTransaction(TOTAL, 0);
	
	private Transaction threadSafeSynchronizedTransaction = new ThreadSafeSynchronizedTransaction(TOTAL, 0);
	
	private Transaction threadSafeWrapperTransaction = new ThreadSafeWrapperTransaction(TOTAL, 0);
	
	private void execute(Callable<Transaction> task, int invocationCount, int threadPoolSize) throws InterruptedException{
		ExecutorService pool = Executors.newFixedThreadPool(threadPoolSize);
		
		for(int i=0; i<invocationCount; i++){
			pool.submit(task);
		}
		pool.shutdown();
		pool.awaitTermination(1, TimeUnit.MINUTES);
	}

	// Uncomment the line below to test the not thread-safe version
	@Test
	public void testNotThreadSafeTransaction() throws InterruptedException{
	
		Callable<Transaction> task = new Callable<Transaction>(){

			public Transaction call() throws Exception {
				notThreadSafeTransaction = notThreadSafeTransaction.transfer(1);
				return null;
			}
		};
		execute(task, 100, 5);
		logger.info("Total :" + notThreadSafeTransaction.getTotal());
		Assert.assertNotEquals(TOTAL, notThreadSafeTransaction.getTotal());
	}
	
	@Test
	public void testThreadSafeImmutableTransaction() throws InterruptedException{
	
		Callable<Transaction> task = new Callable<Transaction>(){

			public Transaction call() throws Exception {
				threadSafeImmutableTransaction = threadSafeImmutableTransaction.transfer(1);
				return threadSafeImmutableTransaction;
			}
		};
		execute(task, 100, 5);
		Assert.assertEquals(TOTAL, threadSafeImmutableTransaction.getTotal());
	}

	@Test
	public void testThreadSafeSynchronizedTransaction() throws InterruptedException{
	
		Callable<Transaction> task = new Callable<Transaction>(){

			public Transaction call() throws Exception {
				threadSafeSynchronizedTransaction = threadSafeSynchronizedTransaction.transfer(1);
				return threadSafeSynchronizedTransaction;
			}
		};
		execute(task, 100, 5);
		Assert.assertEquals(TOTAL, threadSafeSynchronizedTransaction.getTotal());
	}
	
	
	@Test
	public void testThreadSafeWrapperTransaction() throws InterruptedException{
	
		Callable<Transaction> task = new Callable<Transaction>(){

			public Transaction call() throws Exception {
				threadSafeWrapperTransaction = threadSafeWrapperTransaction.transfer(1);
				return threadSafeWrapperTransaction;
			}
		};
		execute(task, 100, 5);
		Assert.assertEquals(TOTAL, threadSafeWrapperTransaction.getTotal());
		
	}
	

}
