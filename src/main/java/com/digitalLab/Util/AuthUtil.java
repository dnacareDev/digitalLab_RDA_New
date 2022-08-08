
package com.digitalLab.Util;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.digitalLab.Entity.Users;

public class AuthUtil {

	public static Users sessionUser() {

		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		
		HttpSession session = servletRequestAttribute.getRequest().getSession(true);

		Users user = (Users) session.getAttribute("LOGIN_MEMBER");

		return user;
	}
	
	public static void sessionLogin(Users user) {
		
		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		
		HttpSession session = servletRequestAttribute.getRequest().getSession(true);
		session.invalidate();
		
		session = servletRequestAttribute.getRequest().getSession(true);
		session.setMaxInactiveInterval(60*60);
		session.setAttribute("LOGIN_MEMBER" , user);
		
		System.out.println("Login User : "+session.getAttribute("LOGIN_MEMBER"));
	}
}
