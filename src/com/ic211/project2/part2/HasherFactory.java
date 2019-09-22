package com.ic211.project2.part2;

import com.ic211.project2.part1.HasherNotFoundException;

/**
 * A factory class to generate a <code>Hasher</code> given the algorithm name and a password.
 */
public class HasherFactory {
	/**
	 * @param algName The algorithm name.
	 * @param key     The key to use as a password.
	 * @return Returns a <code>Hasher</code> given the algorithm name and a password.
	 */
	public static Hasher getHasher(String algName, char[] key) {
		switch (algName) {
			case "clear":
				return new ShiftClear(key);
			case "shift+caesar":
				return new ShiftCaesar(key);
			case "shift+vigenere":
				return new ShiftVigenere(key);
			default:
				String message = "Unknown algorithm '" + algName + "'.";
				//System.out.println(message);
				throw new HasherNotFoundException(message);
		}
	}
}
