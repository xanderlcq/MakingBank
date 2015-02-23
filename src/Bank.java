import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

import acm.program.*;

public class Bank extends ConsoleProgram {
	Account temp = new Account();

	public void run() {
		level1UI();

	}

	private void level1UI() {
		println("1----Register a new account.");
		println("2----Log in to your account.");
		println("3----Exit the program");
		int input = readInt("What do you want to do? ");
		while (input < 1 || input > 3) {
			input = readInt("Invalid input, what do you want to do? ");
		}
		if (input == 1) {
			registerUI();
			level1UI();
		} else if (input == 2) {
			loginUI(temp);
			level2UI();
		} else {
			System.exit(0);
		}

	}

	private void level2UI() {
		println("1----Check your balance");
		println("2----Make a deposit");
		println("3----Withdraw some money");
		println("4----Transfer some money");
		println("5----Manage your account");
		println("6----Log out");
		int input = readInt("What do you want to do? ");
		while (input < 1 || input > 6) {
			input = readInt("Invalid input, what do you want to do? ");
		}
		if (input == 1) {
			println(temp.getBalance());
			level2UI();
		} else if (input == 2) {
			double amount = readDouble("How much do you want to add to your account? ");
			temp.makeDeposit(amount);
			level2UI();
		} else if (input == 3) {
			double amount = readDouble("How much do you want to withdraw? ");
			int pin = readInt("Pin: ");
			println(temp.withdraw(amount, pin));
			level2UI();
		} else if (input == 4) {
			double amount = readDouble("How much do you want to transfer? ");
			String dest = readLine("What's reciever's account name? ");
			int pin = readInt("Pin: ");
			println(temp.transfer(pin, amount, dest));
			level2UI();
		} else if (input == 5) {
			level3UI();
		} else {
			level1UI();
		}

	}

	private void level3UI() {
		println("1----Change your pin");
		println("2----Change your account owner's name");
		println("3----Change your account type");
		println("4----Close your account");
		println("5----Back");
		int input = readInt("What do you want to do? ");
		while (input < 1 || input > 5) {
			input = readInt("Invalid input, what do you want to do? ");
		}
		if (input == 1) {

		} else if (input == 2) {

		} else if (input == 3) {

		} else if (input == 4) {

		} else if (input == 5) {
			level2UI();
		}
	}

	private void registerUI() {
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
