package com.ic211.project2.part5;

import com.ic211.project2.part1.EncryptorFactory;
import com.ic211.project2.part1.VaultFormatException;
import com.ic211.project2.part3.User;

/**
 * Abstraction for data that is associated with a <code>User</code>.
 */
public class Data {
	private User user;
	private String encryptionAlgorithm;
	private String encryptedCipherText;
	private String label;
	private String text;

	/**
	 * @param user                The <code>User</code> that is holding this <code>Data</code> object.
	 * @param encryptionAlgorithm The algorithm used to encrypt the data.
	 * @param encryptedCipherText The encrypted text.
	 */
	public Data(User user, String encryptionAlgorithm, String encryptedCipherText) {
		this.user = user;
		this.encryptionAlgorithm = encryptionAlgorithm;
		this.encryptedCipherText = encryptedCipherText;
	}

	/**
	 * @return The encryption algorithm used by this line of data.
	 */
	public String getEncryptionAlgorithm() {
		return encryptionAlgorithm;
	}

	/**
	 * @return The encrypted text.
	 */
	public String getEncryptedCipherText() {
		return encryptedCipherText;
	}

	/**
	 * @return The label (1st part of the encrypted text).
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return The main body of text (2nd part of the decrypted text).
	 */
	public String getText() {
		return text;
	}

	/**
	 * Decrypts the encrypted text, then determines the label and main text for this <code>Data</code>.
	 *
	 * @param plainTextPassword The plaintext password used to decrypt the data.
	 * @return <code>this</code>.
	 * @throws VaultFormatException If the vault is formatted incorrectly.
	 */
	public Data decryptText(String plainTextPassword) throws VaultFormatException {
		//Decrypt the text.
		String decryptedCipherText = EncryptorFactory.getEncryptor(encryptionAlgorithm, plainTextPassword.toCharArray()).decrypt(encryptedCipherText);

		//Splits the text into an array of Strings by the character '_'. If the length of the array isn't at least 2, then throw a VaultFormatException.
		String[] splitText = decryptedCipherText.split("_");
		if (splitText.length < 2) {
			System.out.println("Error! Corrupted entry '" + encryptedCipherText + "' in vault file.");
			user.getData().remove(this);
			throw new VaultFormatException("Invalid formatting for data text in vault.");
		}
		label = splitText[0];
		//Rejoin all the splitted main text with the '_' character.
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < splitText.length; i++) {
			sb.append(splitText[i]).append(i != splitText.length - 1 ? "_" : "");
		}
		text = sb.toString();
		return this;
	}
}
