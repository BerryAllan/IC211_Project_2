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
		DataGatherer dataGatherer = new DataGatherer(args).invoke();
		if (dataGatherer.is()) return;
		Scanner sc = dataGatherer.getSc();
		User user = dataGatherer.getUser();

		//The main loop of user interaction. Gathers and switches on a command string from the user every iteration.
		String command = "";
		while (!command.equals("quit")) {
			System.out.print("> ");
			command = sc.next();
			switch (command) {
				case "labels": //Print the labels of the user.
					for (Data data : user.getData()) {
						System.out.println(data.getLabel());
					}
					break;
				case "get": //Get the text associated with the label inputted.
					String label = sc.next();
					Optional<Data> optionalData = user.getData().stream().filter(data -> data.getLabel().equals(label)).findFirst();
					optionalData.ifPresent(data -> System.out.println(data.getText()));
					break;
				case "quit": //Do nothing, the loop will exit.
					break;
				default: //Print an error message.
					System.out.println("Unknown command '" + command + "'.");
					break;
			}
		}
	}

	/**
	 * Simply an Intellij refactoring generated class to gather the data used in the main methods of Parts 5 and 6; trying to avoid code duplication.
	 */
	public static class DataGatherer {
		private boolean myResult;
		private String[] args;
		private Scanner sc;
		private String username;
		private String password;
		private VaultFileInterface vfi;
		private User user;

		/**
		 * @param args Just the arguments passed into the main method.
		 */
		public DataGatherer(String[] args) {
			this.args = args;
		}

		/**
		 * @return Returns true if <code>invoke()</code> went off with a hitch, otherwise false.
		 */
		public boolean is() {
			return myResult;
		}

		/**
		 * @return The scanner associated with the data gatherer.
		 */
		public Scanner getSc() {
			return sc;
		}

		/**
		 * @return The username gathered by this class.
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @return The password gathered by this class.
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @return The <code>VaultFileInterface</code> gathered by this class.
		 */
		public VaultFileInterface getVfi() {
			return vfi;
		}

		/**
		 * @return The <code>User</code> gathered by this class.
		 */
		public User getUser() {
			return user;
		}

		/**
		 * This method does all the work of gathering the data needed in the main methods of Parts 5 and 6.
		 *
		 * @return <code>this</code>
		 */
		public DataGatherer invoke() {
			//Check if we're adding a user and default to Part 4's main method logic.
			if (args.length == 2 && args[0].equals("-au")) {
				com.ic211.project2.part4.Vault.main(args);
				myResult = true;
				return this;
			}
			//Fill the user array with data from the vault.
			ArrayList<User> users;
			sc = new Scanner(System.in);
			vfi = new VaultFileInterface(args[0]);
			try {
				users = vfi.fillUserAndDataArray();
			} catch (FileNotFoundException | NoSuchElementException e) {
				myResult = true;
				return this;
			}

			//Get the attempted username and password from the user.
			System.out.print("username: ");
			username = sc.nextLine();
			//username = System.console().readLine();
			System.out.print("password: ");
			password = sc.nextLine();
			//password = new String(System.console().readPassword());

			//Deny or grant access based on the user's inputted username and password.
			Optional<User> optionalUser = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
			if (optionalUser.isPresent()
					&& optionalUser.get().getHashedPassword().equals(HasherFactory.getHasher(optionalUser.get().getHashAlgorithm(), password.toCharArray()).hash(password))) {
				user = optionalUser.get();
				user.authenticate(password);
				System.out.println("Access granted!");
			} else {
				System.out.println("Access denied!");
				myResult = true;
				return this;
			}
			myResult = false;
			return this;
		}
	}
}
