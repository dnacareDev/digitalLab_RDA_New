package com.digitalLab.Controller;

import com.digitalLab.Entity.Users;
import com.digitalLab.Entity.Verification;
import com.digitalLab.Service.VerificationService;
import com.digitalLab.Util.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class VerificationController {
    @Autowired
    private VerificationService verificationService;
    
    // 데이터 검증 리스트
    @GetMapping("example_list")
    public ModelAndView example_list(ModelAndView mv) {
        Map<String,Object> result = verificationService.selectVerificationList();

        Users user = AuthUtil.sessionUser();
		
		mv.addObject("user", user);
        mv.addObject("result", result);
        mv.setViewName("example/example_list");
        return mv;
    }

    @GetMapping("example/example_regist")
    public ModelAndView registExample(ModelAndView mv){
        mv.setViewName("example/example_regist");
        return mv;
    }

    @GetMapping("example/example_modify")
    public ModelAndView registExample(ModelAndView mv, @RequestParam int verification_id){
        mv.setViewName("example/example_modify");

        Map<String,Object> result = verificationService.selectVerificationById(verification_id);
        mv.addObject("result", result);
        return mv;
    }

    @PostMapping("example/example_modify")
    @ResponseBody
    public int modifyVerification(@ModelAttribute Verification verification){
        int result = -1;
        result = verificationService.modifyVerification(verification);
        return result;
    }

    @PostMapping("example/example_regist")
    @ResponseBody
    public int registExample(@ModelAttribute Verification verification){
        int result = -1;
        result = verificationService.registVerification(verification);
        return result;
    }
}
