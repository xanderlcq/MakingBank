import java.math.*;

import acm.program.*;

public class Account {
	private String owner;
	private double balance;
	private double interestRate;
	private long lastRefreshTime;
	private byte[] encrypted;

	public Account(String owner, int balance, int pin) {
		super();
		this.owner = owner;
		this.balance = balance;
		this.encrypted = BigInteger.valueOf(pin).toByteArray();
		lastRefreshTime = System.currentTimeMillis();
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getBalance(int pin) {
		if (checkPin(pin)) {
			interestCalculator();
			return (int) balance;
		} else {
			return -1;
		}
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public byte[] getPin() {
		return encrypted;
	}

	public String setPin(int currentPin, int newPin) {
		if (checkPin(currentPin)) {
			this.encrypted = BigInteger.valueOf(newPin).toByteArray();
			return ("Done!");
		} else {
			return ("Current Pin is incorrect!");
		}
	}

	public boolean checkPin(int input) {
		if (BigInteger.valueOf(input).toByteArray() == encrypted) {
			return true;
		} else {
			return false;
		}
	}

	private void interestCalculator() {
		long duration = System.currentTimeMillis() - lastRefreshTime;
		lastRefreshTime = System.currentTimeMillis();
		double durationInHour = (int) (duration / 1000 / 60 / 60);
		balance = balance * Math.pow((1 + interestRate), durationInHour);
	}

}
