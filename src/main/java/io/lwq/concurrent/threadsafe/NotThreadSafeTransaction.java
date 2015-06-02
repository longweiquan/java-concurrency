package io.lwq.concurrent.threadsafe;


/**
 * Non thread-safe implementation of transaction.
 * 
 * @author Weiquan LONG
 */
public class NotThreadSafeTransaction implements Transaction {
	
	private int accountFrom;
	private int accountTo;
    
    public NotThreadSafeTransaction(int accountFrom, int accountTo) {
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
    	accountFrom -= amount;
    	Thread.sleep(1);
    	accountTo += amount;
        return this;
    }
}
