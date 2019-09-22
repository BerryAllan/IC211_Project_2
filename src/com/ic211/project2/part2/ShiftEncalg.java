package com.ic211.project2.part2;

import com.ic211.project2.part1.Encryptor;
import com.ic211.project2.part1.EncryptorFactory;
import com.ic211.project2.part1.TestEncryptors;

import java.util.ArrayList;

public abstract class ShiftEncalg implements Hasher {
	protected Encryptor encryptor;
	private char[] key;

	/**
	 * Checks the key for any out of bounds characters.
	 *
	 * @param key The password to use for the <code>Encryptor</code> in the hashing algorithm.
	 */
	public ShiftEncalg(char[] key) {
		this.key = key;
		for (char c : key) TestEncryptors.UtilityMethods.characterBoundsCheck(c, "key");
	}

	@Override
	public String getAlgName() {
		return "shift+" + encryptor.getAlgName();
	}

	@Override
	public String hash(String plain) {
		//Implementation of the full shift+encalg hashing algorithm for Part 7.
		ArrayList<String> splittedPlain = splitPlaintext(plain, 16);
		String x = "GO_NAVY_2018^mid";
		for (String s : splittedPlain) {
			encryptor = EncryptorFactory.getEncryptor(encryptor.getAlgName(), s.toCharArray());
			for (int j = 0; j < x.length(); j++) {
				char c = x.toCharArray()[j];
				int k = (int) c % 16;
				x = TestEncryptors.UtilityMethods.shiftStringLeft(x, k);
				x = encryptor.encrypt(x);
			}
		}
		return x;
	}

	/**
	 * @param plaintext The plaintext to split.
	 * @param numChars  The number of characters by which to split the plaintext.
	 * @return An <code>ArrayList</code> of the splitted plaintext.
	 */
	private ArrayList<String> splitPlaintext(String plaintext, int numChars) {
		ArrayList<String> strings = new ArrayList<>();
		for (int i = 0; i < plaintext.length(); i += numChars) {
			strings.add(plaintext.substring(i, Math.min(i + numChars, plaintext.length())));
		}
		return strings;
	}
}
