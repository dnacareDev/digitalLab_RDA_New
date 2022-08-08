package com.digitalLab.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor { 
	
	private static final String SESSION_ID = "LOGIN_MEMBER"; 
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { 
		
		String requestURI = request.getRequestURI(); 
		
		HttpSession session = request.getSession(false); 
		

		if (session == null || session.getAttribute(SESSION_ID) == null) { 
			
			System.out.println("미인증 사용자 요청"); // 로그인으로 redirect
			
			response.sendRedirect("/auth/testLogin");
			//response.sendRedirect("http://10.30.220.189:80");
			
			return false;
		} 
		
		return true; 
	} 
}
