package com.digitalLab.ServiceImpl;

import com.digitalLab.Entity.*;
import com.digitalLab.Mapper.DivisionMapper;
import com.digitalLab.Service.DivisionService;
import com.digitalLab.Util.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DivisionServiceImpl implements DivisionService {
    
	@Autowired
    private DivisionMapper divisionMapper;
	
    @Override
    public int registDivision(Division division) {
        int result = -1;
        int division_id = divisionMapper.getSeqId();
        division.setDivision_id(division_id);
        result = divisionMapper.registDivision(division);
        if(result == 0){
            return -1;
        }
        return division_id;
    }

    @Override
    public Map<String,Object> selectDivisionListByParents(DivisionSearchParam param){
    	
    	if(param.getDivision_depth() == 4) {
    		Users user = AuthUtil.sessionUser();
    		param.setAccount(user.getAccount());
    		param.setAuthority(user.getAuthority());
    		param.setUser_code("mi-"+user.getGroupCode());
    	}
    	
        List<Division> divisionList = divisionMapper.selectDivisionListByParents(param);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("state",0);
        map.put("list",divisionList);

        return map;
    }

    @Override
    public CommonDivision selectDivisionByMethod(int method_id) {
    	CommonDivision division = divisionMapper.selectDivisionByMethod(method_id);
        return division;
    }
    
    @Override
    public CommonDivision selectDivisionByAnalysis(int plan_id) {
    	CommonDivision division = divisionMapper.selectDivisionByAnalysis(plan_id);
    	return division;
    }

    @Override
    public Map<String,Object> selectDivisionByReport(int report_id) {
        Map<String,Object> result = new HashMap<>();
        List<DivisionResult> divisionList = divisionMapper.selectDivisionByReport(report_id);
        result.put("list",divisionList);
        result.put("state",1);
        return result;
    }

    @Override
    public List<DivisionChart> getDivisionChartData(String startDate, String endDate) {

        Users user = AuthUtil.sessionUser();

        return divisionMapper.getDivisionChartData(user , startDate , endDate);
    }

}
