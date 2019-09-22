package com.ic211.project2.part2;

import com.ic211.project2.part1.Caesar;

/**
 * This class provides implementation for a caesar shift encryption and decryption.
 */
public class ShiftCaesar extends ShiftEncalg {
	/**
	 * @param key The password to use for the <code>Encryptor</code> in the hashing algorithm.
	 */
	public ShiftCaesar(char[] key) {
		super(key);
		encryptor = new Caesar(key);
	}
}
