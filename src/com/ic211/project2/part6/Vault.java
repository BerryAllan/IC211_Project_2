package com.ic211.project2.part6;

import com.ic211.project2.part1.*;
import com.ic211.project2.part2.HasherFactory;
import com.ic211.project2.part3.User;
import com.ic211.project2.part3.VaultFileInterface;
import com.ic211.project2.part5.Data;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class Vault {
	public static void main(String[] args) {
		if (args.length == 2 && args[0].equals("-au")) {
			com.ic211.project2.part4.Vault.main(args);
			return;
		}
		ArrayList<User> users;
		Scanner sc = new Scanner(System.in);
		String username, password;
		VaultFileInterface vfi = new VaultFileInterface(args[0]);
		try {
			users = vfi.fillUserAndDataArray();
		} catch (FileNotFoundException | NoSuchElementException e) {
			return;
		}

		System.out.print("username: ");
		username = sc.nextLine();
		System.out.print("password: ");
		password = sc.nextLine();

		Optional<User> optionalUser = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
		User user = null;
		if (optionalUser.isPresent()
				&& optionalUser.get().getHashedPassword().equals(HasherFactory.getHasher(optionalUser.get().getHashAlgorithm(), password.toCharArray()).hash(password))) {
			user = optionalUser.get();
			user.authenticate(password);
			System.out.println("Access granted!");
		} else {
			System.out.println("Access denied!");
			return;
		}

		String command = "";
		while (!command.equals("quit")) {
			System.out.print("> ");
			command = sc.next();
			switch (command) {
				case "labels":
					for (Data data : user.getData()) {
						System.out.println(data.getLabel());
					}
					break;
				case "get":
					String label = sc.next();
					Optional<Data> optionalData = user.getData().stream().filter(data -> data.getLabel().equals(label)).findFirst();
					optionalData.ifPresent(data -> System.out.println(data.getText()));
					break;
				case "add":
					String encryptionAlg = sc.next(), newLabel = sc.next(), newText = sc.next();
					Encryptor encryptor;
					try {
						encryptor = EncryptorFactory.getEncryptor(encryptionAlg, password.toCharArray());
					} catch (EncryptorNotFoundException e) {
						break;
					}
					try {
						TestEncryptors.UtilityMethods.characterBoundsCheck(newLabel, "data");
						if (newLabel.contains("_"))
							throw new CharacterOutOfBoundsException("Error! Label '" + newLabel + "' is invalid.", '_');
					} catch (CharacterOutOfBoundsException e) {
						System.out.println("Error! Label '" + newLabel + "' is invalid.");
						break;
					}
					try {
						TestEncryptors.UtilityMethods.characterBoundsCheck(newText, "data");
					} catch (CharacterOutOfBoundsException e) {
						System.out.println("Error! Invalid character '" + e.getCharacter() + "' in text.");
						break;
					}
					String encryptedCipherText = encryptor.encrypt(newLabel + '_' + newText);
					vfi.writeToVault("data " + username + ' ' + encryptionAlg + ' ' + encryptedCipherText);
					//Maintains concurrency with data in vault vs data in memory without having to do file-io all over again
					user.getData().add(new Data(user, encryptionAlg, encryptedCipherText).decryptText(password));
				case "quit":
					break;
				default:
					System.out.println("Unknown command '" + command + "'.");
					break;
			}
		}
	}
}
