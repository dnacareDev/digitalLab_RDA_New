package com.digitalLab.ServiceImpl;

import com.digitalLab.Entity.PlanStep;
import com.digitalLab.Entity.Step;
import com.digitalLab.Entity.StepImage;
import com.digitalLab.Entity.StepImageList;
import com.digitalLab.Mapper.InputMapper;
import com.digitalLab.Mapper.StepMapper;
import com.digitalLab.Service.FileService;
import com.digitalLab.Service.StepService;
import com.digitalLab.Util.Base64Encoding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StepServiceImpl implements StepService {
    @Autowired
    private StepMapper stepMapper;
    
    @Autowired
    private InputMapper inputMapper;

    @Autowired
    private FileService fileService;

    private static final String type = "step";
    
    
    @Override
    public List<Step> selectStepByMethod(int method_id) {
        List<Step> stepList = stepMapper.methodStepList(method_id);

        return stepList;
    }

    @Override
    public Step selectStepById(int step_id) {
        Step step = stepMapper.selectStepById(step_id);
        step.setInputList(inputMapper.selectStepInput(step.getStep_id()));
        
        return step;
    }

    @Override
    public Map<String, Object> selectPlanImage(PlanStep stepParam) {
        Map<String,Object> result = new HashMap<>();
        PlanStep step = stepMapper.selectPlanStep(stepParam);
        if(step == null){
            int plan_step_id = stepMapper.getSeqNextVal("PLAN_STEP_SEQ_ID");
            stepParam.setPlan_step_id(plan_step_id);

            int count = stepMapper.registPlanStep(stepParam);
            if(count != 1){
                result.put("state", -1);
                return result;
            }
            step = stepParam;
        }
        List<StepImage> imageList = stepMapper.selectStepImageByStep(step.plan_step_id);
        
        for(StepImage image : imageList) {
        	
        	System.out.println(image);
        	String[] imageArr = image.getATCH_AT_CODE().split("\\.");
        	System.out.println(imageArr[0]);
        	System.out.println(imageArr[1]);
        	try {
        		String extention = Base64Encoding.base64Decoding(imageArr[imageArr.length-1]);
        		System.out.println(extention);
				image.setATCH_AT_CODE(imageArr[0]+"."+extention);
				
			} catch (UnsupportedEncodingException e) {
				System.out.println("UnsupportedEncodingException");
			}
        }
        
        result.put("state", 1);
        result.put("plan_step_id", step.plan_step_id);
        result.put("list",imageList);

        return result;
    }

    @Override
    public int registStepImageList(StepImageList imageList) {
        
    	int result = -1;
    	
    	List<StepImage> stepImageList = imageList.getStepImageList();
        
    	if(!stepImageList.isEmpty()) {
    		
	        for(StepImage image : stepImageList){
	        	
	        	if(image.getStep_image_id() == -1) {
		        	
		            if(image.getImg() == null){
		                return -23;
		            }
		            
		            String fileName = "";
		            
		            try {
		                fileName = fileService.uploadAndGetFilePath(image.getImg(),type);
		            }
		            catch (IOException e){
		                System.out.println("IOException");
		                return -3;
		            }
		            image.setATCH_AT_CODE(fileName);
		            image.setORIG_FILE_NM(image.getImg().getOriginalFilename());
		            
		            result = stepMapper.registStepImage(image);
		            
		            if(result <= 0) {
		            	return 0;
		            }
		            
	        	}else {
	        		
	        		result = stepMapper.modifyStepImage(image);
	        		
	        		if(result <= 0) {
	        			return 0;
		            }
	        	}
	        }
	        
    	}
    	
        return result;
    }

    @Override
    public int deleteStepImage(int step_image_id) {
        int result = -1;
        result = stepMapper.deleteStepImage(step_image_id);
        return result;
    }
}
