package io.longweiquan.concurrency;


/**
 * Thread not safe implementation of transaction. 
 */
public class NotThreadSafeTransaction implements Transaction {
	
	private int accountFrom;
	private int accountTo;
    
    public NotThreadSafeTransaction(int accountFrom, int accountTo) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }
    
    /**
     * Not thread safe.
     */
    public int getTotal() {
        return accountFrom + accountTo;
    }
    
    /**
     * Not thread safe.
     * @throws InterruptedException 
     */
    public Transaction transfer(int amount) throws InterruptedException {
    	accountFrom -= amount;
    	Thread.sleep(1);
    	accountTo += amount;
        return this;
    }
}
