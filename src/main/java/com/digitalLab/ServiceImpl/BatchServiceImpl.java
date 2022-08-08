package com.digitalLab.ServiceImpl;

import com.digitalLab.Entity.*;
import com.digitalLab.Enum.PlateStatusType;
import com.digitalLab.Mapper.BatchMapper;
import com.digitalLab.Mapper.PlateMapper;
import com.digitalLab.Service.*;
import com.digitalLab.Util.DataLogUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BatchServiceImpl implements BatchService {
    @Autowired
    private BatchMapper batchMapper;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    private DataLogUtil logUtil;

    @Autowired
    private StepService stepService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    @Autowired
    private PlateMapper plateMapper;

    @Autowired
    private ScheduleService scheduleService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String type = "analysis";

    @Override
    public int registBatch(Batch batch) {
        int result = -1;

        result = batchMapper.registBatch(batch);

        return result;
    }

    @Override
    public int registBatchList(List<Batch> batchList, int plan_id) {
        int result = -1;

        batchMapper.deleteBatchByPlan(plan_id);
        for( int i = 0; i < batchList.size(); i++) {
        	result = batchMapper.registBatch(batchList.get(i));
        }
//        result = batchMapper.registBatchList(batchList, plan_id);

        return result;
    }

    @Override
    public List<Batch> selectBatchByPlan(int plan_id){
        List<Batch> batchList = batchMapper.selectBatchByPlan(plan_id);
        return batchList;
    }

    @Override
    public List<Map<String,Object>> selectBatchByPlate(int plate_id) {
        List<Batch> batchList = batchMapper.selectBatchByPlate(plate_id);

        List<Map<String,Object>> batchMapList = new ArrayList<>();

        batchList.forEach(batch ->{
            Map<String,Object> batchMap = parseBatchToMap(batch);
            batchMapList.add(batchMap);
        });

        return batchMapList;
    }
    
    @Override
    public List<Map<String,Object>> selectBatchSubStep(int plate_id , int step_id) {
        List<Batch> batchList = batchMapper.selectBatchSubStep(plate_id ,step_id);

        List<Map<String,Object>> batchMapList = new ArrayList<>();

        batchList.forEach(batch ->{
            Map<String,Object> batchMap = parseBatchToMap(batch);
            batchMapList.add(batchMap);
        });

        return batchMapList;
    }

    @Override
    public List<Map<String,Object>> selectSampleList(int plate_id) {
        List<Batch> batchList = batchMapper.selectBatchByPlate(plate_id);

        List<Map<String,Object>> batchMapList = new ArrayList<>();

        batchList.forEach(batch ->{
            Map<String,Object> batchMap = parseBatchToSampleMap(batch);
            batchMapList.add(batchMap);
        });

        return batchMapList;
    }

    @Override
    public int deleteBatchByPlan(int plan_id) {
        int result = -1;
        result = batchMapper.deleteBatchByPlan(plan_id);
        return result;
    }

    private Map<String,Object> parseBatchToMap(Batch batch){
        Map<String,Object> batchMap = new HashMap<>();
        if(batch.getBatch_data() != null){
            batchMap = gson.fromJson(batch.getBatch_data(), batchMap.getClass());
            
            System.out.println("batchMap : "+batchMap );
        }

        batchMap.put("batch_id", batch.getBatch_id());
        batchMap.put("batch_index", batch.getBatch_index());
        batchMap.put("batch_sample", batch.getBatch_sample());
        batchMap.put("batch_test", batch.getBatch_test());
        batchMap.put("batch_well", batch.getBatch_well());
        batchMap.put("plate_id", batch.getPlate_id());
        batchMap.put("plate_index", batch.getPlate_index());
        batchMap.put("genetic_depth_1",batch.getGenetic_depth_1());
        batchMap.put("genetic_depth_2",batch.getGenetic_depth_2());
        batchMap.put("batch_data",batch.getBatch_data());
        batchMap.put("batch_sub_id", batch.getBatch_sub_id());
        
        System.out.println(batchMap+" : batchMap");

        return batchMap;
    }

    private Map<String,Object> parseBatchToMapResult(Batch batch){
        Map<String, Object> batchMap = new HashMap<>();
        if(batch.getBatch_result() != null){
            batchMap = gson.fromJson(batch.getBatch_result(), batchMap.getClass());
        }

        batchMap.put("batch_id", batch.getBatch_id());
        batchMap.put("batch_index", batch.getBatch_index());
        batchMap.put("batch_sample", batch.getBatch_sample());
        batchMap.put("batch_test", batch.getBatch_test());
        batchMap.put("batch_well", batch.getBatch_well());
        batchMap.put("plate_id", batch.getPlate_id());
        batchMap.put("plate_index", batch.getPlate_index());
        batchMap.put("genetic_depth_1",batch.getGenetic_depth_1());
        batchMap.put("genetic_depth_2",batch.getGenetic_depth_2());
        batchMap.put("batch_result",batch.getBatch_result());

        return batchMap;
    }

    private Map<String,Object> parseBatchToSampleMap(Batch batch){
        Map<String, Object> batchMap = new HashMap<>();
        if(batch.getBatch_result() != null){
            batchMap = gson.fromJson(batch.getBatch_result(), batchMap.getClass());
        }

        batchMap.put("sample_id", batch.getBatch_id());
        batchMap.put("sample_index", batch.getBatch_index());
        batchMap.put("sample", batch.getBatch_sample());
        batchMap.put("sample_test", batch.getBatch_test());
        batchMap.put("sample_well", batch.getBatch_well());
        batchMap.put("batch_id", batch.getPlate_id());
        batchMap.put("batch_index", batch.getPlate_index());
        batchMap.put("genetic_depth_1",batch.getGenetic_depth_1());
        batchMap.put("genetic_depth_2",batch.getGenetic_depth_2());
        batchMap.put("sample_result",batch.getBatch_result());

        return batchMap;
    }

    @Transactional
    public int modifySampleData(ModifyBatchParam param){
        int result = 0;
        AtomicInteger atomicInt = new AtomicInteger(result);

        Type listType = new TypeToken<ArrayList<Batch>>(){}.getType();
        List<Batch> batchList = gson.fromJson(param.getBatchJson(), listType);

        Plate plate = plateMapper.findPlateById(param.getPlan_plate());


        if(plate.getPlate_status() == 1){
            if(!param.getType().equals("all")){
                System.out.println("return = 9");
                return -9;
            }
        }else if(plate.getPlate_status() == 2){
            System.out.println("return = 10");
            return -10;
        }

        if(param.getType().equals("batch") || param.getType().equals("all")) {
        	if(!param.isState()) {
                System.out.println("return = 7");
        		return -7;
        	}
        }
        
        batchList.forEach(batch -> {
            int count = batchMapper.updateBatchData(batch);
            atomicInt.set(atomicInt.get() + count);
        });

        if(param.isState()){
            planService.modifyPlanState(param.getPlan_id(), PlanServiceImpl.PLAN_TYPE_SEED);
        }
        else{
            planService.updateLatestPlanData(param.getPlan_plate(),param.getPlan_step(), param.getPlan_id());
        }

        plateStatusChange(param.getPlan_plate() , param.getType());

        Users user = userService.getLoginUser();

        logUtil.addDataLog(user.getAccount(),param.getPlan_id(), DataLogUtil.ACTION_ANALYSIS,param.getPlan_code(), type);

        result = scheduleService.registNoteSchedule(param.getPlan_id(), param.getDivision());

        return result;
    }

    public void plateStatusChange(int plate_id , String type){
        plateMapper.updatePlateStatus(plate_id , PlateStatusType.valueOf(type).getValue());
    }
    
    public int modifySubStepData(ModifyBatchParam param) {
    	
    	Type listType = new TypeToken<ArrayList<Batch>>(){}.getType();
        List<Batch> batchList = gson.fromJson(param.getBatchJson(), listType);
    	
        if (batchList.isEmpty()){
            return -7;
        }

        batchList.forEach(batch -> {
        	batch.setStep_id(param.getStep_id());
        	
        	if(batch.getBatch_sub_id() == 0) {
        		batchMapper.insertSubdata(batch);
        	}else {
        		batchMapper.updateSubData(batch);
        	}
            
        });
        
    	return 1;
    }

    public String modifySampleByApi(int batch_id, int step_id, String value){
        int result = -1;
        Step step = stepService.selectStepById(step_id);
        if(step == null){
            return "f";
        }
        Batch batch =  batchMapper.selectBatchById(batch_id);
        if(batch == null){
            return "f";
        }
        Map<String,Object> batchMap = new HashMap<>();
        if(batch.getBatch_data() != null){
            batchMap = gson.fromJson(batch.getBatch_data(), batchMap.getClass());
        }

        if(batchMap.get(step.getStep_name()) != null){
            return "f";
        }

        batchMap.put(step.getStep_name(), value);
        PlanBatchResult planBatchResult = planService.selectPlanByBatch(batch_id);
        if(planBatchResult != null) {
            scheduleService.registNoteSchedule(planBatchResult.getPlan_id(), planBatchResult.getDivision());

            Users user = userService.getLoginUser();
            System.out.println(user);

            logUtil.addDataLog(user.getAccount(),planBatchResult.getPlan_id(), DataLogUtil.ACTION_ANALYSIS,planBatchResult.getPlan_code(), type);
        }
        
        String batch_data =  gson.toJson(batchMap);
        batch.setBatch_data(batch_data);
        result = batchMapper.updateBatchData(batch);

        Users user = userService.getLoginUser();

        return "t";
    }

    public boolean checkSampleValue(int batch_id, int step_id){

        Batch batch =  batchMapper.selectBatchById(batch_id);

        Map<String,Object> batchMap = new HashMap<>();
        if(batch.getBatch_data() != null){
            batchMap = gson.fromJson(batch.getBatch_data(), batchMap.getClass());
        }

        System.out.println(batchMap.get(Integer.toString(step_id)));
        if(batchMap.get(Integer.toString(step_id)) == null || batchMap.get(Integer.toString(step_id)).toString().isEmpty()){
            System.out.println("true");
            return true;
        }
        System.out.println("fa");
        return false;
    }

    @Override
    public List<Map<String, Object>> selectBatchResult(int plan_id) {
        List<Map<String, Object>> batchList = new ArrayList<>();
        List<Batch> list = batchMapper.selectBatchResult(plan_id);
        list.forEach(batch ->{
            Map<String,Object> batchMap = parseBatchToMapResult(batch);
            batchList.add(batchMap);
        });
        return batchList;
    }

    @Override
    public int modifySampleResult(ModifyBatchParam param) {
        int result = 0;
        AtomicInteger atomicInt = new AtomicInteger(result);

        Type listType = new TypeToken<ArrayList<Batch>>(){}.getType();
        List<Batch> batchList = gson.fromJson(param.getBatchJson(), listType);
        System.out.println(param.getBatchJson());
        System.out.println(batchList);
        batchList.forEach(batch -> {
            int count = batchMapper.updateBatchResult(batch);
            atomicInt.set(atomicInt.get() + count);
        });

        planService.modifyPlanState(param.getPlan_id(), PlanServiceImpl.PLAN_TYPE_RESULT);

        Users user = userService.getLoginUser();
        logUtil.addDataLog(user.getAccount(),param.getPlan_id(), DataLogUtil.ACTION_RESULT,param.getPlan_code(), type);
        return atomicInt.get();
    }
}
