package com.digitalLab.ServiceImpl;

import com.digitalLab.Entity.Plate;
import com.digitalLab.Entity.PlateApiParam;
import com.digitalLab.Mapper.PlateMapper;
import com.digitalLab.Service.PlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlateServiceImpl implements PlateService {
    @Autowired
    PlateMapper plateMapper;
    @Override
    public int registPlate(Plate plate) {
        int result = -1;

        int plate_id = plateMapper.getSeqId();

        plate.setPlate_id(plate_id);
        result = plateMapper.registPlate(plate);

        return result;
    }

    @Override
    public int deletePlan(int plan_id) {
        int result = -1;

        result = plateMapper.deletePlateByPlan(plan_id);

        return result;
    }

    @Override
    public List<Plate> selectPlateByPlan(int plan_id) {
        List<Plate> plateList = plateMapper.selectPlateByPlan(plan_id);
        return plateList;
    }

    @Override
    public List<PlateApiParam> selectPlateByPlanApi(int plan_id) {
        List<PlateApiParam> plateList = plateMapper.selectPlateByPlanApi(plan_id);
        return plateList;
    }

    @Override
    public Plate findPlateById(int plate_id) {
        return plateMapper.findPlateById(plate_id);
    }
}
