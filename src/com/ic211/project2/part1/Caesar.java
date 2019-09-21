package com.ic211.project2.part1;

/**
 * This class provides implementation for a caesar shift encryption and decryption.
 */
public class Caesar implements Encryptor {
    private char sc;

    public Caesar(char[] key) {
        int tempSc = 18;
        for (char c : key) {
            TestEncryptors.UtilityMethods.characterBoundsCheck(c, "key");
            tempSc += ((int) c - 42);
        }
        tempSc %= 81;
        sc = (char) (42 + tempSc);
    }

    @Override
    public String encrypt(String plain) {
        StringBuilder stringBuilder = new StringBuilder();
        int k = (int) sc - 42;
        for (char pc : plain.toCharArray()) {
            TestEncryptors.UtilityMethods.characterBoundsCheck(pc, "plaintext");
            int p = (int) pc - 42;
            int c = (char) ((p + k) % 81);
            char cc = (char) (42 + c);
            stringBuilder.append(cc);
        }
        return stringBuilder.toString();
    }

    @Override
    public String decrypt(String cipher) {
        StringBuilder stringBuilder = new StringBuilder();
        int k = (int) sc - 42;
        for (char cc : cipher.toCharArray()) {
            int c = (int) cc - 42;
            int p = (c + (81 - k)) % 81;
            char pc = (char) (42 + p);
            stringBuilder.append(pc);
        }
        return stringBuilder.toString();
    }
}
