package com.ic211.project2.part1;

public class EncryptorFactory {
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
