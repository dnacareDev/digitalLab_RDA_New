package com.digitalLab.ServiceImpl;

import com.digitalLab.Entity.*;
import com.digitalLab.Enum.RegistType;
import com.digitalLab.Mapper.PlanMapper;
import com.digitalLab.Service.*;
import com.digitalLab.Util.DataLogUtil;
import com.digitalLab.Util.JsonUtil;
import com.digitalLab.Util.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SampleServiceImpl implements SampleService {
	
    @Autowired
    PlanMapper planMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PlateService plateService;

    @Autowired
    private MethodService methodService;

    @Autowired
    private PlanService planService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private DataLogUtil logUtil;

    @Autowired
    private UserService userService;

    private static final String type = "analysis";

    @Override
    public Map<String,Object> sampleDetail(int plan_id, int USEE_AT_CODE){
        Map<String,Object> result = getPlanById(plan_id,USEE_AT_CODE);
        return result;
    }

    @Override
    public Map<String, Object> selectPlanBySchedule(int sch_id) {
        Map<String, Object> result = new HashMap<>();
        Plan plan = planMapper.selectPlanBySchedule(sch_id);
        result.put("plan",plan);
        result = parseSampleData(plan,result);
        return result;
    }

    @Override
    public Map<String, Object> getSampleDataApi(String plan_code) {
        Map<String,Object> result = new HashMap<>();
        Plan plan = planMapper.selectPlanByCode(plan_code);

        Map<String,Object> planMap = new HashMap<>();

        List<Step> stepList = methodService.methodStepList(plan.getMethod_id());

        planMap.put("plan_id", plan.getPlan_id());
        planMap.put("analysis_item", plan.getDivision_depth_3());
        planMap.put("plan_code", plan.getPlan_code());
        planMap.put("plan_comments", plan.getPlan_contents());
        planMap.put("step",stepList);

        List<PlateApiParam> batchList = plateService.selectPlateByPlanApi(plan.getPlan_id());
        planMap.put("batch",batchList);

        List<Map<String, Object>> geneticData = planService.getGeneticList(plan.getPlan_id());

        result.put("geneticData",geneticData);
        result.put("plan",planMap);


        return result;
    }

    @Override
    public int moidfyPlanEasy(PlanFile plan){
    	
    	if(plan.getPlan_id() != SessionUtil.getPkId(type)) {
    		return -20;
    	};
    	
        String fileName = "";
        System.out.println("aaa");
        
        try {
            fileName = fileService.uploadAndGetFilePath(plan.getFile(), type);
        } catch (IOException e) {
            System.out.println("IOException");
            return -3;
        }
        System.out.println("bbb");

        
        plan.setATCH_FILE_NM(fileName);
        plan.setORIG_FILE_NM(plan.getFile().getOriginalFilename());

        System.out.println(plan.getATCH_FILE_NM());
        System.out.println(plan.getORIG_FILE_NM());
        
        System.out.println(plan.getPlan_id());
        System.out.println(plan.getPlan_type());
        
        //일단 진행이 되는지 확인. 확인 후 수정필
        //int result = planMapper.registPlanFile(plan);
        
        // 무조건 등록으로 했더니 1개만 출력되어야하는 부분이 여러개 나와서 페이지에러 -> 복구
        PlanFile file = planMapper.selectPlanFile(plan);
        if(file == null){
            int result = planMapper.registPlanFile(plan);
        }
        else{
            plan.setPlan_file_id(file.getPlan_file_id());
            int result = planMapper.modifyPlanFile(plan);
        }
        //
        
        
        planService.modifyPlanState(plan.getPlan_id(), PlanServiceImpl.PLAN_TYPE_SEED);
        Users user = userService.getLoginUser();
        logUtil.addDataLog(user.getAccount(), plan.getPlan_id(), DataLogUtil.ACTION_ANALYSIS, plan.getPlan_code(), type);

        return 0;
    }

    @Override
    public int moidfyPlanTemplate(Plan plan) {
    	
    	if(plan.getPlan_id() != SessionUtil.getPkId(type)) {
    		return -20;
    	};
    	
    	try {
			JsonUtil.createJson(plan.getPlan_code(), plan.getTemplate_head(), plan.getTemplate_body());
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}
    	
        planService.modifyPlanState(plan.getPlan_id(), PlanServiceImpl.PLAN_TYPE_SEED);
        Users user = userService.getLoginUser();
		logUtil.addDataLog(user.getAccount(), plan.getPlan_id(), DataLogUtil.ACTION_ANALYSIS, plan.getPlan_code(), type);

        return 0;
    }

    private Map<String,Object> parseSampleData(Plan plan, Map<String,Object> result){
        List<Plate> plateList = plateService.selectPlateByPlan(plan.getPlan_id());
        result.put("plate",plateList);

        List<Integer> seed_pk_list = planMapper.selectPlanSeedPk(plan.getPlan_id());
        plan.setSeed_pk_list(seed_pk_list);
        plan.setSeed_ammount(seed_pk_list.size());

        List<PlanMethod> planMethodList = plan.getPlanMethodList();
        List<Step> stepList = methodService.methodStepList(plan.getMethod_id());

        result.put("step",stepList);

        System.out.println(planMethodList);
        if(planMethodList == null || planMethodList.isEmpty()){
            System.out.println(plan);
            System.out.println("======================");
            return result;
        }

        List<Map<String, Object>> geneticData = planService.getGeneticList(plan.getPlan_id());

        result.put("geneticData",geneticData);

        return result;
    }

    private Map<String,Object> getPlanById(int plan_id, int USEE_AT_CODE){
    	
    	SessionUtil.setPkId(plan_id, type);
    	
        Map<String,Object> result = new HashMap<>();
        Plan plan = null;

        if(USEE_AT_CODE == RegistType.EASY || USEE_AT_CODE == RegistType.TEMPLATE){
            plan = planMapper.selectPlanEasyById(plan_id);
        }
        else if(USEE_AT_CODE == RegistType.GENERAL){
            plan = planMapper.selectPlanById(plan_id);
        }
        if(plan == null){
            result.put("state","-1");
            return result;
        }

        if (USEE_AT_CODE == RegistType.TEMPLATE) {
            try {
                Map<String , Object> template = JsonUtil.readJson(plan.getPlan_code());
                
                String template_head = (String)template.get("head");
                
                if(template_head != null) {
                	plan.setTemplate_head(template_head);
                }
                
                String template_body = (String)template.get("body");
                
                if(template_body != null) {
                	plan.setTemplate_body(template_body);
                }
            } catch (NullPointerException e) {
            	System.out.println("NullPointerException");
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException");
            }
        }

        result.put("reportList", reportService.reportList("seed"));
        result.put("viewName",parseViewName(plan.getUSEE_AT_CODE()));
        result.put("plan",plan);
        result = parseSampleData(plan,result);

        List<Schedule> sch_list = scheduleService.selectScheduleByPlan(plan.getPlan_id());
        result.put("schedule", sch_list);
        result.put("logList", logUtil.logList(plan_id, type));

        return result;
    }

    private static String parseViewName(int USEE_AT_CODE){
        switch (USEE_AT_CODE){
            case RegistType.GENERAL: return "sample_analysis/sample_analysis_modify";
            case RegistType.EASY: return "sample_analysis/sample_analysis_add_easy";
            case RegistType.TEMPLATE: return "sample_analysis/sample_analysis_add_template";
            default: return "";
        }
    }
}
