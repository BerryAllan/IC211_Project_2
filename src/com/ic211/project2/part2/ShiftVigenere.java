package com.ic211.project2.part2;

import com.ic211.project2.part1.Vigenere;

/**
 * This class provides implementation for a vigenere shift encryption and decryption.
 */
public class ShiftVigenere extends ShiftEncalg {

    public ShiftVigenere(char[] key) {
        super(key);
        encryptor = new Vigenere(key);
    }

}
