package com.digitalLab.Mapper;

import com.digitalLab.Entity.Plate;
import com.digitalLab.Entity.PlateApiParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlateMapper {
    public int registPlate(Plate plate);

    public int getSeqId();

    public int deletePlateByPlan(int plan_id);

    public List<Plate> selectPlateByPlan(int plan_id);

    public List<PlateApiParam> selectPlateByPlanApi(int plan_id);

    public Plate findPlateById(int plate_id);

    public int updatePlateStatus(@Param("plate_id") int plate_id, @Param("plate_status") int plate_status);
}
