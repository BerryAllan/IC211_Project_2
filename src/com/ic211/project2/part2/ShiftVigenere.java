package com.ic211.project2.part2;

import com.ic211.project2.part1.Vigenere;

/**
 * This class provides implementation for a vigenere shift hasher.
 */
public class ShiftVigenere extends ShiftEncalg {
	/**
	 * @param key The password to use for the <code>Encryptor</code> in the hashing algorithm.
	 */
	public ShiftVigenere(char[] key) {
		super(key);
		encryptor = new Vigenere(key);
	}

}
