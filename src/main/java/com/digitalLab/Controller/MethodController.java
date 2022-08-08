package com.digitalLab.Controller;

import com.digitalLab.Entity.*;
import com.digitalLab.Service.DivisionService;
import com.digitalLab.Service.MethodService;
import com.digitalLab.Service.PlaceService;
import com.digitalLab.Service.ReagentService;
import com.digitalLab.Util.ExcelUpload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MethodController {
	@Autowired
	private MethodService methodService;

	@Autowired
	private DivisionService divisionService;
	
	@Autowired
	private ReagentService reagentService;
	
	@Autowired
	private PlaceService placeService;

	@Autowired
	private ExcelUpload excel;

	// Method 관리
	@GetMapping("method/method_list")
	public ModelAndView method_list(ModelAndView mv) {

		mv.setViewName("method/method_list");

		return mv;
	}
	
	// Method 상세보기
	@GetMapping("method/method-detail/{method_id}")
	public ModelAndView selectEquipmentById(@PathVariable int method_id, ModelAndView mv) {
		Map<String, Object> result = methodService.selectMethodById(method_id);
		
		mv.addObject("eachList", reagentService.reagentEachList());
		mv.addObject("placeList", placeService.place_list());
		mv.addObject("result", result);
		mv.setViewName(result.get("viewName").toString());
		return mv;
	}

	@GetMapping("method/search-method")
	@ResponseBody
	public Map<String, Object> selectMethodList(@ModelAttribute SearchParam searchParam) {
		Map<String, Object> param = new HashMap<>();
		List<Method> methodList = methodService.selectMethodList(searchParam);
		param.put("list", methodList);
		return param;
	}

	// Method 등록 뷰
	@GetMapping("method/method_add")
	public ModelAndView method_add(ModelAndView mv) {

		DivisionSearchParam param = new DivisionSearchParam();
		Map<String, Object> result = divisionService.selectDivisionListByParents(param);
		mv.addObject("result", result);
		mv.addObject("eachList", reagentService.reagentEachList());
		mv.addObject("placeList", placeService.place_list());
		mv.setViewName("method/method_add");

		return mv;
	}

	// Method 등록
	@PostMapping("method/method_add")
	@ResponseBody
	public int registMethod(Method method, @RequestParam("step_data") String step_data)
			throws JsonMappingException, JsonProcessingException {
		
		method.setStep_data(step_data);
		System.out.println("method = " + method);
		return methodService.registMethod(method);
	}

	// Method 등록 간편 뷰
	@GetMapping("method/method_easy_add")
	public ModelAndView method_add_easy(ModelAndView mv) {

		DivisionSearchParam param = new DivisionSearchParam();
		Map<String, Object> result = divisionService.selectDivisionListByParents(param);
		mv.addObject("result", result);
		mv.setViewName("method/method_add_easy");

		return mv;
	}

	// Method 등록 간편
	@PostMapping("method/method_easy_add")
	@ResponseBody
	public int registMethodEasy(@ModelAttribute Method method) {
		int result = -1;
		result = methodService.registMethodEasy(method);
		return result;
	}

	// Method 등록 템플릿 뷰
	@GetMapping("method/method_template_add")
	public ModelAndView method_add_template(ModelAndView mv) {

		DivisionSearchParam param = new DivisionSearchParam();
		Map<String, Object> result = divisionService.selectDivisionListByParents(param);
		mv.addObject("result", result);
		mv.setViewName("method/method_add_template");

		return mv;
	}

	// Method 등록 템플릿
	@PostMapping("method/method_template_add")
	@ResponseBody
	public int method_template_add(Method method) {
		return methodService.registMethodTemplate(method);
	}
		
	// Method 수정
	@PostMapping("method/updateMethod")
	@ResponseBody
	public int updateMethod(Method method) {
		int result = -1;
		System.out.println("method = " + method);
		result = methodService.updateMethod(method);
		return result;
	}
	
	// Method 재사용
	@GetMapping("method/method_division/{division_id}")
	@ResponseBody
	public Map<String, Object> method_division(@PathVariable int division_id) {

		return methodService.selectMethodByDivision(division_id);
	}

	// Method 삭제
	@PostMapping("method/delete/{method_id}")
	@ResponseBody
	public int deleteMethod(@PathVariable int method_id) {

		return methodService.deleteMethod(method_id);
	}

	// Method 스탭 단위 리스트
	@GetMapping("api/method/each/{type}")
	@ResponseBody
	public List<Each> method_each_list(@PathVariable int type) {

		return methodService.selectMethodEach(type);
	}

	// Method 스탭 리스트
	@GetMapping("api/method/step/{method_id}")
	@ResponseBody
	public List<Step> method_step_list(@PathVariable int method_id) {

		return methodService.methodStepList(method_id);
	}

	// Method 엑셀 등록
	@PostMapping("api/method_excel_add")
	@ResponseBody
	public Map<String, Object> method_excel_add(MultipartFile excelFile) {
		return excel.excel_list(excelFile);
	}

}