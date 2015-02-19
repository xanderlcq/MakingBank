public class SavingAccount extends Account {
	private double interestRate;

	public SavingAccount(String owner, int balance, int pin) {
		super(owner, balance, pin);
		this.interestRate = 0.03;
	}

	public void addMoney(int amount) {

	}
	public void withdraw(int amount,int pin){

	}
	public void checkBalance(int pin){
		
	}
	public void closeAccount(){
		
	}
	public String changePin(int currentPin,int newPin){
		return(this.setPin(currentPin,newPin));
	}
	
	//Follow methods overwride the same methods in the superclass. Bank interface will not have direct access to these methods in the super class
	public void setOwner(){
		
	}
	public void setBalance(){
		
	}
	public void setPin(){
		
	}
}
