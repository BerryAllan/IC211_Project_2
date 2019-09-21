package com.ic211.project2.part3;

import com.ic211.project2.part1.VaultFormatException;
import com.ic211.project2.part5.Data;

import java.util.ArrayList;

public class User {
	private String username;
	private String hashAlgorithm;
	private String hashedPassword;
	private String plainPassword;
	private ArrayList<Data> data = new ArrayList<>();

	public User(String username, String hashAlgorithm, String hashedPassword) {
		this.username = username;
		this.hashAlgorithm = hashAlgorithm;
		this.hashedPassword = hashedPassword;
	}

	public String getUsername() {
		return username;
	}

	public String getHashAlgorithm() {
		return hashAlgorithm;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public ArrayList<Data> getData() {
		return data;
	}

	private void authenticate() {
		for (Data data : data) {
			try {
				data.decryptText(plainPassword);
			} catch (VaultFormatException ignored) {
			}
		}
	}

	public void authenticate(String plainPassword) {
		this.plainPassword = plainPassword;
		authenticate();
	}
}
