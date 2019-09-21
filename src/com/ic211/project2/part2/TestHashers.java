package com.ic211.project2.part2;

import java.util.Scanner;

public class TestHashers {
	public static void main(String[] args) {
		// Create ArrayList of all supported hashers
		Scanner sc = new Scanner(System.in);
		// Get alg,psw,msg from user
		System.out.print("algorithm: ");
		String encalg = sc.nextLine();
		//String encalg = System.console().readLine();
		System.out.print("password : ");
		String password = sc.nextLine();
		//char[] password = System.console().readPassword();
		//System.out.print("message  : ");
		//String plaintext = sc.nextLine();
		//String plaintext = System.console().readLine();

		Hasher hasher = HasherFactory.getHasher(encalg, password.toCharArray());

		// Encrypt, decrypt print summary of results
		String hash = hasher.hash(password);
		System.out.println("password read : " + password);
		//System.out.println("cipher: " + ciphertext);
		System.out.println("hash computed: " + hash);
	}
}