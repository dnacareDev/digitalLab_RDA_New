package com.digitalLab.ServiceImpl;


import com.digitalLab.Aria.Cipher;
import com.digitalLab.Entity.Users;
import com.digitalLab.Service.LoginService;
import com.digitalLab.Service.UserService;
import com.digitalLab.Small.Mapper.RdaUserMapper;
import com.digitalLab.Util.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
	
    @Autowired
    private RdaUserMapper rdaMapper;

    @Autowired
    private UserService userService;

    @Override
    public Users login(String account) {
    	System.out.println("account : "+account);
        String decryptName = Cipher.ariaDecoding(account);
        
        // 옹달샘 연동 부분
        Users user = null;
        
        System.out.println("user id : "+decryptName);
        
        if(decryptName.equals("admin01") || decryptName.equals("user01") || decryptName.equals("user02")) {
        	
        	System.out.println("테스트 로그인");
        	user = userService.selectUserByAccount(decryptName);
        	
        } else {
        	
        	System.out.println("옹달샘 로그인");
        	user = rdaMapper.selectUserByAccount(decryptName);
        }	
        
        if(user == null){
        	System.out.println("유저 데이터가 존재하지 않습니다.");
        	return null;
        }
        
        Users loginUser = userService.userLogin(user);
        
        if(loginUser != null) {
        	AuthUtil.sessionLogin(loginUser);
        }
        
        return loginUser;
    }
}
