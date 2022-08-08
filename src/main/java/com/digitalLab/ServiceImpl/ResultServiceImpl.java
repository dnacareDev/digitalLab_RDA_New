package com.digitalLab.ServiceImpl;

import com.digitalLab.Entity.DataLog;
import com.digitalLab.Entity.Plan;
import com.digitalLab.Enum.RegistType;
import com.digitalLab.Mapper.PlanMapper;
import com.digitalLab.Service.BatchService;
import com.digitalLab.Service.ResultService;
import com.digitalLab.Util.DataLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    PlanMapper planMapper;

    @Autowired
    BatchService batchService;

    @Autowired
    DataLogUtil logUtil;

    private static final String type = "analysis";

    @Override
    public Map<String, Object> resultDetail(int plan_id, int USEE_AT_CODE) {
        Map<String,Object> result = getPlanById(plan_id,USEE_AT_CODE);
        List<Map<String, Object>> batch =  batchService.selectBatchResult(plan_id);
        List<DataLog> logList = logUtil.logList(plan_id, type);
        result.put("logList", logList);
        result.put("batch",batch);

        return result;
    }

    private Map<String,Object> getPlanById(int plan_id, int USEE_AT_CODE){
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
        result.put("viewName",parseViewName(plan.getUSEE_AT_CODE()));
        result.put("plan",plan);
        return result;
    }

    private static String parseViewName(int USEE_AT_CODE){
        switch (USEE_AT_CODE){
            case RegistType.GENERAL: return "regist_results/regist_results_add";
            case RegistType.EASY: return "regist_results/regist_results_add_easy";
            case RegistType.TEMPLATE: return "regist_results/regist_results_add_template";
            default: return "";
        }
    }
}
