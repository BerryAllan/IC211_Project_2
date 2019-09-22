package com.ic211.project2.part1;

import java.util.Scanner;

public class TestEncryptors {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// Get alg,psw,msg from user
		System.out.print("algorithm: ");
		String encalg = sc.nextLine();
		//String encalg = System.console().readLine();
		System.out.print("password : ");
		String password = sc.nextLine();
		//String password = new String(System.console().readPassword());
		System.out.print("message  : ");
		String plaintext = sc.nextLine();
		//String plaintext = System.console().readLine();
		Encryptor encryptor = EncryptorFactory.getEncryptor(encalg, password.toCharArray());

		// Encrypt, decrypt, print summary of results
		String ciphertext = encryptor.encrypt(plaintext);
		String hopefully = encryptor.decrypt(ciphertext);
		System.out.println("plain : " + plaintext);
		System.out.println("cipher: " + ciphertext);
		System.out.println("decryp: " + hopefully);
	}

	public static class UtilityMethods {
		/**
		 * @param c    The character to check.
		 * @param type A string to aid in debugging the exception message.
		 */
		public static void characterBoundsCheck(char c, String type) {
			if ((int) c < 42 || (int) c > 122)
				throw new CharacterOutOfBoundsException("Error! " + c + " not allowed in " + type + ".\nThe string must only be valid ASCII characters in the range of 42-122.", c);
		}

		/**
		 * @param s    The string whose characters to check.
		 * @param type A string to aid in debugging the exception message.
		 */
		public static void characterBoundsCheck(String s, String type) {
			for (char c : s.toCharArray())
				characterBoundsCheck(c, type);
		}

		/**
		 * @param s The string to shift left.
		 * @param n The number of characters by which to shift.
		 * @return Returns the shifted string.
		 */
		public static String shiftStringLeft(String s, int n) {
			return s.substring(n) + s.substring(0, n);
		}
	}
}