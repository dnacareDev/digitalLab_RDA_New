package com.digitalLab.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.digitalLab.Entity.Instrument;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Service.InstrumentService;

@Controller
public class InstrumentController {

	@Autowired
	private InstrumentService instrumentService;

	public InstrumentController() {
		// TODO Auto-generated constructor stub
	}

	// 기구 리스트
	@GetMapping("instrument/instrument_list")
	public ModelAndView instrument_list(ModelAndView mv) {

		mv.setViewName("instrument_information/instrument_list");

		return mv;
	}

	@GetMapping("instrument/instrument-detail/{instrument_id}")
	public ModelAndView instrument_detail(@PathVariable int instrument_id, ModelAndView mv) {
		Map<String, Object> result = instrumentService.selectInstrumentbyId(instrument_id);

		mv.addObject("result", result);
		mv.setViewName("instrument_information/instrument_detail");

		return mv;
	}

	// 기구 리스트
	@GetMapping("api/instrument/instrument_list")
	@ResponseBody
	public List<Instrument> instrument_list(SearchParam searchParam) {

		return instrumentService.searchInstrumentList(searchParam);
	}

	// 기구 등록 뷰
	@GetMapping("instrument/instrument_add")
	public ModelAndView equipment_add(ModelAndView mv) {
		mv.setViewName("instrument_information/instrument_add");

		return mv;
	}

	// 기구 등록
	@PostMapping("instrument/instrument_add")
	@ResponseBody
	public int instrument_add(@RequestBody Instrument instrument) {

		return instrumentService.registInstrument(instrument);
	}

	// 기구 수정
	@PostMapping("instrument/instrument_modify")
	@ResponseBody
	public int instrument_modify(Instrument instrument) {
		
		System.out.println("controller : "+instrument);
		return instrumentService.modifyInstrument(instrument);
	}

	// 기구 등록 간편 뷰
	@GetMapping("instrument/instrument_easy_add")
	public ModelAndView instrument_add_easy(ModelAndView mv) {
		mv.setViewName("instrument_information/instrument_add_easy");

		return mv;
	}

	// 기구 등록 템플릿 뷰
	@GetMapping("/instrument/instrument_template_add")
	public ModelAndView instrument_add_template(ModelAndView mv) {
		mv.setViewName("instrument_information/instrument_add_template");

		return mv;
	}

	// 기구 삭제
	@PostMapping("instrument/delete/{instrument_id}")
	@ResponseBody
	public int instrument_delete(@PathVariable int instrument_id) throws IOException {

		return instrumentService.instrumentDelete(instrument_id);
	}


}
