import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

import acm.io.IODialog;
import acm.program.*;

public class Bank extends ConsoleProgram {
	Account temp = new Account();

	public void run() {
		setSize(600, 600);
		level1UI();

	}

	private void level1UI() {
		println();
		println("1----Register a new account.");
		println("2----Log in to your account.");
		println("3----Exit the program");
		int input = readInt("What do you want to do? ");
		println();
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
			IODialog confirm = new IODialog();
			if (confirm.readBoolean("Are you sure you want to exit?")) {
				IODialog seeYou = new IODialog();
				seeYou.println("Thank you for using this bank!-----Xander Li");
				System.exit(0);
			}
		}

	}

	private void level2UI() {
		println();
		println("1----Check your balance");
		println("2----Make a deposit");
		println("3----Withdraw some money");
		println("4----Transfer some money");
		println("5----Manage your account");
		println("6----Log out");
		int input = readInt("What do you want to do? ");
		println();
		while (input < 1 || input > 6) {
			input = readInt("Invalid input, what do you want to do? ");
		}
		if (input == 1) {
			println(temp.getBalance());
			level2UI();
		} else if (input == 2) {
			double amount = readDouble("How much do you want to add to your account? ");
			println(temp.makeDeposit(amount));
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
		println();
		println("1----Change your pin");
		println("2----Change your account owner's name");
		println("3----Change your account type");
		println("4----Close your account");
		println("5----Back");
		int input = readInt("What do you want to do? ");
		println();
		while (input < 1 || input > 5) {
			input = readInt("Invalid input, what do you want to do? ");
		}
		if (input == 1) {
			int oldPin = readInt("What's your current pin? ");
			int newPin = readInt("What's your new pin? ");
			println(temp.changePin(oldPin, newPin));
			level1UI();
		} else if (input == 2) {
			String newOwner = readLine("What's your new owner name? ");
			int pin = readInt("Pin: ");
			
			String te = temp.changeOwner(newOwner, pin);
			if(te.equals("Done! Please log in again!")){
				println(te);
				level1UI();
			}else{
				println(te);
				level3UI();
			}
			
		} else if (input == 3) {
			println();
			println("1---Change to Saving Account");
			println("2---Change to Checking Account");
			println("3---Back");
			int input2 = readInt("What do you want to do: ");
			println();
			while (input2 < 1 || input2 > 3) {
				input2 = readInt("Invalid input: ");
			}
			if (input2 == 1) {
				int pin = readInt("Pin: ");
				println(temp.changeType(pin, "S"));
				level3UI();
			} else if (input2 == 2) {
				int pin = readInt("Pin: ");
				println(temp.changeType(pin, "C"));
				level3UI();
			} else {
				level3UI();
			}
		} else if (input == 4) {
			int pin = readInt("Pin: ");
			IODialog confirm = new IODialog();
			if (confirm
					.readBoolean("Are you sure you want to close this account?")) {
				println(temp.closeAccount(pin));
				level1UI();
			}
		} else if (input == 5) {
			level2UI();
		}
	}

	private void registerUI() {
		Account temp = new Account();
		String name = readLine("name: ");
		int pin = readInt("Pin: ");
		double deposit = readDouble("Initial Deposit: ");
		int type = readInt("1---Saving Account; 2---Checking Account");
		while (type < 1 || type > 2) {
			type = readInt("Invalid input.");
		}
		while (!((temp.nameCheck(name)).equals("yes"))) {
			println(temp.nameCheck(name));
			name = readLine("name: ");
			pin = readInt("Pin: ");
			deposit = readDouble("Initial Deposit: ");
			println();
		}
		if (type == 1) {
			temp = new SavingAccount(name, pin, deposit);
			println("Success!");
		} else {
			temp = new CheckingAccount(name, pin, deposit);
			println("Success!");
		}
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
