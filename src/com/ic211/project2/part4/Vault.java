package com.ic211.project2.part4;

import com.ic211.project2.part1.CharacterOutOfBoundsException;
import com.ic211.project2.part1.HasherNotFoundException;
import com.ic211.project2.part1.TestEncryptors;
import com.ic211.project2.part2.Hasher;
import com.ic211.project2.part2.HasherFactory;
import com.ic211.project2.part3.User;
import com.ic211.project2.part3.VaultFileInterface;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Vault {
	public static void main(String[] args) {
		//If there isn't any arguments, simply prompt the user and exit.
		if (args.length == 0) {
			System.out.println("usage: java Vault [-au] <filename>");
			return;
		} else if (args.length == 1) { //If there is only 1 argument, assume it's the filename and proceed as defined in Part 3.
			com.ic211.project2.part3.Vault.main(args);
			return;
		}

		//Set the filename and gather all data from the vault file.
		String username, password, hashAlg, filename;
		filename = args[1];

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

		//Read in the username, password, and hash algorithm.
		Scanner sc = new Scanner(System.in);
		System.out.print("username: ");
		username = sc.nextLine();
		//username = System.console().readLine();
		System.out.print("password: ");
		password = sc.nextLine();
		//password = new String(System.console().readPassword());
		System.out.print("Hash algorithm: ");
		hashAlg = sc.nextLine();
		//hashAlg = System.console().readLine();

		//Ensure that password is valid, hashAlg exists, and username isn't already in use.
		try {
			password.chars().forEach(i -> TestEncryptors.UtilityMethods.characterBoundsCheck((char) i, "password"));
		} catch (CharacterOutOfBoundsException e) {
			System.out.println("Error! Invalid symbol '" + e.getCharacter() + "' in password.");
			return;
		}

		Hasher hasher;
		try {
			hasher = HasherFactory.getHasher(hashAlg, password.toCharArray());
		} catch (HasherNotFoundException e) {
			System.out.println("Error! Hash algorithm '" + hashAlg + "' not supported.");
			return;
		}

		if (userLines.stream().anyMatch(user -> user.getUsername().equals(username))) {
			System.out.println(" Error! Username '" + username + "' already in use.");
			return;
		}

		//Write the new user's line into the vault file.
		vfi.writeLineToVault("user", username, hashAlg, hasher.hash(password));
	}
}
