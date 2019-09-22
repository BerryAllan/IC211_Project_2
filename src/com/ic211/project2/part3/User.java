package com.ic211.project2.part3;

import com.ic211.project2.part1.VaultFormatException;
import com.ic211.project2.part5.Data;

import java.util.ArrayList;

/**
 * Abstraction for a user in a vault file.
 */
public class User {
	private String username; //The user's username
	private String hashAlgorithm; //The hashAlgorithm the user has implemented
	private String hashedPassword; //The user's hashed password
	private String plainPassword; //The user's plain password; only calculated after authentication is granted
	private ArrayList<Data> data = new ArrayList<>(); //A list of the user's data

	/**
	 * @param username       The user's username.
	 * @param hashAlgorithm  The algorithm that the user has implemented to hash their password
	 * @param hashedPassword The hashed password of the user.
	 */
	public User(String username, String hashAlgorithm, String hashedPassword) {
		this.username = username;
		this.hashAlgorithm = hashAlgorithm;
		this.hashedPassword = hashedPassword;
	}

	/**
	 * @return The username of the user.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return The hash algorithm of the user.
	 */
	public String getHashAlgorithm() {
		return hashAlgorithm;
	}

	/**
	 * @return The hashed password of the user.
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * @return The plain password of the user.
	 */
	public String getPlainPassword() {
		return plainPassword;
	}

	/**
	 * @return The list of the user's data.
	 */
	public ArrayList<Data> getData() {
		return data;
	}

	/**
	 * Authenticate's the user's data with plainPassword.
	 */
	private void authenticate() {
		//Isn't a foreach because if the ciphertext is invalid, that data object is removed from the data array.
		// In a foreach, this would lead to a concurrent modification exception.
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < data.size(); i++) {
			try {
				data.get(i).decryptText(plainPassword);
			} catch (VaultFormatException ignored) {
			}
		}
	}

	/**
	 * Authenticate's the user's data.
	 *
	 * @param plainPassword The plaintext password with which to decrypt the user's data.
	 */
	public void authenticate(String plainPassword) {
		this.plainPassword = plainPassword;
		authenticate();
	}
}
