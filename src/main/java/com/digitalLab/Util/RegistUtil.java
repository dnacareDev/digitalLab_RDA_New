package com.digitalLab.Util;

public class RegistUtil {
	
	public static String parseRegistType(int USEE_AT_CODE) {
		switch (USEE_AT_CODE) {
		case 0:
			return "고급 등록";
		case 1:
			return "일반 등록";
		case 2:
			return "템플릿 등록";
		default:
			return "";
		}
	}

	/*
	 * public static String parseDvision(int division_id) {
	 * 
	 * return }
	 */
}
