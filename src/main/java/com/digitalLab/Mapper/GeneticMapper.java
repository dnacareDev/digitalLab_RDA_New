package com.digitalLab.Mapper;

import com.digitalLab.Entity.Genetic;
import com.digitalLab.Entity.GeneticChart;
import com.digitalLab.Entity.Users;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GeneticMapper {
    public List<Genetic> selectGeneticBySeed (List<Integer> seed_pk_list);

    public List<Genetic> selectGeneticIdByPlan (int plan_id);
    
    public List<Genetic> selectGeneticDepth (Genetic genetic);
    
    public int insertGenetic (Genetic genetic);
    
    public int getSeqId();

    List<GeneticChart> getGeneticChartData(@Param("user") Users user, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
