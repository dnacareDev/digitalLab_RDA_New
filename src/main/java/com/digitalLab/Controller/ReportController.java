package com.digitalLab.Controller;

import java.rmi.RemoteException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.digitalLab.Entity.Report;
import com.digitalLab.Entity.Users;
import com.digitalLab.Service.ReportService;
import com.digitalLab.Service.UserService;

@Controller
public class ReportController
{		
	@Autowired
	private ReportService reportService;
	
	// 과제 관리
	@GetMapping("report/report_list")
	public ModelAndView assignment_list(ModelAndView mv)
	{	
		mv.setViewName("assignment/assignment_list");
		return mv;
	}

	// 과제 등록 뷰
	@GetMapping("report/report_add")
	public ModelAndView assignment_modify(ModelAndView mv)
	{
		mv.setViewName("assignment/assignment_add");
		
		return mv;
	}
	
	// 과제 리스트 
	@GetMapping("api/report/{report_id}")
	@ResponseBody
	public Report report_detail(@PathVariable int report_id)
	{
		return reportService.selectReportById(report_id);
	}
	
	// 과제 리스트
	@GetMapping("api/report/search_report")
	@ResponseBody
	public Map<String , Object> report_list() throws RemoteException
	{	
		return reportService.reportList("seed");
	}

	//분석 계획이 등록된 과제 리스트
	@GetMapping("api/report-result")
	@ResponseBody
	public Map<String , Object> selectReportListByResult()
	{
		return reportService.selectRerpotListByPlan();
	}
	
}