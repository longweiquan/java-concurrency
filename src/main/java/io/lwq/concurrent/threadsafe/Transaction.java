package io.lwq.concurrent.threadsafe;

/**
 * This interface is used to explain how to implement a thread-safe class. We
 * will simulate the transaction from one account to another. A thread-safe
 * implementation can keep their sum always the same, but a non thread-safe
 * implementation can not.
 * 
 * @author Weiquan LONG
 *
 */
public interface Transaction {

	/**
	 * @return the sum of two accounts
	 */
	int getTotal();

	/**
	 * Transfer an amount from one account to another.
	 * @param amount amount to transfer
	 * @return the transaction
	 * @throws InterruptedException thrown by Thread.sleep 
	 */
	Transaction transfer(int amount) throws InterruptedException;
}
