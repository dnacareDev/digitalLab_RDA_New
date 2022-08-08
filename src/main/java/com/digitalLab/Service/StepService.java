package com.digitalLab.Service;

import com.digitalLab.Entity.PlanStep;
import com.digitalLab.Entity.Step;
import com.digitalLab.Entity.StepImageList;

import java.util.List;
import java.util.Map;

public interface StepService {
    public  List<Step> selectStepByMethod(int method_id);

    public Step selectStepById(int step_id);

    public Map<String,Object> selectPlanImage(PlanStep stepParam);

    public int registStepImageList(StepImageList imageList);

    public int deleteStepImage(int step_image_id);
}
