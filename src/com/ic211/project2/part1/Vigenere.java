package com.ic211.project2.part1;

/**
 * This class provides implementation for encryption and decryption via the Vigenere algorithm.
 */
public class Vigenere implements Encryptor {
	private char[] key;

	/**
	 * @param key The password with which to encrypt
	 */
	public Vigenere(char[] key) {
		for (char c : key) TestEncryptors.UtilityMethods.characterBoundsCheck(c, "key");
		this.key = key;
	}

	@Override
	public String encrypt(String plain) {
		//Implementation of the Vigenere encryption algorithm
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < plain.toCharArray().length; i++) {
			char pc = plain.toCharArray()[i];
			TestEncryptors.UtilityMethods.characterBoundsCheck(pc, "plaintext");

			char sc = key[i % key.length];
			int k = (int) sc - 42;
			int p = (int) pc - 42;
			int c = (p + k) % 81;
			char cc = (char) (42 + c);
			stringBuilder.append(cc);
		}
		return stringBuilder.toString();
	}

	@Override
	public String decrypt(String cipher) {
		//Implementation of the Vigenere decryption algorithm
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < cipher.toCharArray().length; i++) {
			char cc = cipher.toCharArray()[i];
			char sc = key[i % key.length];
			int k = (int) sc - 42;
			int c = (int) cc - 42;
			int p = (c + (81 - k)) % 81;
			char pc = (char) (42 + p);
			stringBuilder.append(pc);
		}
		return stringBuilder.toString();
	}
}
