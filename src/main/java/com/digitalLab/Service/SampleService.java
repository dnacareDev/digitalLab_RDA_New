package com.digitalLab.Service;

import com.digitalLab.Entity.Plan;
import com.digitalLab.Entity.PlanFile;

import java.util.Map;

public interface SampleService {
    public Map<String,Object> sampleDetail(int plan_id, int USEE_AT_CODE);

    public Map<String,Object> selectPlanBySchedule(int sch_id);

    public Map<String, Object> getSampleDataApi(String plan_code);

    public int moidfyPlanEasy(PlanFile plan);

    int moidfyPlanTemplate(Plan plan);
}
