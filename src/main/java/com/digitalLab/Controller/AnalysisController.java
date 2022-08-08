package com.digitalLab.Controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalLab.Entity.DivisionSearchParam;
import com.digitalLab.Entity.Genetic;
import com.digitalLab.Entity.Plan;
import com.digitalLab.Entity.PlanEasyParam;
import com.digitalLab.Entity.PlanSearchParam;
import com.digitalLab.Entity.PlanSetEasyParam;
import com.digitalLab.Entity.PlanSetParam;
import com.digitalLab.Entity.Schedule;
import com.digitalLab.Entity.Users;
import com.digitalLab.Service.DivisionService;
import com.digitalLab.Service.GeneticService;
import com.digitalLab.Service.PlanService;
import com.digitalLab.Service.ReportService;
import com.digitalLab.Service.ScheduleService;
import com.digitalLab.Service.UserService;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.SessionUtil;

@Controller
public class AnalysisController {

	@Autowired
	private PlanService planService;

	@Autowired
	private DivisionService divisionService;

	@Autowired
	private GeneticService geneticService;

	@Autowired
	private ReportService resportService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ScheduleService scheduleService;

	private static final String type = "analysis";

	// 분석 계획 등록 리스트
	@GetMapping("regist_analysis_list")
	public ModelAndView regist_analysis_list(ModelAndView mv) {

		Users user = AuthUtil.sessionUser();
		
		mv.addObject("user", user);
		mv.setViewName("regist_analysis/regist_analysis_list");
		return mv;
	}

	// 분석 계획 등록 신규 뷰
	@GetMapping("regist_analysis_add")
	public ModelAndView regist_analysis_add(ModelAndView mv) {

		mv.setViewName("regist_analysis/regist_analysis_add");
		return mv;
	}

	@PostMapping("regist_analysis_add")
	@ResponseBody
	public Map<String, Object> registAnalysis(@ModelAttribute Plan plan) {
		System.out.println(plan);
		Map<String, Object> result = null;
		result = planService.registPlan(plan);

		return result;
	}

	@PostMapping("regist_analysis_add/modify")
	@ResponseBody
	public int modifyPlan(@ModelAttribute Plan plan) {
		int result = -1;
		result = planService.modifyPlan(plan);
		return result;
	}

	@GetMapping("search-analysis")
	@ResponseBody
	public List<Plan> selectPlanList(@ModelAttribute PlanSearchParam param) {
		List<Plan> list = planService.selectPlanList(param);
		
		Users user = AuthUtil.sessionUser();
		
		System.out.println(user.getAccount());
		System.out.println(user.getAuthority());
		System.out.println(param.getPlan_status());
		
		//연구자 직책일 경우 if문 발동. MANAGER, ADMIN, RESPONSIBLE은 list를 모두 출력(RESPONSIBLE은 쿼리문에서 자신에게 속한 과로 한정된 list를 받음)
		if(user.getAuthority().equals("RESEARCHER") ) {
			// 시료분석, 결과등록은 담당자
			if(param.getPlan_status() >= 2) {
				
				for(int i=0 ; i<list.size() ; i++) {
					System.out.println();
					System.out.println("account : " + list.get(i).getAccount());
					System.out.println("plan_id : " + list.get(i).getPlan_id());
					
					// 로그인 계정과 일치하면 바로 다음으로 넘어간다
					if(list.get(i).getAccount().equals(user.getAccount())) {
						System.out.println("account matched");
						continue;
					}
					
					// 담당자(정), 담당자(부)가 아예 없으면 list에서 제거
					List<Schedule> schedule = scheduleService.selectScheduleByPlan(list.get(i).getPlan_id());
					if(schedule.size() == 0) {
						System.out.println("none - removed");
						list.remove(i);
						i--;
						continue;
					}
					
					// 담당자(정), 담당자(부) 계정명 수집
					ArrayList<String> managerList = new ArrayList<>();
					for(int j=0 ; j<schedule.get(0).getManagerList().size(); j++) {
						
						managerList.add(schedule.get(0).getManagerList().get(j).getAccount());
						System.out.println("managerList : " + managerList.get(j));
					}
					// 담당자(정), 담당자(부)와 계정명이 일치하지 않으면 list에서 제거
					if( !managerList.contains(user.getAccount()) ) {
						System.out.println("manager not matched - removed");
						list.remove(i);
						i--;
					}
				}
				
			} else {
				for(int i=0 ; i<list.size() ; i++) {
					System.out.println(list.get(i).getAccount());
					
					// 로그인 계정과 일치하지 않으면 배열 제거
					if( !list.get(i).getAccount().equals(user.getAccount()) ) {
						System.out.println("account not matched");
						list.remove(i);
						i--;
					}
				}	
			}
		}	
		
		return list;
	}

	// 분석 계획 등록 간편 뷰
	@GetMapping("regist_analysis_easy_add")
	public ModelAndView regist_analysis_easy_add(ModelAndView mv) {

		DivisionSearchParam param = new DivisionSearchParam();
		Map<String, Object> result = divisionService.selectDivisionListByParents(param);
		
		mv.addObject("geneticList", geneticService.selectGeneticDepth(new Genetic()));
		mv.addObject("reportList", resportService.reportList("seed"));
		mv.addObject("result", result);
		mv.setViewName("regist_analysis/regist_analysis_add_easy");
		
		return mv;
	}

	@PostMapping("regist_analysis_easy_add")
	@ResponseBody
	public int registAnalysisEasy(@ModelAttribute PlanEasyParam plan) {
		int result = -1;
		result = planService.registPlanEasy(plan);
		return result;
	}

