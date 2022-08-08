package com.digitalLab.Aria;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;

import cyberdigm.custom.util.base64.Base64;
import cyberdigm.custom.util.padding.BlockPadding;

/**
 * AIRA algorithm to encrypt or decrypt the data is the class that provides the
 * ability to.
 * 
 * @author shson
 *
 */

/*
 * 2014.08.19 ������ : [TODO_Encrypt_ARIA256] ARIA256�� ���� �⺻ �ҽ� 2014.09.16
 * ������ : [TODO_Encrypt_ARIA265_Modified] decrypt ���� URL ���ڵ� �� ���� ����
 * (WAS ��ü���� ���ֱ� ������ �������� �ٲ�)
 */

public class Cipher {

	/**
	 * ARIA encryption algorithm block size
	 */
	private static final int ARIA_BLOCK_SIZE = 16;

	/**
	 * ARIA algorithm to encrypt the data.
	 * 
	 * @param data    Target Data
	 * @param key     Masterkey
	 * @param keySize Masterkey Size
	 * @param charset Data character set
	 * @return Encrypted data
	 * @throws UnsupportedEncodingException If character is not supported
	 * @throws InvalidKeyException          If the Masterkey is not valid
	 */
	public static String encrypt(String data, byte[] key, int keySize, String charset)
			throws UnsupportedEncodingException, InvalidKeyException {

		byte[] encrypt = null;
		if (charset == null) {
			encrypt = BlockPadding.getInstance().addPadding(data.getBytes(), ARIA_BLOCK_SIZE);
		} else {
			encrypt = BlockPadding.getInstance().addPadding(data.getBytes(charset), ARIA_BLOCK_SIZE);
		}

		ARIAEngine engine = new ARIAEngine(keySize);
		engine.setKey(key);
		engine.setupEncRoundKeys();

		int blockCount = encrypt.length / ARIA_BLOCK_SIZE;
		for (int i = 0; i < blockCount; i++) {

			byte buffer[] = new byte[ARIA_BLOCK_SIZE];
			System.arraycopy(encrypt, (i * ARIA_BLOCK_SIZE), buffer, 0, ARIA_BLOCK_SIZE);

			buffer = engine.encrypt(buffer, 0);
			System.arraycopy(buffer, 0, encrypt, (i * ARIA_BLOCK_SIZE), buffer.length);
		}
		
		String result = null;
		
		if(encrypt != null) {
			try {
				result = URLEncoder.encode(Base64.toString(encrypt));
			} catch (Exception e) {
				System.out.println("Exception");
				return null;
			}
		}else {
			throw new NullPointerException();
		}
		
		return result;
	}

	/**
	 * ARIA algorithm to decrypt the data.
	 * 
	 * @param data    Target Data
	 * @param key     Masterkey
	 * @param keySize Masterkey Size
	 * @param charset Data character set
	 * @return Decrypted data
	 * @throws UnsupportedEncodingException If character is not supported
	 * @throws InvalidKeyException          If the Masterkey is not valid
	 */
	public static String decrypt(String data, byte[] key, int keySize, String charset)
			throws UnsupportedEncodingException, InvalidKeyException {
		
		System.out.println("data : "+data);
		data = data.trim();
		byte[] decrypt = Base64.toByte(data);
		
		System.out.println("byte[] decrypt : "+decrypt);

		ARIAEngine engine = new ARIAEngine(keySize);
		engine.setKey(key);
		engine.setupDecRoundKeys();
		
		int blockCount = 0;
		
		try {
			blockCount = decrypt.length / ARIA_BLOCK_SIZE;
		} catch (NullPointerException e) {
			System.out.println("NullPointerException");
			// TODO: handle exception
		}
		
		for (int i = 0; i < blockCount; i++) {

			byte buffer[] = new byte[ARIA_BLOCK_SIZE];
			System.arraycopy(decrypt, (i * ARIA_BLOCK_SIZE), buffer, 0, ARIA_BLOCK_SIZE);

			buffer = engine.decrypt(buffer, 0);
			System.arraycopy(buffer, 0, decrypt, (i * ARIA_BLOCK_SIZE), buffer.length);
		}

		if (charset == null) {
			return new String(BlockPadding.getInstance().removePadding(decrypt, ARIA_BLOCK_SIZE));
		} else {
			return new String(BlockPadding.getInstance().removePadding(decrypt, ARIA_BLOCK_SIZE), charset);
		}
	}

	/**
	 * The sample code in the Cipher class
	 * 
	 * @param account none
	 */
	
	public static String ariaEncoding(String account) {
		 byte[] key = new byte[32];
	        for(int i = 0; i < key.length; i++){
	            key[i] = (byte)i;
	        }

	        String encodingName = "";

	        try{
	        	encodingName = Cipher.encrypt(account, key, key.length * 8, "UTF-8");
				encodingName = URLDecoder.decode(encodingName, "UTF-8");
	        }
	        catch (InvalidKeyException ex){
	        	System.out.println("InvalidKeyException");
	            return "error";
	        }
	        catch (UnsupportedEncodingException ex){
	        	System.out.println("UnsupportedEncodingException");
	        	return "error";
	        }
	        
	        return encodingName;
	}
	
	public static String ariaDecoding(String account) {
		 byte[] key = new byte[32];
	        for(int i = 0; i < key.length; i++){
	            key[i] = (byte)i;
	        }

	        String decryptName = "";

	        try{
	            decryptName = decrypt(account, key, key.length * 8, "UTF-8");
	        }
	        catch (InvalidKeyException ex){
	        	System.out.println("InvalidKeyException");
	        	return "error";
	        }
	        catch (UnsupportedEncodingException ex){
	        	System.out.println("UnsupportedEncodingException");
	        	return "error";
	        }
	        
	        return decryptName;
	}
}
