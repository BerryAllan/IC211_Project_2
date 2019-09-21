package com.ic211.project2.part5;

import com.ic211.project2.part2.HasherFactory;
import com.ic211.project2.part3.User;
import com.ic211.project2.part3.VaultFileInterface;

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
				case "quit":
					break;
				default:
					System.out.println("Unknown command '" + command + "'.");
					break;
			}
		}
	}
}