	@PostMapping("regist_analysis/modify-easy")
	@ResponseBody
	public int modifyAnalysisEasy(@ModelAttribute PlanEasyParam plan) {
		int result = -1;
		
		result = planService.modifyPlanEasy(plan);
		return result;
	}

	// 분석 계획 등록 템플릿 뷰
	@GetMapping("regist_analysis_template_add")
	public ModelAndView regist_analysis_template_add(ModelAndView mv) {

		mv.addObject("result", divisionService.selectDivisionListByParents(new DivisionSearchParam()));
		mv.addObject("geneticList", geneticService.selectGeneticDepth(new Genetic()));
		mv.addObject("reportList", resportService.reportList("seed"));
		mv.setViewName("regist_analysis/regist_analysis_add_template");
		
		return mv;
	}

	// 분석 계획 등록 템플릿
	@PostMapping("regist_analysis_template_add")
	@ResponseBody
	public int regist_analysis_template_add(Plan plan) {
		int result = -1;
		result = planService.registPlanTemplate(plan);
		return result;
	}

	// 분석 계획 등록 템플릿 수정
	@PostMapping("regist_analysis_template_update")
	@ResponseBody
	public int regist_analysis_template_update(Plan plan) {
		System.out.println("plan = " + plan);

		int result = -1;
		result = planService.updatePlanTemplate(plan);
		return result;
	}

	// 분석 계획 설정
	@GetMapping("analysis_plan_list")
	public ModelAndView analysis_plan_list(ModelAndView mv) {

		Users user = AuthUtil.sessionUser();
		
		mv.addObject("user", user);
		mv.setViewName("analysis_plan/analysis_plan_list");
		return mv;
	}

	// 분석 계획 설정 간편 뷰
	@GetMapping("analysis_plan_easy_add")
	public ModelAndView analysis_plan_easy_add(ModelAndView mv) {

		mv.setViewName("analysis_plan/analysis_plan_add_easy");
		return mv;
	}

	// 분석 계획 설정 템플릿 뷰
	@GetMapping("analysis_plan_template_add")
	public ModelAndView analysis_plan_template_add(ModelAndView mv) {

		mv.setViewName("analysis_plan/analysis_plan_add_template");
		return mv;
	}

	// 분석 계획 설정 상세정보
	@GetMapping("analysis_plan_modify")
	public ModelAndView analysis_plan_modify(ModelAndView mv) {

		mv.setViewName("analysis_plan/analysis_plan_modify");
		return mv;
	}

	// 결과 등록 리스트
	@GetMapping("regist_results_list")
	public ModelAndView regist_results_list(ModelAndView mv) {

		Users user = AuthUtil.sessionUser();
		
		mv.addObject("user", user);
		mv.setViewName("regist_results/regist_results_list");
		return mv;
	}

	@GetMapping("result/result-detail")
	public ModelAndView regist_result_detail(ModelAndView mv) {
		mv.setViewName("regist_results/regist_results_add");
		return mv;
	}

	// 데이터 검증 상세보기
	@GetMapping("example_modify")
	public ModelAndView example_modify(ModelAndView mv) {

		mv.setViewName("example/example_modify");
		return mv;
	}

	// 분석 계획 설정 detail
	@GetMapping("analysis_plan_list/plan-detail")
	public ModelAndView planDetail(@RequestParam int plan_id, @RequestParam int regist_type, ModelAndView mv) {

		Map<String, Object> result = planService.selectPlanById(plan_id, regist_type);
		mv.addObject("result", result);
		mv.addObject("reportList" , resportService.reportList("seed"));
		mv.addObject("userList", userService.userList(AuthUtil.sessionUser()));
		mv.setViewName(result.get("viewName").toString());
		return mv;
	}

	// 분석 계획 등록 detail
	@GetMapping("regist_analysis_list/plan-regist-detail")
	public ModelAndView registPlanDetail(@RequestParam int plan_id, @RequestParam int regist_type, ModelAndView mv) {

		SessionUtil.setPkId(plan_id,type);
		
		Map<String, Object> result = new HashMap<String , Object>();
				
		if(regist_type == 2) {
			result = planService.selectTemplate(plan_id);
		}else {
			result = planService.selectRegistPlanById(plan_id, regist_type);
		}
		
		//System.out.println("viewName : " +  result.get("viewName").toString());
		
		mv.addObject("result", result);
		mv.setViewName(result.get("viewName").toString());
		return mv;
	}

	// 분석 계획 설정
	@PostMapping("analysis/plan-set")
	@ResponseBody
	public int setAnaliaysPlan(@ModelAttribute PlanSetParam plan) {
		int result = -1;
		result = planService.setAnalistPlan(plan);
		return result;
	}

	@PostMapping("analysis/plan-set-easy")
	@ResponseBody
	public int setAnaliaysPlanEasy(@ModelAttribute PlanSetEasyParam plan) {
		int result = -1;
		result = planService.setAnalysisPlanEasy(plan);
		return result;
	}

	@GetMapping("analysis/search/plan-method")
	@ResponseBody
	public Map<String, Object> selectPlanListByMethod(@RequestParam int method_id) {
		Map<String, Object> result = planService.selectPlanByMethod(method_id);
		return result;
	}

	@PostMapping("analysis/modify-plan-template")
	@ResponseBody
	public int modifyAnalysisPlanTemplate(PlanSetEasyParam plan){
		int result;

		result = planService.modifyAnalysisPlanTemplate(plan);

		return result;
	}
	
	// ReportController의 @GetMapping("api/report/search_report")을 그대로 가져옴
	@PostMapping("/main_manager_name")
	@ResponseBody
	public String selectMainManager(@RequestParam String prj_dtl_no) throws RemoteException {

		System.out.println(prj_dtl_no);
		
		return resportService.selectMainManager("seed", prj_dtl_no);
	}
}