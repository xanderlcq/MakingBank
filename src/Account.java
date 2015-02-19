import java.math.*;

import acm.program.*;

public class Account {
	private String owner;
	private double balance;
	private double interestRate;
	private long createdTime;
	private long lastRefreshTime;
	private byte[] encryoted;
	public Account(String owner, int balance, int pin) {
		super();
		this.owner = owner;
		this.balance = balance;
		this.encryoted = BigInteger.valueOf(pin).toByteArray();
		createdTime = System.currentTimeMillis();
		lastRefreshTime=createdTime;
	}
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getBalance() {
		return (int) balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public byte[] getPin() {
		return encryoted;
	}
	public void setPin(int pin) {
		this.encryoted = BigInteger.valueOf(pin).toByteArray();
	}
	public boolean checkPin(int input){
		if(BigInteger.valueOf(input).toByteArray() == encryoted){
			return true;
		}
		else{
			return false;
		}
	}

	
}
