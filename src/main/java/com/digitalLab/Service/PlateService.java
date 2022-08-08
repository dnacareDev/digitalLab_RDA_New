package com.digitalLab.Service;

import com.digitalLab.Entity.Plate;
import com.digitalLab.Entity.PlateApiParam;

import java.util.List;

public interface PlateService {
    public int registPlate(Plate plate);

    public int deletePlan(int plan_id);

    public List<Plate> selectPlateByPlan(int plan_id);

    public List<PlateApiParam> selectPlateByPlanApi(int plan_id);

    public Plate findPlateById(int plate_id);
}
