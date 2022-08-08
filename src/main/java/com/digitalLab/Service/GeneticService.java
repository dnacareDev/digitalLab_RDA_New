package com.digitalLab.Service;

import com.digitalLab.Entity.Genetic;
import com.digitalLab.Entity.GeneticChart;

import java.util.List;

public interface GeneticService {
    public List<Genetic> selectGeneticBySeed(List<Integer> seed_pk_list);

    public List<Genetic> selectGeneticIdByPlan(int plan_id);
    
    public List<Genetic> selectGeneticDepth(Genetic genetic);

    List<GeneticChart> getGeneticChartData(String startDate, String endDate);
}
