package com.digitalLab.Service;


import com.digitalLab.Entity.*;

import java.util.List;
import java.util.Map;

public interface PlanService {
	
    public int registPlanEasy(PlanEasyParam plan);
    public Map<String, Object> registPlan(Plan plan);
    public int registPlanTemplate(Plan plan);
    
    public List<Plan> selectPlanList(PlanSearchParam param);

    public Map<String,Object> selectPlanById(int plan_id, int USEE_AT_CODE );

    public Map<String, Object> selectRegistPlanById(int plan_id, int USEE_AT_CODE );

    public int modifyPlan(Plan plan);

    public int registPlanMethod(PlanMethod planMethod);

    public int modifyPlanEasy(PlanEasyParam plan);

    public int setAnalistPlan(PlanSetParam plan);

    public int setAnalysisPlanEasy(PlanSetEasyParam plan);

    public void parsePlanGenralType(Plan plan);

    public List<Map<String, Object>> getGeneticList(int plan_id);

    public Map<String, Object> getPlanById(int plan_id, int plan_type, int USEE_AT_CODE);

    public Map<String, Object> selectPlanByMethod(int method_id);

    public int modifyPlanState(int plan_id, int plan_state);
    
    public Map<String , Object> selectTemplate(int plan_id);

    public List<PlanApiParam> selectPlanByApi(String account);

    public PlanApiDetailParam selectPlanByApiDetail(int plan_id);

    int updatePlanTemplate(Plan plan);

    public int updateLatestPlanData(int plate_id, int step_id, int plan_id);

    int modifyAnalysisPlanTemplate(PlanSetEasyParam plan);

    public PlanBatchResult selectPlanByBatch(int batch_id);
}
