package com.ic211.project2.part1;

/**
 * An interface from which to derive the necessary methods to override for encryption and decryption of message given a password.
 */
public interface Encryptor {
	/**
	 * @return Returns the name of the algorithm. By default, this is the name of the class in lower case.
	 */
	default String getAlgName() {
		return this.getClass().getSimpleName().toLowerCase();
	}

	/**
	 * @param plain The plaintext to encrypt.
	 * @return The encrypted plaintext.
	 * <br/><br/>
	 * <a href="https://www.usna.edu/Users/cs/nchamber/courses/ic211/s19/proj/p2/caesar.html">Caesar Algorithm</a>
	 * <br/>
	 * <a href="https://www.usna.edu/Users/cs/nchamber/courses/ic211/s19/proj/p2/vigenere.html">Vigenere Algorithm</a>
	 */
	String encrypt(String plain);

	/**
	 * @param cipher The ciphertext to decrypt.
	 * @return The decrypted ciphertext.
	 */
	String decrypt(String cipher);
}
