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
		//String password = new String(System.console().readPassword());

		Hasher hasher = HasherFactory.getHasher(encalg, password.toCharArray());

		// Hash and print summary of results
		String hash = hasher.hash(password);
		System.out.println("password read : " + password);
		//System.out.println("cipher: " + ciphertext);
		System.out.println("hash computed: " + hash);
	}
}