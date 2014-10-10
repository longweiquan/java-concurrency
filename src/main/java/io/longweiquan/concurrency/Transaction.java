package io.longweiquan.concurrency;

public interface Transaction {

	int getTotal();
	
	Transaction transfer(int amount) throws InterruptedException;
}
