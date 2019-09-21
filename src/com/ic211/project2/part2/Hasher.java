package com.ic211.project2.part2;

public interface Hasher {
    default String getAlgName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    String hash(String plain);
}
