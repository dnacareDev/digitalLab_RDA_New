package com.digitalLab.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalLab.Entity.Plan;
import com.digitalLab.Entity.PlanFile;
import com.digitalLab.Entity.Users;
import com.digitalLab.Service.SampleService;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleAnalysisController {
	@Autowired
	SampleService sampleService;

	private static final String type = "analysis";
	
	//@Autowired
	//private SampleAnalysisService sampleAnalysisService;
	
	public SampleAnalysisController() {
		
	}
	
	// 시료 분석 리스트
	@GetMapping("analysis/sample_analysis_list")
	public ModelAndView sample_analysis_list(ModelAndView mv) {

		Users user = AuthUtil.sessionUser();
		
		mv.addObject("user", user);
		mv.setViewName("sample_analysis/sample_analysis_list");
		return mv;
	}
	
	// 시료 분석 리스트
	@GetMapping("analysis/search_sample_analysis")
	@ResponseBody
	public List<Object> search_sample_analysis() {
		
		return new ArrayList<Object>();
	}

	// 시료 분석 간편 뷰
	@GetMapping("analysis/sample_analysis_easy_add")
	public ModelAndView sample_analysis_easy_add(ModelAndView mv) {

		mv.setViewName("sample_analysis/sample_analysis_add_easy");
		return mv;
	}

	// 시료 분석 대량 뷰
	@GetMapping("analysis/sample_analysis_many_add")
	public ModelAndView sample_analysis_many_add(ModelAndView mv) {

		mv.setViewName("sample_analysis/sample_analysis_add_many");
		return mv;
	}

	// 시료 분석 템플릿 뷰
	@GetMapping("analysis/sample_analysis_template_add")
	public ModelAndView sample_analysis_template_add(ModelAndView mv) {

		mv.setViewName("sample_analysis/sample_analysis_add_template");
		return mv;
	}
	
	// 시료 분석 상세보기
	@GetMapping("analysis/sample_analysis_detail")
	public ModelAndView sample_analysis_detail(ModelAndView mv , @RequestParam int plan_id,
											   @RequestParam(defaultValue = "0") int regist_type,
											   @RequestParam(defaultValue = "") String id) {
		SessionUtil.setPkId(plan_id,type);
		Map<String,Object> result = sampleService.sampleDetail(plan_id,regist_type);
		mv.addObject("result",result);
		mv.setViewName(result.get("viewName").toString());
		return mv;
	}

	@GetMapping("analyasis/sample-schedule")
	@ResponseBody
	public Map<String, Object> selectPlanBySchedule(@RequestParam int sch_id){
		Map<String, Object> result = sampleService.selectPlanBySchedule(sch_id);
		return result;
	}

	@GetMapping("analysis/batch")
	@ResponseBody
	public Map<String,Object> getBatchData(){
		Map<String,Object> result = new HashMap<>();

		return result;
	}

	@PostMapping("analysis/modify-easy")
	@ResponseBody
	public int modifyPlanEasy(@ModelAttribute PlanFile plan){
		int result = -1;
		result = sampleService.moidfyPlanEasy(plan);
		return result;
	}

	@PostMapping("analysis/modify-template")
	@ResponseBody
	public int modifyPlanTemplate(@ModelAttribute Plan plan){

		int result = -1;
		result = sampleService.moidfyPlanTemplate(plan);
		return result;
	}
}
