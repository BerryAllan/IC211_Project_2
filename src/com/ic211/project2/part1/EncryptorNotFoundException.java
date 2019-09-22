package com.ic211.project2.part1;

/**
 * An exception to handle when the user attempts to use an invalid <code>Encryptor</code>.
 */
public class EncryptorNotFoundException extends RuntimeException {
	/**
	 * @param message The exception message to pass to the super class.
	 */
	public EncryptorNotFoundException(String message) {
		super(message);
	}
}
