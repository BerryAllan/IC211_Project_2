package com.ic211.project2.part3;

import com.ic211.project2.part1.CharacterOutOfBoundsException;
import com.ic211.project2.part1.HasherNotFoundException;
import com.ic211.project2.part1.TestEncryptors;
import com.ic211.project2.part2.Hasher;
import com.ic211.project2.part2.HasherFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class Vault {
	public static void main(String[] args) {
		//If there are an incorrect number of arguments, exit.
		if (args.length != 1) {
			System.out.println("usage: java Vault <filename>");
			return;
		}


		//Fill the user array from the vault file; if exceptions encountered, exit.
		String filename = args[0], username, password;
		ArrayList<User> userLines;
		VaultFileInterface vfi = new VaultFileInterface(filename);
		try {
			userLines = vfi.fillUserAndDataArray();
		} catch (FileNotFoundException e) {
			System.out.println("Error! File '" + filename + "' could not be opened.");
			return;
		} catch (NoSuchElementException e) {
			System.out.println("Error! File '" + filename + "' improperly formatted.");
			return;
		}


		//Get the username and password attempt to log in
		Scanner sc = new Scanner(System.in);
		System.out.print("username: ");
		//username = System.console().readLine();
		username = sc.nextLine();

		System.out.print("password: ");
		//password = new String(System.console().readPassword());
		password = sc.nextLine();

		//Check and see if the username is valid; if not, exit.
		Optional<User> optionalData = userLines.stream().filter(d -> d.getUsername().equals(username)).findFirst();
		if (!optionalData.isPresent()) {
			System.out.println("Access denied!");
			return;
		}

		//Ensure that the attempted password only contains valid characters; if not, exit.
		try {
			password.chars().forEach(i -> TestEncryptors.UtilityMethods.characterBoundsCheck((char) i, "password"));
		} catch (CharacterOutOfBoundsException e) {
			System.out.println("Access denied!");
			return;
		}
		User user = optionalData.get();

		//Ensure that the user's hash algorithm is valid; if not, exit.
		Hasher hasher;
		try {
			hasher = HasherFactory.getHasher(user.getHashAlgorithm(), user.getHashedPassword().toCharArray());
		} catch (HasherNotFoundException e) {
			System.out.println("Error! Hash algorithm '" + user.getHashAlgorithm() + "' not supported.");
			return;
		}

		//Check and see if the hash of the entered password matches the hashed password in the vault
		// and print accordingly. Afterwards if accepted, create a prompt to accept commands, namely to quit
		// in this version of the program.
		boolean correctPassword = hasher.hash(password).equals(user.getHashedPassword());

		System.out.println("Access " + (correctPassword ? "granted" : "denied") + "!");

		if (!correctPassword) return;

		//Loop while the current user input command isn't "quit"
		String command = "";
		while (!command.equals("quit")) {
			System.out.print("> ");
			command = sc.nextLine();
		}
	}
}
