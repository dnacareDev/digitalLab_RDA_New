package com.digitalLab.ServiceImpl;

import com.digitalLab.Entity.Genetic;
import com.digitalLab.Entity.GeneticChart;
import com.digitalLab.Entity.Users;
import com.digitalLab.Mapper.GeneticMapper;
import com.digitalLab.Service.GeneticService;
import com.digitalLab.Util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneticServiceImpl implements GeneticService {
    @Autowired
    private GeneticMapper geneticMapper;
    @Override
    public List<Genetic> selectGeneticBySeed(List<Integer> seed_pk_list) {
        List<Genetic> list = geneticMapper.selectGeneticBySeed(seed_pk_list);

        return list;
    }

    public List<Genetic> selectGeneticIdByPlan(int plan_id){
        List<Genetic> geneticList = geneticMapper.selectGeneticIdByPlan(plan_id);

        return geneticList;
    }

	@Override
	public List<Genetic> selectGeneticDepth(Genetic genetic) {
		
		return geneticMapper.selectGeneticDepth(genetic);
	}

    @Override
    public List<GeneticChart> getGeneticChartData(String startDate, String endDate) {
        Users user = AuthUtil.sessionUser();
        
        return geneticMapper.getGeneticChartData(user ,startDate , endDate);
    }

}
