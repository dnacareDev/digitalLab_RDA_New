package com.digitalLab.Util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Base64Encoding
{
	@RequestMapping("encoding")
	public static String base64Encoding(@RequestParam("keyword") String keyword) throws UnsupportedEncodingException
	{
		byte[] textBytes = keyword.getBytes("UTF-8");

		// Base64 인코딩 ///////////////////////////////////////////////////
		Encoder encoder = Base64.getEncoder();

		// Encoder#encode(byte[] src) :: 바이트배열로 반환
//		byte[] encodedBytes = encoder.encode(textBytes);
//		System.out.println(new String(encodedBytes));

		// Encoder#encodeToString(byte[] src) :: 문자열로 반환
		String encodedString = encoder.encodeToString(textBytes);
		
		return encodedString;
	}

	@RequestMapping("decoding")
	public static String base64Decoding(@RequestParam("keyword") String keyword) throws UnsupportedEncodingException
	{
		// Base64 디코딩 ///////////////////////////////////////////////////
		Decoder decoder = Base64.getDecoder();

		// Decoder#decode(bytes[] src)
		//byte[] decodedBytes1 = decoder.decode(encodedBytes);
		
		// Decoder#decode(String src)
		byte[] decodedBytes = decoder.decode(keyword);

		// 디코딩한 문자열을 표시
		//String decodedString = new String(decodedBytes1, "UTF-8");

		return new String(decodedBytes, "UTF-8");
	}
	
}