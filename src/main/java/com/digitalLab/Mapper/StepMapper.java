package com.digitalLab.Mapper;

import java.util.List;

import com.digitalLab.Entity.PlanStep;
import com.digitalLab.Entity.StepImage;
import org.apache.ibatis.annotations.Mapper;

import com.digitalLab.Entity.Step;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StepMapper {
		
	public int insertStep(Step step);
	
	public List<Step> methodStepList(int method_id);

	public int getSeqId();

	public Step selectStepById(int step_id);

	public int stepTotalCount(int method_id);

	public PlanStep selectPlanStep(PlanStep stepParam);

	public int registPlanStep(PlanStep stepParam);

	public List<StepImage> selectStepImageByStep(int plan_step_id);

	public int registStepImage(StepImage stepImage);
	
	public int modifyStepImage(StepImage stepImage);
	
	public int getSeqNextVal(String table);

	public int deleteStepImage(int step_image_id);
	
	public int deleteStep(int method_id);
	
}

