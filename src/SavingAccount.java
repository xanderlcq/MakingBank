public class SavingAccount extends Account {
	private double interestRate;

	public SavingAccount(String owner, int balance, int pin) {
		super(owner, balance, pin);
		this.interestRate = 0.03;
	}

	public void addMoney(int amount) {

	}
	public void withdraw(int amount,int pin){
		if(this.checkPin(pin)){
			
		}
	}
	public int checkBalance(){
		return this.getBalance();
	}
	public void closeAccount(){
		
	}
	public void changePin(int pin){
		this.setPin(pin);
	}

}
