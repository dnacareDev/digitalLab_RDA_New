package com.digitalLab.Service;

import com.digitalLab.Entity.*;

import java.util.List;
import java.util.Map;

public interface DivisionService {
    public int registDivision(Division division);

    public Map<String, Object> selectDivisionListByParents(DivisionSearchParam param);

    public CommonDivision selectDivisionByMethod(int method_id);
    
    public CommonDivision selectDivisionByAnalysis(int plan_id);

    public Map<String,Object> selectDivisionByReport(int report_id);

    List<DivisionChart> getDivisionChartData(String startDate, String endDate);
}
