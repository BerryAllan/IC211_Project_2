package com.ic211.project2.part1;

/**
 * An exception to handle any formatting errors in a vault file.
 */
public class VaultFormatException extends RuntimeException {
	/**
	 * @param message The exception message to pass to the super class.
	 */
	public VaultFormatException(String message) {
		super(message);
	}
}
