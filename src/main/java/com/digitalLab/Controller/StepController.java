package com.digitalLab.Controller;

import com.digitalLab.Entity.PlanStep;
import com.digitalLab.Entity.Step;
import com.digitalLab.Entity.StepImage;
import com.digitalLab.Entity.StepImageList;
import com.digitalLab.Service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StepController {
    @Autowired
    private StepService stepService;
    
    @GetMapping("api/step-method")
    @ResponseBody
    public  Map<String, Object> selectStepByMethod(@RequestParam int method_id){
        Map<String, Object> result = new HashMap<>();

        List<Step> stepList = stepService.selectStepByMethod(method_id);
        result.put("step",stepList);

        return result;
    }

    @GetMapping("api/step_image")
    @ResponseBody
    public Map<String,Object> selectStepList(@ModelAttribute PlanStep stepParam){
        Map<String,Object> result = stepService.selectPlanImage(stepParam);
        return result;
    }

    @PostMapping("api/step_image")
    @ResponseBody
    public int registStepImage(@ModelAttribute StepImageList stepImageListTo){
        int result = -1;
        result = stepService.registStepImageList(stepImageListTo);
        return result;
    }

    @PostMapping("api/delete_image")
    @ResponseBody
    public int deleteStepImage(@RequestParam int step_image_id){
    	int result = -1;
    	result = stepService.deleteStepImage(step_image_id);
        return result;
    }
    
    @GetMapping("api/step/{step_id}")
    @ResponseBody
    public Step selectStepById(@PathVariable int step_id) {
    	return stepService.selectStepById(step_id);
    }
}
