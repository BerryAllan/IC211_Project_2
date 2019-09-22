package com.ic211.project2.part2;

/**
 * An interface from which to derive the necessary methods to override for the hashing/encryption of a plaintext given a password.
 */
public interface Hasher {

	/**
	 * @return The name of the algorithm. Typically the name of the class in lower case or "shift+[the name of the encryption algorithm]".
	 */
	default String getAlgName() {
		return this.getClass().getSimpleName().toLowerCase();
	}

	/**
	 * Updated for <a href="https://www.usna.edu/Users/cs/nchamber/courses/ic211/s19/proj/p2/fullshift.html">Part 7</a>.
	 * <br/>
	 * See original algorithm at <a href="https://www.usna.edu/Users/cs/nchamber/courses/ic211/s19/proj/p2/shift.html">Shift</a>
	 *
	 * @param plain The plaintext to hash.
	 * @return The hashed plaintext.
	 */
	String hash(String plain);
}
