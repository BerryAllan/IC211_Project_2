package com.ic211.project2.part1;

/**
 * An encryption algorithm that does not modify the original string.
 */
public class Clear implements Encryptor {

	/**
	 * @param key The password with which to encrypt a message.
	 */
	public Clear(char[] key) {
		for (char c : key) TestEncryptors.UtilityMethods.characterBoundsCheck(c, "key");
	}

	public String encrypt(String plain) {
		for (char c : plain.toCharArray()) TestEncryptors.UtilityMethods.characterBoundsCheck(c, "plaintext");
		return plain;
	}

	public String decrypt(String cipher) {
		return cipher;
	}
}