package com.ic211.project2.part2;

import com.ic211.project2.part1.TestEncryptors;

public class ShiftClear implements Hasher {

    public ShiftClear(char[] key) {
        for (char c : key) TestEncryptors.UtilityMethods.characterBoundsCheck(c, "key");
    }

    public String getAlgName() {
        return "clear";
    }

    @Override
    public String hash(String plain) {
        for (char c : plain.toCharArray()) TestEncryptors.UtilityMethods.characterBoundsCheck(c, "plaintext");
        if (plain.length() < 16) {
            StringBuilder plainBuilder = new StringBuilder(plain);
            for (int i = plainBuilder.length(); i < 16; i++)
                plainBuilder.append('x');
            plain = plainBuilder.toString();
        }
        return plain;
    }

}