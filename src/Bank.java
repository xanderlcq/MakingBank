import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

import acm.program.*;

public class Bank extends ConsoleProgram {
	public void run() {

		SavingAccount test = new SavingAccount();
		/*
		 * String name = readLine("name: "); int pin = readInt("Pin: "); double
		 * deposit = readDouble("Deposit: ");
		 * while(!((test.nameCheck(name)).equals("yes"))){
		 * println(test.nameCheck(name)); name = readLine("name: "); }
		 * SavingAccount test1 = new SavingAccount(name,pin,deposit);
		 */
		String name = readLine("name: ");
		int pin = readInt("Pin: ");
		loginUI(name, pin, test);
	}

	private void loginUI(String name, int pin, SavingAccount test) {
		while (true) {
			if ((test.login(name, pin).equals("wrongName"))) {
				println("Account under this name doesn't exist!");
				name = readLine("name: ");
				pin = readInt("Pin: ");
			} else if ((test.login(name, pin).equals("wrongPin"))) {
				println("pin incorrect");
				pin = readInt("Pin: ");
			} else {
				break;
			}

		}
		println(test.login(name, pin));
	}

}
