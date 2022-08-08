package com.digitalLab.Controller;

import com.digitalLab.Entity.DivisionSearchParam;
import com.digitalLab.Service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class DivisionController {
    @Autowired
    private DivisionService divisionService;

    @GetMapping("search-division")
    @ResponseBody
    Map<String,Object> selectDivisionList(@ModelAttribute DivisionSearchParam searchParam){
        Map<String,Object> map = divisionService.selectDivisionListByParents(searchParam);

        return map;
    }

    @GetMapping("division/division-result")
    @ResponseBody
    Map<String,Object> selectDvisionResult(@RequestParam int report_id){
        Map<String,Object> result = divisionService.selectDivisionByReport(report_id);

        return result;
    }


}
