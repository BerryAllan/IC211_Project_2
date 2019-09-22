package com.ic211.project2.part3;

import com.ic211.project2.part5.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

/**
 * This class provides a few methods for interaction with vault files;
 * it essentially handles all reading and writing.
 */
public class VaultFileInterface {
	private String filename;

	/**
	 * @param filename The full filename of this vault file.
	 */
	public VaultFileInterface(String filename) {
		this.filename = filename;
	}

	/**
	 * This method handles gathering information from vault files into an <code>ArrayList</code> of <code>User</code>s.
	 *
	 * @return An <code>ArrayList</code> of <code>User</code>s that have <code>Data</code>.
	 * @throws FileNotFoundException  If the full filename isn't valid.
	 * @throws NoSuchElementException If the vault file isn't properly formatted.
	 */
	public ArrayList<User> fillUserAndDataArray() throws FileNotFoundException, NoSuchElementException {
		ArrayList<User> userLines = new ArrayList<>();
		Scanner fileSc;
		//Print an exception message if the file can't be found and throw the exception.
		try {
			fileSc = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("Error! File '" + filename + "' could not be opened.");
			throw new FileNotFoundException();
		}
		//Scans the file word by word, adding users to the list and associated properties to the users accordingly.
		//Additionally, if the current line contains data, add the data to the User's Data array.
		for (String temp = fileSc.next(), tempName = fileSc.next(), tempAlg = fileSc.next(), tempCipher = fileSc.next(); ;
		     temp = fileSc.next(), tempName = fileSc.next(), tempAlg = fileSc.next(), tempCipher = fileSc.next()) {
			if (temp.equals("user"))
				userLines.add(new User(tempName, tempAlg, tempCipher));
			else if (temp.equals("data")) {
				//Need this effectively final variable for the stream
				String finalTempName = tempName;
				//If the array of users has a user associated with this data, add this data to said user's data array.
				Optional<User> optionalUser = userLines.stream().filter(dataUser -> dataUser.getUsername().equals(finalTempName)).findFirst();
				if (optionalUser.isPresent()) {
					User user = optionalUser.get();
					user.getData().add(new Data(user, tempAlg, tempCipher));
				}
			} else {
				//Throw an exception if the file isn't formatted properly.
				System.out.println("Error! File '" + filename + "' improperly formatted.");
				throw new NoSuchElementException();
			}

			//We have to check at the end of the loop, after a line of data is read in; otherwise we will skip the last line of data.
			if (!fileSc.hasNext())
				break;
		}
		return userLines;
	}

	/**
	 * @param type       The type of line this is, either user or data.
	 * @param username   The username this line is associated with.
	 * @param algorithm  The algorithm used.
	 * @param hiddenText The text hidden by <code>algorithm</code>.
	 */
	public void writeLineToVault(String type, String username, String algorithm, String hiddenText) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		pw.println(type + ' ' + username + ' ' + algorithm + ' ' + hiddenText);

		pw.close();
	}
}
