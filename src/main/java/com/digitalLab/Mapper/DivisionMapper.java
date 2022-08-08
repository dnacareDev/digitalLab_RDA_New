package com.digitalLab.Mapper;

import com.digitalLab.Entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DivisionMapper {
    public int registDivision(Division division);

    public int getSeqId();

    public List<Division> selectDivisionListByParents(DivisionSearchParam param);

    public CommonDivision selectDivisionByMethod(int method_id);

    public List<DivisionResult> selectDivisionByReport(int report_id);

    public CommonDivision selectDivisionByAnalysis(int plan_id);

    List<DivisionChart> getDivisionChartData(@Param("user") Users user, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
