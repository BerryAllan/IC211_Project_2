package com.ic211.project2.part3;

import com.ic211.project2.part5.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class VaultFileInterface {
	private String filename;

	public VaultFileInterface(String filename) {
		this.filename = filename;
	}

	public ArrayList<User> fillUserArray() throws FileNotFoundException, NoSuchElementException {
		ArrayList<User> userLines = new ArrayList<>();
		Scanner fileSc = new Scanner(new File(filename));
		for (String temp = fileSc.next(), tempName = fileSc.next(), tempAlg = fileSc.next(), tempPswd = fileSc.next(); ;
		     temp = fileSc.next(), tempName = fileSc.next(), tempAlg = fileSc.next(), tempPswd = fileSc.next()) {
			if (!temp.equals("user")) throw new NoSuchElementException();
			userLines.add(new User(tempName, tempAlg, tempPswd));

			//We have to check at the end of the loop, after a line of data is read in; otherwise we will skip the last line of data.
			if (!fileSc.hasNext())
				break;
		}
		return userLines;
	}

	public ArrayList<User> fillUserAndDataArray() throws NoSuchElementException, FileNotFoundException {
		ArrayList<User> userLines = new ArrayList<>();
		Scanner fileSc;
		try {
			fileSc = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("Error! File '" + filename + "' could not be opened.");
			throw new FileNotFoundException();
		}
		for (String temp = fileSc.next(), tempName = fileSc.next(), tempAlg = fileSc.next(), tempCipher = fileSc.next(); ;
		     temp = fileSc.next(), tempName = fileSc.next(), tempAlg = fileSc.next(), tempCipher = fileSc.next()) {
			if (temp.equals("user"))
				userLines.add(new User(tempName, tempAlg, tempCipher));
			else if (temp.equals("data")) {
				//Need this for the stream
				String finalTempName = tempName;
				Optional<User> optionalUser = userLines.stream().filter(dataUser -> dataUser.getUsername().equals(finalTempName)).findFirst();
				if (optionalUser.isPresent()) {
					User user = optionalUser.get();
					user.getData().add(new Data(user, tempAlg, tempCipher));
				}
			} else {
				System.out.println("Error! File '" + filename + "' improperly formatted.");
				throw new NoSuchElementException();
			}

			//We have to check at the end of the loop, after a line of data is read in; otherwise we will skip the last line of data.
			if (!fileSc.hasNext())
				break;
		}
		return userLines;
	}

	public void writeToVault(String text) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		pw.println(text);

		pw.close();
	}
}
