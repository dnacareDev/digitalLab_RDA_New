package com.digitalLab.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalLab.Aria.Cipher;
import com.digitalLab.Entity.AnalysisShedule;
import com.digitalLab.Entity.DataLog;
import com.digitalLab.Entity.Equipment;
import com.digitalLab.Entity.Method;
import com.digitalLab.Entity.PlanApiParam;
import com.digitalLab.Entity.Reagent;
import com.digitalLab.Entity.Report;
import com.digitalLab.Entity.Users;
import com.digitalLab.Mapper.DataLogMapper;
import com.digitalLab.Mapper.RestApiMapper;
import com.digitalLab.Mapper.UserMapper;
import com.digitalLab.Service.BatchService;
import com.digitalLab.Service.LoginService;
import com.digitalLab.Service.PlanService;
import com.digitalLab.Service.RestApiService;

@Service
public class RestApiServiceImpl implements RestApiService {
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RestApiMapper mapper;
	
	@Autowired
	private PlanService planService;
	
	@Autowired
	private BatchService batchService;
	
	@Autowired
	private DataLogMapper logMapper;

	// 시약 목록 조회
	@Override
	public Map<String, Object> SelectReagentList(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Users user = loginService.login(id);

		if (user != null) {

			int count = mapper.SelectReagentCount();
			List<Reagent> list = mapper.SelectReagentList(user);

			result.put("count", count);
			result.put("list", list);
			result.put("result", 1);
		} else {
			result.put("result", 0);
		}

		return result;
	}

	// 시약 상세 조회
	@Override
	public Map<String, Object> SelectReagentDetail(String id, String reagent_id) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Users user = loginService.login(id);

		if (user != null) {

			Reagent check = mapper.SelectReagentDetail(reagent_id);

			if (check != null) {
				result.put("url", "redirect:/reagent/reagent-detail/" + reagent_id);
				result.put("result", 1);
			} else {
				result.put("result", 0);
			}

		} else {
			result.put("result", 0);
		}

		return result;
	}

	// 장비 목록 조회
	@Override
	public Map<String, Object> SelectEquipmentList(String id) {
		Map<String, Object> result = new HashMap<String, Object>();

		Users user = loginService.login(id);

		if (user != null) {
			int count = mapper.SelectEquipmentCount();
			List<Equipment> list = mapper.SelectEquipmentList(user);

			result.put("count", count);
			result.put("list", list);
			result.put("result", 1);
		} else {
			result.put("result", 0);
		}

		return result;
	}

	@Override
	public Map<String, Object> SelectEquipmentDetail(String id, String equipment_id) {
		Map<String, Object> result = new HashMap<String, Object>();

		Users user = loginService.login(id);

		if (user != null) {

			Equipment check = mapper.SelectEquipmentDetail(equipment_id);

			if (check != null) {
				result.put("url", "redirect:/equipment/equipment-detail/" + equipment_id);
				result.put("result", 1);
			} else {
				result.put("result", 0);
			}

		} else {
			result.put("result", 0);
		}

		return result;
	}

	@Override
	public Map<String, Object> SelectMethodList(String word) {
		Map<String, Object> result = new HashMap<String, Object>();

		int count = mapper.SelectMethodCount();
		List<Method> list = mapper.SelectMethodList(word);
		
		if (list != null) {
			result.put("count", count);
			result.put("list", list);
			result.put("result", 1);
		} else {
			result.put("result", 0);
		}

		return result;
	}

	@Override
	public Map<String, Object> SelectMethodDetail(String id, String method_id) {

		Map<String, Object> result = new HashMap<String, Object>();
		
		Users user = loginService.login(id);

		if (user != null) {

			Method check = mapper.SelectMethodDetail(method_id);

			if (check != null) {
				result.put("url", "redirect:/method/method-detail/" + method_id);
				result.put("result", 1);
			} else {
				result.put("result", 0);
			}
		} else {
			result.put("result", 0);
		}

		return result;
	}

	//////////////// 추가 서비스 로직 //////////////
	@Override
	public int totalCount(String type) {

		if (type.equals("reagent")) {
			return mapper.SelectReagentCount();
		} else if (type.equals("equipment")) {
			return mapper.SelectEquipmentCount();
		} else if (type.equals("method")) {
			return mapper.SelectMethodCount();
		}

		return 0;
	}

	@Override
	public Map<String, Object> selectPlanByApi(String id) {
		
		Map<String , Object> result = new HashMap<String , Object> ();
		
		int count = mapper.SelectMethodCount();
		List<PlanApiParam> list = planService.selectPlanByApi(id);
		
		if (list != null) {
			result.put("count", count);
			result.put("list", list);
			result.put("result", 1);
		} else {
			result.put("result", 0);
		}
		
		return result;
	}

	@Override
	public Report SelectReport(int plan_id)
	{
		return mapper.SelectReport(plan_id);
	}

	@Override
	public String SelectReportCode(int plan_id)
	{	
		Report report = mapper.SelectReportCode(plan_id);
		
		return report.getPrj_dtl_no();
	}

	@Override
	public String modifySampleByApi(int sample_id, int step_id, String value) {
		
		String result = batchService.modifySampleByApi(sample_id, step_id , value);		
		
		return result;
	}

	@Override
	public Map<String, Object> selectAnalysisSchedule(String id) {
		
		Map<String, Object> result = new HashMap<>();
		
		id = Cipher.ariaDecoding(id);
		
		Users user = userMapper.getUser(id);
		
		List<AnalysisShedule> schduleList = new ArrayList<>();
		
		if(user != null) {
			schduleList = mapper.analysisScheduleList(user);
		}
        
        if(!schduleList.isEmpty()) {
        	result.put("list", schduleList);
        	result.put("state", 1);
        }else {
        	result.put("state", 0);
        }
        
        return result;
	}

	@Override
	public DataLog selectDataLog(int log_id) {
		return logMapper.selectDataLog(log_id);
	}
}