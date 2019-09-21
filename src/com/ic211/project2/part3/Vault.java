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
		if (args.length == 0) {
			System.out.println("usage: java Vault <filename>");
			return;
		}

		String command = "";
		while (!command.equals("quit")) {
			String filename = args[0], username, password;
			ArrayList<User> userLines;
			VaultFileInterface vfi = new VaultFileInterface(filename);
			try {
				userLines = vfi.fillUserArray();
			} catch (FileNotFoundException e) {
				System.out.println("Error! File '" + filename + "' could not be opened.");
				return;
			} catch (NoSuchElementException e) {
				System.out.println("Error! File '" + filename + "' improperly formatted.");
				return;
			}

			Scanner sc = new Scanner(System.in);
			System.out.print("username: ");
			username = sc.nextLine();

			System.out.print("password: ");
			password = sc.nextLine();

			Optional<User> optionalData = userLines.stream().filter(d -> d.getUsername().equals(username)).findFirst();
			if (!optionalData.isPresent()) {
				System.out.println("Access denied!");
				continue;
			}

			try {
				password.chars().forEach(i -> TestEncryptors.UtilityMethods.characterBoundsCheck((char) i, "password"));
			} catch (CharacterOutOfBoundsException e) {
				System.out.println("Access denied!");
				continue;
			}
			User user = optionalData.get();

			Hasher hasher = null;
			try {
				hasher = HasherFactory.getHasher(user.getHashAlgorithm(), user.getHashedPassword().toCharArray());
			} catch (HasherNotFoundException e) {
				System.out.println("Error! Hash algorithm '" + user.getHashAlgorithm() + "' not supported.");
				continue;
			}

			boolean correctPassword = hasher.hash(password).equals(user.getHashedPassword());

			System.out.println("Access " + (correctPassword ? "granted" : "denied") + "!");

			System.out.print("> ");
			command = sc.nextLine();
		}
	}
}
