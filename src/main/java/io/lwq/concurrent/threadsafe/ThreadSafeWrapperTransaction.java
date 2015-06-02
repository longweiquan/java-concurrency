package io.lwq.concurrent.threadsafe;

/**
 * Thread safe implementation of RGB Color using the wrapper approach.
 *
 * @author Weiquan LONG
 */
public class ThreadSafeWrapperTransaction implements Transaction {

	private NotThreadSafeTransaction transaction;
    
	public ThreadSafeWrapperTransaction(int accountFrom, int accountTo) {
		 transaction = new NotThreadSafeTransaction(accountFrom, accountTo);
	}
	
	public synchronized int getTotal() {
		return transaction.getTotal();
    }
    
    public synchronized Transaction transfer(int amount) throws InterruptedException {   
    	transaction.transfer(amount);
    	return this;
    }
}
