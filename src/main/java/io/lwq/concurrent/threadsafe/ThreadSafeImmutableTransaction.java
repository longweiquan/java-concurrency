package io.lwq.concurrent.threadsafe;

/**
 * Thread-safe implementation of transaction using the immutable approach.
 * 
 * @author Weiquan LONG
 */
public class ThreadSafeImmutableTransaction implements Transaction {

	private int accountFrom;
	private int accountTo;
    
    public ThreadSafeImmutableTransaction(int accountFrom, int accountTo) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }
    
    /**
     * {@inheritDoc}
     */
    public int getTotal() {
        return accountFrom + accountTo;
    }
    
    /**
     * {@inheritDoc}
     */
    public Transaction transfer(int amount) throws InterruptedException {
    	
    	int newAccountFrom = accountFrom - amount;
    	Thread.sleep(1);
    	int newAccountTo = accountTo + amount;
        return new ThreadSafeImmutableTransaction(newAccountFrom, newAccountTo);
    }
}
