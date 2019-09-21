package com.ic211.project2.part1;

public class CharacterOutOfBoundsException extends RuntimeException {
	private char character;

	public CharacterOutOfBoundsException(String message, char c) {
		super(message);
		this.character = c;
	}

	public char getCharacter() {
		return character;
	}
}
