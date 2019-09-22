package com.ic211.project2.part1;

/**
 * An exception to handle characters that are out of developer-defined bounds.
 */
public class CharacterOutOfBoundsException extends RuntimeException {
	private char character;

	/**
	 * @param message The exception message to pass to the super class <code>RuntimeException</code>.
	 * @param c       The character on which the exception occurred.
	 */
	public CharacterOutOfBoundsException(String message, char c) {
		super(message);
		this.character = c;
	}

	/**
	 * @return The character on which the exception occurred.
	 */
	public char getCharacter() {
		return character;
	}
}
