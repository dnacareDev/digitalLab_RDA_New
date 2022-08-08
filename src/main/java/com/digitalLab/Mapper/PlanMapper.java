package com.digitalLab.Mapper;

import com.digitalLab.Entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PlanMapper {

    public String getCode(String group_code);

    public int getSeqId();

    public int registPlanEasy(PlanEasyParam plan);

    public List<Plan> selectPlanList(@Param("param") PlanSearchParam param , @Param("user") Users user);

    public Plan selectPlanById(int plan_id);

    public Plan selectPlanByCode(String plan_code);

    public int registPlan(Plan plan);

    public int registPlanSeedList(@Param("list") List<Integer> planList,
                              @Param("plan_id") int plan_id);

    public int registPlanMethod(PlanMethod planMethod);

    public int registPlanSeed(@Param("seed_id") int seed_id,
                              @Param("plan_id") int plan_id);

    public List<PlanMethod> selectPlanMethod(int plan_id);

    public List<Integer> selectPlanSeedPk(int plan_id);

    public Plan selectPlanEasyById(int plan_id);

    public int modifyPlanEasy(PlanEasyParam plan);

    public int deletePlanMethodByPlan(int plan_id);

    public int deletePlanSeedByPlan(int plan_id);

    public int modifyPlan(Plan plan);

    public int modifyPlanSet(PlanSetParam plan);

    public Map<String,Object> selectScheduleByPlan(int sch_id);

    public Plan selectPlanBySchedule(int sch_id);

    public List<PlanResult> selectPlanByMethod(int method_id);

    public int modifyPlanState(@Param("plan_id") int plan_id, @Param("plan_state") int plan_state);
    
    public int registPlanTemplate(Plan plan);

    public List<PlanApiParam> selectPlanByApi(String account);

    public PlanApiDetailParam selectPlanByApiDetail(int plan_id);

    public Plan selectPlanTemplateById(int plan_id);

    int updatePlanTemplate(Plan plan);

    public int updateLatestPlanData(@Param("plan_plate") int plan_plate,
                                    @Param("plan_step") int plan_step,
                                    @Param("plan_id") int plan_id);

    public int updatePlanSetEasy(PlanSetEasyParam plan);

    public int registPlanFile(PlanFile planFile);

    public int modifyPlanFile(PlanFile planFile);

    public PlanFile selectPlanFile(PlanFile planFile);

    public List<PlanFile> selectPlanFileByPlan(int plan_id);

    int updatePlanSetTemplate(PlanSetEasyParam plan);

    public PlanBatchResult selectPlanByBatch(int batch_id);
}
