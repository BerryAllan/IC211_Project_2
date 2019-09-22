package com.ic211.project2.part6;

import com.ic211.project2.part1.*;
import com.ic211.project2.part3.User;
import com.ic211.project2.part3.VaultFileInterface;
import com.ic211.project2.part5.Data;

import java.util.Optional;
import java.util.Scanner;

public class Vault {
	public static void main(String[] args) {
		com.ic211.project2.part5.Vault.DataGatherer dataGatherer = new com.ic211.project2.part5.Vault.DataGatherer(args).invoke();
		if (dataGatherer.is()) return;
		Scanner sc = dataGatherer.getSc();
		String username = dataGatherer.getUsername();
		String password = dataGatherer.getPassword();
		VaultFileInterface vfi = dataGatherer.getVfi();
		User user = dataGatherer.getUser();

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
				case "add": //Add the requested data to the vault file.
					String encryptionAlg = sc.next(), newLabel = sc.next(), newText = sc.next();
					Encryptor encryptor;
					//Ensure that all the inputted parts of the data are correct. If they aren't, print error messages and break.
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
					//Encrypt the label and text and write it to the vault.
					String encryptedCipherText = encryptor.encrypt(newLabel + '_' + newText);
					vfi.writeLineToVault("data", username, encryptionAlg, encryptedCipherText);
					//Maintains concurrency with data in vault vs data in memory without having to do file-io all over again.
					user.getData().add(new Data(user, encryptionAlg, encryptedCipherText).decryptText(password));
				case "quit": //Do nothing, the loop will exit.
					break;
				default: //Print an error message.
					System.out.println("Unknown command '" + command + "'.");
					break;
			}
		}
	}

}
