import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

import acm.program.*;

public class Bank extends ConsoleProgram {
	public void run() {

		//register();
		SavingAccount temp = new SavingAccount();
		//register();
		loginUI(temp);
		//double amount = readDouble("How much do u wanna withdraw? ");
		//int pin = readInt("Pin: ");
		//String newOwner = readLine("New Owner Name: ");
		//int oldPin = readInt("Old Pin: ");
		int pin = readInt("New Pin: ");
		println(temp.transfer(pin,100,"mmm"));
		println(temp.getBalance());
		
	}

	private void register() {
		SavingAccount temp = new SavingAccount();
		String name = readLine("name: ");
		int pin = readInt("Pin: ");
		double deposit = readDouble("Initial Deposit: ");
		while (!((temp.nameCheck(name)).equals("yes"))) {
			println(temp.nameCheck(name));
			name = readLine("name: ");
			pin = readInt("Pin: ");
			deposit = readDouble("Initial Deposit: ");
			println();
		}
		temp = new SavingAccount(name, pin, deposit);
		println("Success!");
	}

	private void loginUI(Account temp) {
		
		String name = readLine("name: ");
		int pin = readInt("Pin: ");
		while (true) {
			if ((temp.login(name, pin).equals("wrongName"))) {
				println("Account under this name doesn't exist!");
				name = readLine("name: ");
				pin = readInt("Pin: ");
			} else if ((temp.login(name, pin).equals("wrongPin"))) {
				println("pin incorrect");
				pin = readInt("Pin: ");
			} else {
				break;
			}

		}
		println(temp.login(name, pin));
	}

}
