package com.ic211.project2.part5;

import com.ic211.project2.part1.EncryptorFactory;
import com.ic211.project2.part1.VaultFormatException;
import com.ic211.project2.part3.User;

public class Data {
	private String encryptionAlgorithm;
	private String encryptedCipherText;
	private String label;
	private String text;

	public Data(User user, String encryptionAlgorithm, String encryptedCipherText) {
		this.encryptionAlgorithm = encryptionAlgorithm;
		this.encryptedCipherText = encryptedCipherText;
	}

	public String getEncryptionAlgorithm() {
		return encryptionAlgorithm;
	}

	public String getEncryptedCipherText() {
		return encryptedCipherText;
	}

	public String getLabel() {
		return label;
	}

	public String getText() {
		return text;
	}

	public Data decryptText(String plainTextPassword) throws VaultFormatException {
		String decryptedCipherText = EncryptorFactory.getEncryptor(encryptionAlgorithm, plainTextPassword.toCharArray()).decrypt(encryptedCipherText);

		String[] splitText = decryptedCipherText.split("_");
		if (!(splitText.length >= 2)) {
			System.out.println("Error! Corrupted entry '" + encryptedCipherText + "' in vault file.");
			throw new VaultFormatException("Invalid formatting for data text in vault.");
		}
		label = splitText[0];
		text = "";
		for (int i = 1; i < splitText.length; i++) {
			text += splitText[i] + (i != splitText.length - 1 ? "_" : "");
		}
		return this;
	}
}
