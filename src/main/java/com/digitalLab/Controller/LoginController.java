package com.digitalLab.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.digitalLab.Entity.Users;
import com.digitalLab.Service.LoginService;
import com.digitalLab.Service.UserService;
import com.digitalLab.Small.Mapper.RdaUserMapper;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private RdaUserMapper rdaMapper;

	@GetMapping("auth/ssoLogin")
	public ModelAndView ssoLogin(ModelAndView mv, @RequestParam("u") String u) {
		
		Users loginUser = loginService.login(u);

		if (loginUser != null) {
			mv.setViewName("redirect:/home");
		}else {
			mv.setViewName("redirect:/http://10.30.220.189:80");
		}

		return mv;
	}
	
	@RequestMapping("auth/testLogin")
	public ModelAndView testLogin(ModelAndView mv, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		Users loginUser = null;
		
		loginUser = userService.getUser("pulpelune");
		
		if(loginUser == null) {
			loginUser = userService.getUser("admin01");
		}
		
		if(loginUser == null) {
			loginUser = userService.getUser("user01");
		}
		
		if(loginUser == null) {
			loginUser = userService.getUser("user02");
		}

		mv.setViewName("redirect:/home");
		
		session.setAttribute("LOGIN_MEMBER", loginUser);
		
		return mv;
	}
	
	@GetMapping("test/list")
	public List<Users> testList(){
		
		return rdaMapper.selectUserList("12");
	}
	
	@GetMapping("test/user")
	public Users test(){
		
		return rdaMapper.selectUserByAccount("plantdoctor7");
	}
}
