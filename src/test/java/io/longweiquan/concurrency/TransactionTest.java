package io.longweiquan.concurrency;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TransactionTest {
	
	private final static int TOTAL = 100000;
	
	private Transaction notThreadSafeTransaction = new NotThreadSafeTransaction(TOTAL, 0);
	
	private Transaction threadSafeImmutableTransaction = new ThreadSafeImmutableTransaction(TOTAL, 0);
	
	private Transaction threadSafeSynchronizedTransaction = new ThreadSafeSynchronizedTransaction(TOTAL, 0);
	
	private Transaction threadSafeWrapperTransaction = new ThreadSafeWrapperTransaction(TOTAL, 0);
	
	
	// Uncomment the line below to test the not thread-safe version
	//@Test(invocationCount = 50, threadPoolSize=2)
	public void testNotThreadSafeTransaction() throws InterruptedException{
	
		notThreadSafeTransaction = notThreadSafeTransaction.transfer(1);
		Assert.assertEquals(TOTAL, notThreadSafeTransaction.getTotal());
	}
	
	@Test(invocationCount = 50, threadPoolSize=2)
	public void testThreadSafeImmutableTransaction() throws InterruptedException{
	
		threadSafeImmutableTransaction = threadSafeImmutableTransaction.transfer(1);
		Assert.assertEquals(TOTAL, threadSafeImmutableTransaction.getTotal());
	}

	@Test(invocationCount = 50, threadPoolSize=2)
	public void testThreadSafeSynchronizedTransaction() throws InterruptedException{
	
		threadSafeSynchronizedTransaction = threadSafeSynchronizedTransaction.transfer(1);
		Assert.assertEquals(TOTAL, threadSafeSynchronizedTransaction.getTotal());
	}
	
	
	@Test(invocationCount = 50, threadPoolSize=2)
	public void testThreadSafeWrapperTransaction() throws InterruptedException{
	
		threadSafeWrapperTransaction = threadSafeWrapperTransaction.transfer(1);
		Assert.assertEquals(TOTAL, threadSafeWrapperTransaction.getTotal());
		
	}
	

}
