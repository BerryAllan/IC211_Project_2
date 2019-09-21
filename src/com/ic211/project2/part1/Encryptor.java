package com.ic211.project2.part1;

public interface Encryptor {
    default String getAlgName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    String encrypt(String plain);

    String decrypt(String cipher);
}
