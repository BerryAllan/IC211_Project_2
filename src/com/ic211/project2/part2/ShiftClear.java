package com.ic211.project2.part2;

import com.ic211.project2.part1.TestEncryptors;

/**
 * A hashing algorithm that does not modify the original string, besides adding 'x's if the original string is less than 16 characters long.
 */
public class ShiftClear implements Hasher {
	/**
	 * @param key The password that this <code>Hasher</code> uses.
	 */
	public ShiftClear(char[] key) {
		//Checks to ensure no characters are out of bounds.
		for (char c : key) TestEncryptors.UtilityMethods.characterBoundsCheck(c, "key");
	}

	public String getAlgName() {
		return "clear";
	}

	@Override
	public String hash(String plain) {
		//Ensures that there are no invalid characters in the plaintext.
		for (char c : plain.toCharArray()) TestEncryptors.UtilityMethods.characterBoundsCheck(c, "plaintext");
		//Simply appends 'x's until plain is 16 characters long.
		StringBuilder plainBuilder = new StringBuilder(plain);
		for (int i = plainBuilder.length(); i < 16; i++)
			plainBuilder.append('x');
		plain = plainBuilder.toString();

		return plain;
	}

}