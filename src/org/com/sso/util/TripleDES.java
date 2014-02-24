/**
 * 
 */
package org.com.sso.util;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Wang Piaoyang
 *
 */
public class TripleDES {
 //表示三重数据加密标准算法的基类，TripleDES 的所有实现都必须从此基类派生。
	 private static final String UNICODE_FORMAT = "UTF8";
	 private static final String DES_PWD = "GDEHRSSO";
	 public byte[] encrypt(String message) throws Exception {
	    	final MessageDigest md = MessageDigest.getInstance("md5");
	    	final byte[] digestOfPassword = md.digest(DES_PWD
	    			.getBytes("utf-8"));
	    	final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
	    	for (int j = 0, k = 16; j < 8;) {
	    		keyBytes[k++] = keyBytes[j++];
	    	}

	    	final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
	    	final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
	    	final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
	    	cipher.init(Cipher.ENCRYPT_MODE, key, iv);

	    	final byte[] plainTextBytes = message.getBytes(UNICODE_FORMAT);
	    	final byte[] cipherText = cipher.doFinal(plainTextBytes);
	    	// final String encodedCipherText = new sun.misc.BASE64Encoder()
	    	// .encode(cipherText);

	    	return cipherText;
	    }

	    public String decrypt(byte[] message) throws Exception {
	    	final MessageDigest md = MessageDigest.getInstance("md5");
	    	final byte[] digestOfPassword = md.digest(DES_PWD
	    			.getBytes(UNICODE_FORMAT));
	    	final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
	    	for (int j = 0, k = 16; j < 8;) {
	    		keyBytes[k++] = keyBytes[j++];
	    	}

	    	final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
	    	final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
	    	final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
	    	decipher.init(Cipher.DECRYPT_MODE, key, iv);

	    	// final byte[] encData = new
	    	// sun.misc.BASE64Decoder().decodeBuffer(message);
	    	final byte[] plainText = decipher.doFinal(message);

	    	return new String(plainText, UNICODE_FORMAT);
	    }


	    public static void main(String args []) throws Exception
	    {
	    	String text = "kyle boon";

	    	byte[] codedtext = new TripleDES().encrypt(text);
	    	String decodedtext = new TripleDES().decrypt(codedtext);

	    	System.out.println(codedtext); // this is a byte array, you'll just see a reference to an array
	    	System.out.println(decodedtext); // This correctly shows "kyle boon"

	    }


}
