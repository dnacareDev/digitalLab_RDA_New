package com.digitalLab.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalLab.Entity.Place;
import com.digitalLab.Service.PlaceService;

@Controller
public class PlaceController {

	@Autowired
	private PlaceService placeService;

	public PlaceController() {
		// TODO Auto-generated constructor stub
	}

	// 장소 관리
	@GetMapping("place/place_list")
	public ModelAndView place_list(ModelAndView mv) {
		mv.setViewName("place/place_list");
		return mv;
	}

	// 장소 관리
	@GetMapping("place/search_place")
	@ResponseBody
	public Map<String, Object> place_list() {
		Map<String, Object> map = new HashMap<>();
		List<Place> list = placeService.place_list();
		map.put("list", list);

		return map;
	}

	// 장소 등록 뷰
	@GetMapping("place/place_add")
	public ModelAndView place_add(ModelAndView mv) {
		mv.setViewName("place/place_add");
		return mv;
	}

	// 장소 등록
	@PostMapping("place/place_add")
	@ResponseBody
	public int place_add(Place place) throws IOException {
		return placeService.insertPlace(place);
	}

	// 장소 상세보기
	@GetMapping("place/place_detail/{place_id}")
	public ModelAndView place_modify(ModelAndView mv, @PathVariable int place_id) {

		mv.addObject("result", placeService.placeDetail(place_id));
		mv.setViewName("place/place_modify");

		return mv;
	}

	// 장소 수정
	@PostMapping("place/place_modify")
	@ResponseBody
	public int place_modify(Place place) throws IOException {

		return placeService.modifyPlace(place);
	}

	// 장소 삭제
	@PostMapping("place/delete/{place_id}")
	@ResponseBody
	public int place_delete(@PathVariable int place_id) throws IOException {
			
		return placeService.placeDelete(place_id) ;
	}

	// 장소 상세보기 API
	@GetMapping("api/place/{place_id}")
	@ResponseBody
	public Map<String, Object> place(@PathVariable int place_id) {
		return placeService.placeDetail(place_id);
	}

}
