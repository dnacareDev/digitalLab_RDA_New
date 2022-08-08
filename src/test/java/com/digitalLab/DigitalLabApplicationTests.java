package com.digitalLab;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.digitalLab.Aria.Cipher;

@SpringBootTest
class DigitalLabApplicationTests {
	
	
	@Test
	public void test() {
		
		String account = "user01";
		
		byte[] key = new byte[32];
	    for(int i = 0; i < key.length; i++){
	        key[i] = (byte)i;
	    }
	
	    String encodingName = "plantdoctor7";
	
	    try{
	    	encodingName = Cipher.encrypt(account, key, key.length * 8, "UTF-8");
	    }
	    catch (InvalidKeyException ex){
	    	System.out.println("InvalidKeyException");
	    }
	    catch (UnsupportedEncodingException ex){
	    	System.out.println("UnsupportedEncodingException");
	    }
	    
	    System.out.println("encodingName : "+encodingName);
	    
	}
}
