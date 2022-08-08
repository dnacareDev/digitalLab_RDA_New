package com.digitalLab.Controller;

import com.digitalLab.Entity.Batch;
import com.digitalLab.Entity.ModifyBatchParam;
import com.digitalLab.Entity.Schedule;
import com.digitalLab.Service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BatchController {
    @Autowired
    private BatchService batchService;

    @GetMapping("api/batch-plate")
    @ResponseBody
    public Map<String,Object> selectBatchByPlate(@RequestParam int plate_id){
        Map<String,Object> result = new HashMap<>();
        List<Map<String,Object>> batchList = batchService.selectBatchByPlate(plate_id);
        result.put("list", batchList);

        return result;
    }
    
    @GetMapping("api/subStep")
    @ResponseBody
    public Map<String,Object> selectBatchSubStep(@RequestParam int plate_id , @RequestParam int step_id){
        Map<String,Object> result = new HashMap<>();
        List<Map<String,Object>> batchList = batchService.selectBatchSubStep(plate_id , step_id);
        result.put("list", batchList);

        return result;
    }

    @GetMapping("api/sample-batch")
    @ResponseBody
    public Map<String,Object> selectBatchBySample(@RequestParam int batch_id){
        Map<String,Object> result = new HashMap<>();
        List<Map<String,Object>> batchList = batchService.selectBatchByPlate(batch_id);
        result.put("list", batchList);

        return result;
    }

    @GetMapping("api/sample-data")
    @ResponseBody
    public Map<String,Object> selectSampleList(@RequestParam int batch_id){
        Map<String,Object> result = new HashMap<>();
        List<Map<String,Object>> batchList = batchService.selectSampleList(batch_id);
        result.put("list", batchList);

        return result;
    }

    @GetMapping("api/batch-plan")
    @ResponseBody
    public Map<String,Object> selectBatchByPlan(@RequestParam int plan_id){
        Map<String,Object> result = new HashMap<>();
        List<Map<String,Object>> batchList = batchService.selectBatchResult(plan_id);
        result.put("list", batchList);

        return result;
    }
    
    @PostMapping("api/modify-batch")
    @ResponseBody
    public int modifyBatch(@ModelAttribute ModifyBatchParam param){
        int result = batchService.modifySampleData(param);
        return result;
    }
    
    @PostMapping("/api/modify-substep")
    @ResponseBody
    public int modifySubStep(@ModelAttribute ModifyBatchParam param){
    	int result = batchService.modifySubStepData(param);
    	
        return 1;
    }

    @PostMapping("api/batch/result")
    @ResponseBody
    public int modifyBatchResult(@ModelAttribute ModifyBatchParam param){
        int result = batchService.modifySampleResult(param);
        return result;
    }

    @GetMapping("check/batch")
    @ResponseBody
    public boolean checkBatchValue(@RequestParam int batch_id, @RequestParam int step_id){

        return batchService.checkSampleValue(batch_id,step_id);
    }
}
