package com.digitalLab.Controller;

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

import com.digitalLab.Entity.Storage;
import com.digitalLab.Service.PlaceService;
import com.digitalLab.Service.StorageService;

@Controller
public class StorageController {

	@Autowired
	private StorageService storageService;

	@Autowired
	private PlaceService placeService;

	public StorageController() {

	}

	// 저장고 온/습도 리스트
	@GetMapping("storage/storage_list")
	public ModelAndView storage_list(ModelAndView mv) {
		mv.setViewName("storage/storage_list");
		return mv;
	}

	// 저장고 온/습도 리스트
	@GetMapping("storage/search-storage")
	@ResponseBody
	public Map<String, Object> selectStorageList() {
		Map<String, Object> param = new HashMap<>();
		List<Storage> list = storageService.selectStorageList();
		param.put("list", list);

		return param;
	}

	// 저장고 온/습도 디테일
	@GetMapping("storage/storage-detail/{storage_id}")
	public ModelAndView storage_modify(ModelAndView mv, @PathVariable int storage_id) {

		mv.addObject("placeList", placeService.place_list());
		mv.addObject("result", storageService.storageDetail(storage_id));
		mv.setViewName("storage/storage_modify");

		return mv;
	}

	// 저장고 온/습도 등록 뷰
	@GetMapping("storage/storage_add")
	public ModelAndView storage_add(ModelAndView mv) {

		mv.addObject("placeList", placeService.place_list());
		mv.setViewName("storage/storage_add");
		return mv;
	}

	// 저장고 온/습도 등록
	@PostMapping("storage/storage_add")
	@ResponseBody
	public int storage_add(Storage storage) {

		return storageService.insertStorage(storage);
	}

	// 저장고 온/습도 수정
	@PostMapping("storage/storage_modify")
	@ResponseBody
	public int storage_modify(Storage storage) {

		return storageService.modifyStorage(storage);
	}
	
	// 저장고 온/습도 수정
	@PostMapping("storage/delete/{storage_id}")
	@ResponseBody
	public int storage_modify(@PathVariable int storage_id) {
		
		return storageService.deleteStorage(storage_id);
	}
}
