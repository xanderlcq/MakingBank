import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.*;
import java.security.MessageDigest;
import java.util.ArrayList;

import acm.program.*;

public class Account {
	private final File file = new File(
			"C:/Users/xander/workspace/MakingBank/src/database");
	private String owner;
	private double balance;
	private double interestRate;
	private String type;
	private long lastRefreshTime;
	private String encrypted;
	
	public Account(){
		//create empty object to load data
	}
	public Account(String owner, double balance, double interestRate,
			long lastRefreshTime, int pin, String type) {
		this.owner = owner;
		this.balance = balance;
		this.interestRate = interestRate;
		this.lastRefreshTime = lastRefreshTime;
		this.type = type;
		try {
			this.encrypted = md5("" + pin);
		} catch (Exception e) {
			System.out.println("!encryption error!");
			e.printStackTrace();
		}
		storeNewData();
	}

	public String login(String owner, int pin) {
		this.owner = owner;
		return loadData(pin);
	}

	public double getBalance() {
		return balance;
	}

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
						return "Pin incorrect!";
					}
				}
			reader.close();
			}

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

	private String arrayToString(String[] arr) {
		String str = "";
		for (String i : arr) {
			str = str + i + " ";
		}
		return str;
	}

	public static String md5(String input) throws Exception {

		String original = input;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(original.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return (sb.toString());

	}

	private void interestCalculator() {
		long duration = System.currentTimeMillis() - lastRefreshTime;
		lastRefreshTime = System.currentTimeMillis();
		double durationInHour = (int) (duration / 1000 / 60 / 60);
		balance = balance * Math.pow((1 + interestRate), durationInHour);
	}

}
