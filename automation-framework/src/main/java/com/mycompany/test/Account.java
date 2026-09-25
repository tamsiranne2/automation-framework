package com.mycompany.test;

public class Account {
	private double balance = 0.0;
    public void deposit(double amount) {
        // Left empty intentionally for the RED phase
    	//now green phase
    	//refactored with negative deposit exception
    	if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
    	this.balance += amount;
    }

    public double getBalance() {
       // return 0.0; // Returns default value so it compiles, but test fails
        //green phase
        return this.balance;
    }
}