package com.github.aureliano.verbum_domini.core.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.github.aureliano.verbum_domini.core.bean.UserBean;
import com.github.aureliano.verbum_domini.core.exception.VerbumDominiException;

public final class HashHelper {

	private static final String MD5 = "MD5";
	private static final byte PASSWORD_HASH_SEED = 10;
	private static final byte SALT_NUMBER_SIZE = 5;
	
	private HashHelper() {
		super();
	}
	
	public static String generateSaltNumber() {
		Random random = new Random(System.nanoTime());
		StringBuilder saltNumber = new StringBuilder();
		
		for (byte i = 0; i < SALT_NUMBER_SIZE; i++) {
			saltNumber.append(random.nextInt(10));
		}
		
		return saltNumber.toString();
	}
	
	public static String generatePasswordHash(String password, String saltNumber) {
		StringBuilder hash = new StringBuilder();
		
		for (byte i = 0; i < PASSWORD_HASH_SEED; i++) {
			String text = new StringBuffer(hash.toString())
				.append(password)
				.append(saltNumber)
				.toString();
			hash.append(generateHash(MD5, text));
		}
		
		return generateHash(MD5, hash.toString());
	}
	
	public static String generatePasswordHash(UserBean user) {
		return generatePasswordHash(user.getPassword(), user.getSaltNumber());
	}
	
	public static String generateHash(String algorithm, String text) {
		if ("md5".equalsIgnoreCase(algorithm)) {
			return md5(text);
		} else {
			throw new UnsupportedOperationException("Method not implemented for algorithm " + algorithm);
		}
	}
	
	private static String md5(String text) {
		MessageDigest messageDigest = null;
		
		try {
			messageDigest = MessageDigest.getInstance(MD5);
		} catch (NoSuchAlgorithmException ex) {
			throw new VerbumDominiException(ex);
		}
		
		byte[] digested = messageDigest.digest(text.getBytes());
		
		StringBuilder hash = new StringBuilder();
		for (int i = 0; i < digested.length; ++i) {
			hash.append(Integer.toHexString((digested[i] & 0xFF) | 0x100).substring(1,3));
		}
		
		return hash.toString();
	}
}