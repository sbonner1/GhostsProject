package edu.ycp.cs496.ghosts.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * @author shane
 * 
 * class to help encrypt password data. used code from here: http://www.javacodegeeks.com/2012/05/secure-password-storage-donts-dos-and.html
 *
 */
public class PasswordEncryptionService {
	
	public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] hash) throws InvalidKeySpecException, NoSuchAlgorithmException{
		
		byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, hash);
		
		return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
		
	}
	
	public byte[] getEncryptedPassword(String password, byte[] hash) throws InvalidKeySpecException, NoSuchAlgorithmException{
		String hashcode = "PBKDF2WithHmacSHA1";
		
		int keyLength = 160;
		
		int iterations = 15000;
		
		KeySpec spec = new PBEKeySpec(password.toCharArray(), hash, iterations, keyLength);
		SecretKeyFactory f = SecretKeyFactory.getInstance(hashcode);
		
		return f.generateSecret(spec).getEncoded();
	}
	
	public byte[] generateHash() throws NoSuchAlgorithmException{
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		
		byte[] hash = new byte[8];
		random.nextBytes(hash);
		
		return hash;
	}
}
