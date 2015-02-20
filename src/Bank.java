import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

import acm.program.*;

public class Bank extends ConsoleProgram {
	public void run() {
		SavingAccount test = new SavingAccount();
		println(test.login("xander", 123456));
		test.setBalance(50000.0);
	}

}
