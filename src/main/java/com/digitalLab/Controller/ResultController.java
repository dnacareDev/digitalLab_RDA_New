package com.digitalLab.Controller;

import com.digitalLab.Service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ResultController {
	
    @Autowired
    ResultService resultService;

    @GetMapping("regist_results_list/result-detail")
    public ModelAndView resultDetail(@RequestParam int plan_id,
                                     @RequestParam int regist_type,
                                     ModelAndView mv){
        Map<String,Object> result = resultService.resultDetail(plan_id,regist_type);
        
        mv.setViewName(result.get("viewName").toString());
        mv.addObject("result",result);
        return mv;
    }
    
}