package com.digitalLab.Util;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtil {
	
	public static void setPkId(int id , String type) {

		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpSession session = servletRequestAttribute.getRequest().getSession(true);
		session.setMaxInactiveInterval(60*60);

		session.setAttribute(type , id);
	}
	
	public static int getPkId(String type) {

		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpSession session = servletRequestAttribute.getRequest().getSession(true);

		Object attribute = session.getAttribute(type);
		if(attribute != null){
			int pk_id = (Integer)attribute;
			System.out.println(pk_id);
			return pk_id;
		}

		return -1;
	}
}