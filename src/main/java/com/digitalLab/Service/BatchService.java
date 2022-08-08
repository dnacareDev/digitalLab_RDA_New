package com.digitalLab.Service;


import com.digitalLab.Entity.Batch;
import com.digitalLab.Entity.ModifyBatchParam;

import java.util.List;
import java.util.Map;

public interface BatchService {
    public int registBatch(Batch batch);

    public int registBatchList(List<Batch> batchList, int plan_id);

    public List<Batch> selectBatchByPlan(int plan_id);

    public List<Map<String,Object>> selectBatchByPlate(int plate_id);
    public List<Map<String,Object>> selectBatchSubStep(int plate_id , int step_id);

    public int modifySampleData(ModifyBatchParam param);

    public int modifySubStepData(ModifyBatchParam param);
    
    public String modifySampleByApi(int batch_id, int step_id, String value);

    public boolean checkSampleValue(int batch_id, int step_id);

    public List<Map<String,Object>> selectBatchResult(int plan_id);

    public int modifySampleResult(ModifyBatchParam param);

    public List<Map<String,Object>> selectSampleList(int plate_id);

    public int deleteBatchByPlan(int plan_id);
}
