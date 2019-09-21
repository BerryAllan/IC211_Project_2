package com.ic211.project2.part2;

import com.ic211.project2.part1.Encryptor;
import com.ic211.project2.part1.EncryptorFactory;
import com.ic211.project2.part1.TestEncryptors;

import java.util.ArrayList;

public abstract class ShiftEncalg implements Hasher {
	protected Encryptor encryptor;
	protected char[] key;

	public ShiftEncalg(char[] key) {
		this.key = key;
		for (char c : key) TestEncryptors.UtilityMethods.characterBoundsCheck(c, "key");
	}

	@Override
	public String getAlgName() {
		return "shift+" + encryptor.getAlgName();
	}

	//Updated for part 7
	@Override
	public String hash(String plain) {
		ArrayList<String> splittedPlain = splitPlaintext(plain, 16);
		String x = "GO_NAVY_2018^mid";
		for (String s : splittedPlain) {
			encryptor = EncryptorFactory.getEncryptor(encryptor.getAlgName(), s.toCharArray());
			for (int j = 0; j < x.length(); j++) {
				char c = x.toCharArray()[j];
				int k = (int) c % 16;
				x = TestEncryptors.UtilityMethods.shiftStringLeft(x, k);
				x = encryptor.encrypt(x);
			}
		}
		return x;
	}

	private ArrayList<String> splitPlaintext(String plaintext, int numChars) {
		ArrayList<String> strings = new ArrayList<>();
		for (int i = 0; i < plaintext.length(); i += numChars) {
			strings.add(plaintext.substring(i, Math.min(i + numChars, plaintext.length())));
		}
		return strings;
	}
}
