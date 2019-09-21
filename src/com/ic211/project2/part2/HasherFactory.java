package com.ic211.project2.part2;

import com.ic211.project2.part1.HasherNotFoundException;

public class HasherFactory {
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
				System.out.println(message);
				throw new HasherNotFoundException(message);
		}
	}
}
