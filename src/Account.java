import java.io.*;
import java.security.MessageDigest;

public class Account {
	private final File file = new File(getFile());
	private String owner;
	private double balance;
	private double interestRate;
	private String type;
	private long lastRefreshTime;
	private String encrypted;
	private boolean loggedIn = false;
	private String oldOwner = owner;

	public Account() {
		// create empty object to load data
	}

	public boolean checkPin(int pin) {
		try {
			return (md5("" + pin).equals(encrypted));
		} catch (Exception e) {
			System.out.println("checkPin Error");
			e.printStackTrace();
		}
		return false;
	}

	public String withdraw(double amount, int pin) {
		try {
			if (encrypted.equals(md5("" + pin))) {
				String currentBalance = getBalance();
				balance -= amount;
				String newBalance = getBalance();
				return ("Your current balance is " + currentBalance
						+ ". You withdrew: " + amount
						+ ". Your new balance is: " + newBalance);
			} else {

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Pin Incorrect!";
	}

	public String makeDeposit(double amount) {
		String currentBalance = getBalance();
		balance += amount;
		String newBalance = getBalance();
		return ("Your current balance is " + currentBalance
				+ ". You made a deposit of: " + amount
				+ ". Your new balance is: " + newBalance);
	}

	public String closeAccount(int pin) {
		if (loggedIn && checkPin(pin)) {
			String[] existingOwner;
			String[] existingBalance;
			String[] existingInterestRate;
			String[] existingType;
			String[] existingLastRefreshTime;
			String[] existingEncrypted;
			BufferedReader reader;
			try {
				int index;
				reader = new BufferedReader(new FileReader(file));
				String line;
				line = reader.readLine();
				existingOwner = line.split(" ");
				line = reader.readLine();
				existingBalance = line.split(" ");
				line = reader.readLine();
				existingInterestRate = line.split(" ");
				line = reader.readLine();
				existingType = line.split(" ");
				line = reader.readLine();
				existingLastRefreshTime = line.split(" ");
				line = reader.readLine();
				existingEncrypted = line.split(" ");
				reader.close();
				for (int i = 0; i < existingOwner.length; i++) {
					if (owner.equalsIgnoreCase(existingOwner[i])) {
						index = i;
						existingOwner[index] = "";
						existingBalance[index] = "";
						existingInterestRate[index] = "";
						existingType[index] = "";
						existingLastRefreshTime[index] = "";
						existingEncrypted[index] = "";
						// close account
						PrintWriter out = new PrintWriter(file);
						out.println(arrayToString(existingOwner));
						out.println(arrayToString(existingBalance));
						out.println(arrayToString(existingInterestRate));
						out.println(arrayToString(existingType));
						out.println(arrayToString(existingLastRefreshTime));
						out.println(arrayToString(existingEncrypted));
						out.flush();
						out.close();
						//
						owner = null;
						balance = 0;
						interestRate = 0;
						type = null;
						lastRefreshTime = 0;
						encrypted = null;
						return "Account deleted!";
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// set it to space

		} else {
			return "Please Log In First";
		}
		return "Not found";
	}

	public String nameCheck(String name) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			reader.close();
			String[] existingOwner = line.split(" ");
			for (int i = 0; i < existingOwner.length; i++) {
				if (name.equals(existingOwner[i])) {
					return "An account is already under this name";
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("!database loading error(during name check)!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("!database loading error(during name check)!");
			e.printStackTrace();
		}
		return "yes";

	}

	public Account(String owner, double balance, double interestRate, int pin,
			String type) {
		this.owner = owner;
		this.balance = balance;
		this.interestRate = interestRate;
		this.lastRefreshTime = System.currentTimeMillis();
		;
		this.type = type;
		try {
			this.encrypted = md5("" + pin);
		} catch (Exception e) {
			System.out.println("!encryption error!");
			e.printStackTrace();
		}
		storeNewData();
		loggedIn = true;
	}

	public String login(String owner, int pin) {
		this.owner = owner;
		loggedIn = true;
		return loadData(pin);
	}

	public String getBalance() {
		if (loggedIn) {
			interestCalculator();
			return "" + balance;
		} else {
			return "Please Log in first!";
		}
	}
	
	public String changePin(int oldPin, int newPin){
		try {
			if(encrypted.equals(md5(""+oldPin))){
				encrypted = md5(""+newPin);
				refreshData();
				loggedIn = false;
				return "Success! Please log in again!";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Current Pin Incorrect!";
	}
	
	public String changeType(int pin, String newType){
		try {
			if(encrypted.equals(md5(""+pin))){
				type = newType;
				getBalance();
				if(type.equals("S")){
					interestRate = 0.03;
				}else{
					interestRate = 0.01;
				}
				refreshData();
				return "Success!";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Current Pin Incorrect!";
	}
	
	public String changeOwner(String newOwner, int pin){
		try {
			if(encrypted.equals(md5(""+pin))){
				if(!nameCheck(newOwner).equals("yes")){
					return nameCheck(newOwner);
				}
				oldOwner = owner;
				owner = newOwner;
				refreshData();
				loggedIn = false;
				return "Done! Please log in again!";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Pin Incorrect!";
	}
	// ------basic private method-------
	private String loadData(int pin) {
		String[] existingOwner;
		String[] existingBalance;
		String[] existingInterestRate;
		String[] existingType;
		String[] existingLastRefreshTime;
		String[] existingEncrypted;
		BufferedReader reader;

		try {
			// load data into string
			int index;
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			existingOwner = line.split(" ");
			line = reader.readLine();
			existingBalance = line.split(" ");
			line = reader.readLine();
			existingInterestRate = line.split(" ");
			line = reader.readLine();
			existingType = line.split(" ");
			line = reader.readLine();
			existingLastRefreshTime = line.split(" ");
			line = reader.readLine();
			existingEncrypted = line.split(" ");
			// find the account and load info
			for (int i = 0; i < existingOwner.length; i++) {
				if (owner.equalsIgnoreCase(existingOwner[i])) {
					index = i;
					encrypted = existingEncrypted[index];
					if (md5("" + pin).equals(encrypted)) {
						balance = Double.parseDouble(existingBalance[index]);
						interestRate = Double
								.parseDouble(existingInterestRate[index]);
						type = existingType[index];
						lastRefreshTime = Long
								.parseLong(existingLastRefreshTime[index]);
						return "Success!";
					} else {
						return "wrongPin";
					}
				}
				reader.close();
			}
			return "wrongName";
		} catch (FileNotFoundException e) {
			System.out.println("!database loading error!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out
					.println("!database loading error(Buffered Reader error)!");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("!database loading error!");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("!database loading error(encryption error)!");
			e.printStackTrace();
		}
		return "Error!";

	}

	private void storeNewData() {
		int newLength;
		String[] existingOwner;
		String[] existingBalance;
		String[] existingInterestRate;
		String[] existingType;
		String[] existingLastRefreshTime;
		String[] existingEncrypted;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			// reading existing data into existingArrays
			String line = reader.readLine();
			existingOwner = line.split(" ");
			line = reader.readLine();
			existingBalance = line.split(" ");
			line = reader.readLine();
			existingInterestRate = line.split(" ");
			line = reader.readLine();
			existingType = line.split(" ");
			line = reader.readLine();
			existingLastRefreshTime = line.split(" ");
			line = reader.readLine();
			existingEncrypted = line.split(" ");

			newLength = existingOwner.length + 1;
			// creating arrays for new data
			String[] newOwner = new String[newLength];
			String[] newBalance = new String[newLength];
			String[] newInterestRate = new String[newLength];
			String[] newType = new String[newLength];
			String[] newLastRefreshTime = new String[newLength];
			String[] newEncrypted = new String[newLength];
			// copying old data into new arrays
			System.arraycopy(existingOwner, 0, newOwner, 0,
					existingOwner.length);
			System.arraycopy(existingBalance, 0, newBalance, 0,
					existingOwner.length);
			System.arraycopy(existingInterestRate, 0, newInterestRate, 0,
					existingOwner.length);
			System.arraycopy(existingType, 0, newType, 0, existingOwner.length);
			System.arraycopy(existingLastRefreshTime, 0, newLastRefreshTime, 0,
					existingOwner.length);
			System.arraycopy(existingEncrypted, 0, newEncrypted, 0,
					existingOwner.length);
			// add the new account at the end
			newOwner[newLength - 1] = owner;
			newBalance[newLength - 1] = "" + balance;
			newInterestRate[newLength - 1] = "" + interestRate;
			newType[newLength - 1] = type;
			newLastRefreshTime[newLength - 1] = "" + lastRefreshTime;
			newEncrypted[newLength - 1] = encrypted;
			// println the string array to file
			PrintWriter out = new PrintWriter(file);
			out.println(arrayToString(newOwner));
			out.println(arrayToString(newBalance));
			out.println(arrayToString(newInterestRate));
			out.println(arrayToString(newType));
			out.println(arrayToString(newLastRefreshTime));
			out.println(arrayToString(newEncrypted));
			out.flush();
			out.close();
			reader.close();
			// catching
		} catch (FileNotFoundException e) {
			System.out.println("!database reading error (buffered reader)!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("!database reading error (buffered readline)!");
			e.printStackTrace();
		}

	}

	private void refreshData() {
		if(oldOwner == null){
			oldOwner = owner;
		}
		String[] Owner;
		String[] Balance;
		String[] InterestRate;
		String[] Type;
		String[] LastRefreshTime;
		String[] Encrypted;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			line = reader.readLine();
			Owner = line.split(" ");
			line = reader.readLine();
			Balance = line.split(" ");
			line = reader.readLine();
			InterestRate = line.split(" ");
			line = reader.readLine();
			Type = line.split(" ");
			line = reader.readLine();
			LastRefreshTime = line.split(" ");
			line = reader.readLine();
			Encrypted = line.split(" ");
			reader.close();
			for (int i = 0; i < Owner.length; i++) {
				
				if (owner.equalsIgnoreCase(Owner[i]) ||oldOwner.equalsIgnoreCase(Owner[i])) {
					Owner[i] = owner;
					InterestRate[i] = "" + interestRate;
					Balance[i] = "" + balance;
					LastRefreshTime[i] = "" + lastRefreshTime;
					Type[i] = type;
					Encrypted[i] = encrypted;
					PrintWriter out = new PrintWriter(file);
					out.println(arrayToString(Owner));
					out.println(arrayToString(Balance));
					out.println(arrayToString(InterestRate));
					out.println(arrayToString(Type));
					out.println(arrayToString(LastRefreshTime));
					out.println(arrayToString(Encrypted));
					out.flush();
					out.close();
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String arrayToString(String[] arr) {
		String str = "";
		for (String i : arr) {
			str = str + i + " ";
		}
		return str;
	}

	private void interestCalculator() {
		long duration = System.currentTimeMillis() - lastRefreshTime;
		int durationInHour = (int) ((System.currentTimeMillis() - lastRefreshTime) / 1000 / 60 / 60);
		long remainder = duration - 60 * 60 * 1000 * durationInHour;
		lastRefreshTime = System.currentTimeMillis() - remainder;
		balance = balance * Math.pow((1 + interestRate), durationInHour);
		refreshData();
	}

	private static String md5(String input) throws Exception {

		String original = input;
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(original.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b));
		}
		return (sb.toString());

	}

	private String getFile() {
		String file;
		String OS = System.getProperty("os.name");
		if (OS.indexOf("Win") >= 0) {
			file = "C:/Users/xander/workspace/MakingBank/src/database";
			return file;
		} else {
			file = "/Users/ali/Documents/workspace/MakingBank/src/database";
			return file;
		}

	}
}
