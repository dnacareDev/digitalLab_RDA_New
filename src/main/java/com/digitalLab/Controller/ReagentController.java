package com.digitalLab.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalLab.Entity.Reagent;
import com.digitalLab.Entity.ReagentEasyParam;
import com.digitalLab.Service.PlaceService;
import com.digitalLab.Service.ReagentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalLab.Util.ExcelUpload;

@Controller
public class ReagentController {

	@Autowired
	private ReagentService reagentService;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private ExcelUpload excel;

	@GetMapping("reagent/reagent-detail/{reagent_id}")
	public ModelAndView reagentDetail(@PathVariable int reagent_id, ModelAndView mv) {
		Map<String, Object> result = reagentService.selectReagentById(reagent_id);

		mv.addObject("placeList", placeService.place_list());
		mv.addObject("eachList", reagentService.reagentEachList());

		mv.addObject("result", result);
		mv.setViewName(result.get("viewName").toString());
		return mv;
	}

	// 시약 목록
	@GetMapping("reagent/reagent_list")
	public ModelAndView reagent_list(ModelAndView mv) {
		mv.setViewName("reagent_information/reagent_list");

		return mv;
	}

	// 시약 등록 뷰
	@GetMapping("reagent/reagent_add")
	public ModelAndView reagent_add(ModelAndView mv) {

		mv.addObject("placeList", placeService.place_list());
		mv.addObject("eachList", reagentService.reagentEachList());
		mv.setViewName("reagent_information/reagent_add");

		return mv;
	}

	// 시약 등록
	@PostMapping("reagent/reagent_add")
	@ResponseBody
	public int reagent_add(@ModelAttribute Reagent reagent) {
		return reagentService.registReagent(reagent);
	}

	// 시약 등록 간편 뷰
	@GetMapping("reagent/reagent_easy_add")
	public ModelAndView reagent_add_easy(ModelAndView mv) {
		mv.setViewName("reagent_information/reagent_add_easy");

		return mv;
	}

	// 시약 등록 간편
	@PostMapping("reagent/reagent_easy_add")
	@ResponseBody
	public int registReagentEasy(@ModelAttribute ReagentEasyParam reagent) {
		int result = -1;
		result = reagentService.registReagentEasy(reagent);
		return result;
	}

	@GetMapping("reagent/search-reagent")
	@ResponseBody
	public Map<String, Object> selectReagentList() {
		Map<String, Object> param = new HashMap<>();
		List<Reagent> list = reagentService.selectReagentList();
		param.put("list", list);

		return param;
	}

	// 시약 등록 대량 뷰
	@GetMapping("reagent/reagent_many_add")
	public ModelAndView reagent_add_many(ModelAndView mv) {
		
		mv.addObject("placeList", placeService.place_list());
		mv.addObject("eachList", reagentService.reagentEachList());
		mv.setViewName("reagent_information/reagent_add_many");

		return mv;
	}

	// 시약 등록 대량
	@PostMapping("reagent/reagent_many_add")
	@ResponseBody
	public Map<String , Object> reagent_add_many(@RequestBody Reagent reagent) {
		return reagentService.registReagentMany(reagent);
	}

	// 시약 등록 템플릿 뷰
	@GetMapping("reagent/reagent_template_add")
	public ModelAndView reagent_add_template(ModelAndView mv) {
		mv.setViewName("reagent_information/reagent_add_template");

		return mv;
	}

	// 시약 등록 템플릿
	@PostMapping("reagent/reagent_template_add")
	@ResponseBody
	public int registReagentEasy(Reagent reagent){
		
		return reagentService.registReagentTemplate(reagent);
	}

	// 시약 수정
	@PostMapping("reagent/reagent_modify")
	@ResponseBody
	public int reagent_modify(Reagent reagent) {
		System.out.println("reagent = " + reagent);
		return reagentService.reagentModify(reagent);
	}

	// 시약 템플릿 엑셀 리스트
	@PostMapping("api/reagent_excel_add")
	@ResponseBody
	public Map<String, Object> reagent_excel_add(MultipartFile excelFile) {

		return excel.excel_list(excelFile);
	}

	@PostMapping("reagent/delete/{reagent_id}")
	@ResponseBody
	public int reagent_delete(@PathVariable int reagent_id){
		return reagentService.reagentDelete(reagent_id);
	}

}
