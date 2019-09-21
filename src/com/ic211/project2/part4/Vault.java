package com.ic211.project2.part4;

import com.ic211.project2.part1.CharacterOutOfBoundsException;
import com.ic211.project2.part1.HasherNotFoundException;
import com.ic211.project2.part1.TestEncryptors;
import com.ic211.project2.part2.Hasher;
import com.ic211.project2.part2.HasherFactory;
import com.ic211.project2.part3.User;
import com.ic211.project2.part3.VaultFileInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Vault {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("usage: java Vault [-au] <filename>");
			return;
		} else if (args.length == 1) {
			com.ic211.project2.part3.Vault.main(args);
			return;
		}

		String username, password, hashAlg, filename;
		filename = args[1];

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
		System.out.print("Hash algorithm: ");
		hashAlg = sc.nextLine();

		try {
			password.chars().forEach(i -> TestEncryptors.UtilityMethods.characterBoundsCheck((char) i, "password"));
		} catch (CharacterOutOfBoundsException e) {
			System.out.println("Error! Invalid symbol '" + e.getCharacter() + "' in password.");
			return;
		}

		Hasher hasher = null;
		try {
			hasher = HasherFactory.getHasher(hashAlg, password.toCharArray());
		} catch (HasherNotFoundException e) {
			System.out.println("Error! Hash algorithm '" + hashAlg + "' not supported.");
			return;
		}

		if (userLines.stream().noneMatch(user -> user.getUsername().equals(username))) {
			System.out.println(" Error! Username '" + username + "' already in use.");
			return;
		}

		vfi.writeToVault("user " + username + ' ' + hashAlg + ' ' + hasher.hash(password));
	}
}
