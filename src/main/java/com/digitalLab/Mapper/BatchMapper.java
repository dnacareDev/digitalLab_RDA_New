package com.digitalLab.Mapper;

import com.digitalLab.Entity.Batch;
import com.digitalLab.Entity.Plate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BatchMapper {
    public int registBatch(Batch batch);

    public int registBatchList(@Param("list") List<Batch> batchList, @Param("plan_id") int plan_id);

    public List<Batch> selectBatchByPlan(int plan_id);

    public int deleteBatchByPlan(int plan_id);

    public List<Batch> selectBatchByPlate(int plate_id);
    public List<Batch> selectBatchSubStep(@Param("plate_id") int plate_id , @Param("step_id") int step_id);
    
    public int updateBatchData(Batch batch);

    public Batch selectBatchById(int batch_id);

    public List<Batch> selectBatchResult(int plan_id);

    public int updateBatchResult(Batch batch);
    
    // 서브스텝 배치 등록
    public int insertSubdata(Batch batch);
    
    // 서브스텝 배치 업데이트
    public int updateSubData(Batch batch);
}
