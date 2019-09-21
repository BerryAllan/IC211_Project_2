package com.ic211.project2.part1;

import java.util.Scanner;

public class TestEncryptors {
	public static void main(String[] args) throws Throwable {
		Scanner sc = new Scanner(System.in);
		// Get alg,psw,msg from user
		System.out.print("algorithm: ");
		String encalg = sc.nextLine();
		//String encalg = System.console().readLine();
		System.out.print("password : ");
		String password = sc.nextLine();
		//char[] password = System.console().readPassword();
		System.out.print("message  : ");
		String plaintext = sc.nextLine();
		//String plaintext = System.console().readLine();
		Encryptor encryptor = EncryptorFactory.getEncryptor(encalg, password.toCharArray());

		// Encrypt, decrypt print sumamry of results
		String ciphertext = encryptor.encrypt(plaintext);
		String hopefully = encryptor.decrypt(ciphertext);
		System.out.println("plain : " + plaintext);
		System.out.println("cipher: " + ciphertext);
		System.out.println("decryp: " + hopefully);
	}

	public static class UtilityMethods {
		public static void characterBoundsCheck(char c, String type) {
			if ((int) c < 42 || (int) c > 122)
				throw new CharacterOutOfBoundsException("Error! " + c + " not allowed in " + type + ".\nThe string must only be valid ASCII characters in the range of 42-122.", c);
		}

		public static String characterBoundsCheck(String s, String type) {
			for (char c : s.toCharArray())
				characterBoundsCheck(c, type);
			return s;
		}

		public static String shiftStringLeft(String s, int n) {
			return s.substring(n) + s.substring(0, n);
		}
	}
}