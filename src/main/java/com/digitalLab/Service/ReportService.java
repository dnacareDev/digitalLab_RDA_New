package com.digitalLab.Service;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalLab.Entity.Report;
import com.digitalLab.Entity.Seed;
import com.digitalLab.Entity.Users;

public interface ReportService {
	
	public Map<String , Object> reportList(String type);
	
	public Report selectReportById(int report_id);
	
	public int addPrjDtl(String dic_code) throws RemoteException;
	
	public int addManager(HashMap<String,Object> map);

	public Map<String , Object> selectRerpotListByPlan();
	
	public int seedReportCheck(Seed seed , int USEE_AT_CODE);
	
	public List<Report> testReport();
	
	public String selectMainManager(String type, String prj_dtl_no);
}
