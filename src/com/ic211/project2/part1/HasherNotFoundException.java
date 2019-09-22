package com.ic211.project2.part1;

/**
 * An exception to handle when the user attempts to use an invalid <code>Hasher</code>.
 */
public class HasherNotFoundException extends RuntimeException {
	/**
	 * @param message The exception message to pass to the super class.
	 */
	public HasherNotFoundException(String message) {
		super(message);
	}
}
