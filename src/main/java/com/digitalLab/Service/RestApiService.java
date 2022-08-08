package com.digitalLab.Service;

import java.util.Map;

import com.digitalLab.Entity.DataLog;
import com.digitalLab.Entity.Report;

public interface RestApiService
{	
	public DataLog selectDataLog(int log_id);
	
	// 추가 API
	public Map<String, Object> SelectReagentList(String id);
	public Map<String, Object> SelectReagentDetail(String id , String reagent_id);
	
	public Map<String, Object> SelectEquipmentList(String id);
	public Map<String, Object> SelectEquipmentDetail(String id , String equipment_id);
	
	public Map<String, Object> SelectMethodList(String word);
	public Map<String, Object> SelectMethodDetail(String id , String method_id);
	
	public int totalCount(String type);
	
	public Map<String , Object> selectPlanByApi(String id);

	Report SelectReport(int plan_id);

	String SelectReportCode(int plan_id);
	
	String modifySampleByApi(int sample_id, int step_id ,String value);
	
	public Map<String , Object> selectAnalysisSchedule(String id);
	
}