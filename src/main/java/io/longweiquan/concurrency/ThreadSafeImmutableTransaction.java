package io.longweiquan.concurrency;

/**
 * Thread safe implementation of transaction using the immutable approach.
 *
 */
public class ThreadSafeImmutableTransaction implements Transaction {

	private int accountFrom;
	private int accountTo;
    
    public ThreadSafeImmutableTransaction(int accountFrom, int accountTo) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }
    
   
    public int getTotal() {
        return accountFrom + accountTo;
    }
    
    public Transaction transfer(int amount) throws InterruptedException {
    	
    	int newAccountFrom = accountFrom - amount;
    	Thread.sleep(1);
    	int newAccountTo = accountTo + amount;
        return new ThreadSafeImmutableTransaction(newAccountFrom, newAccountTo);
    }
}
