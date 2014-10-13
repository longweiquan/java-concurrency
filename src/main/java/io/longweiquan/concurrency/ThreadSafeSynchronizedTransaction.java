package io.longweiquan.concurrency;

/**
 * Thread safe implementation of transaction using the synchronized approach.
 *
 * @author Weiquan LONG
 */
public class ThreadSafeSynchronizedTransaction implements Transaction {

	private int accountFrom;
	private int accountTo;
    
    public ThreadSafeSynchronizedTransaction(int accountFrom, int accountTo) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }
    
    public synchronized int getTotal() {
        return accountFrom + accountTo;
    }
    
    public synchronized Transaction transfer(int amount) throws InterruptedException {   
    	accountFrom -= amount;
    	Thread.sleep(1);
    	accountTo += amount;
        return this;
    }
}
