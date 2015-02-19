import acm.program.*;

public class Acoount {
	private String owner;
	private double balance;
	private int pin;
	private double interestRate;
	private long birthDay;
	public Acoount(String owner, int balance, int pin) {
		super();
		this.owner = owner;
		this.balance = balance;
		this.pin = pin;
		birthDay = System.currentTimeMillis();
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
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}

	
}
