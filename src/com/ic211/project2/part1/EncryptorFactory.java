package com.ic211.project2.part1;

/**
 * A factory class to generate an <code>Encryptor</code> given the algorithm name and a password.
 */
public class EncryptorFactory {
	/**
	 * @param algName The algorithm name.
	 * @param key     The key to use as a password.
	 * @return Returns an <code>Encryptor</code> given the algorithm name and a password.
	 */
	public static Encryptor getEncryptor(String algName, char[] key) {
		switch (algName) {
			case "clear":
				return new Clear(key);
			case "caesar":
				return new Caesar(key);
			case "vigenere":
				return new Vigenere(key);
			default:
				String message = "Error! Encryption algorithm '" + algName + "' not supported.";
				System.out.println(message);
				throw new EncryptorNotFoundException(message);
		}
	}

}
