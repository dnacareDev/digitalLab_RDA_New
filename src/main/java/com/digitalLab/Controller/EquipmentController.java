package com.digitalLab.Controller;

import com.digitalLab.Entity.Equipment;
import com.digitalLab.Entity.EquipmentEasyParam;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Service.EquipmentService;
import com.digitalLab.Util.ExcelUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EquipmentController {
	
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private ExcelUpload excel;

    // 장비 리스트
    @GetMapping("equipment/equipment_list")
    public ModelAndView equipment_list(ModelAndView mv)
    {
        mv.setViewName("equipment_information/equipment_list");

        return mv;
    }
    
    // 장비 상세보기
    @GetMapping("equipment/equipment-detail/{equipment_id}")
    public ModelAndView selectEquipmentById(@PathVariable int equipment_id, ModelAndView mv){
        Map<String, Object> result = equipmentService.selectEquipmentById(equipment_id);
        mv.addObject("result", result);
        mv.setViewName(result.get("viewName").toString());
        return mv;
    }

    // 장비 검색
    @GetMapping("equipment/search-equipment")
    @ResponseBody
    public Map<String, Object> searchEquipment(@ModelAttribute SearchParam searchParam){
        Map<String, Object> param = new HashMap<>();
        List<Equipment> list = equipmentService.searchEquipment(searchParam);

        param.put("list", list);
        param.put("paging", searchParam);

        return param;
    }

    // 장비 등록 뷰
    @GetMapping("equipment/equipment_add")
    public ModelAndView equipment_add(ModelAndView mv)
    {
        mv.setViewName("equipment_information/equipment_add");

        return mv;
    }
    
    // 장비 등록
    @PostMapping("equipment/equipment_add")
    @ResponseBody
    public int equipment_add(Equipment equipment)
    {		
    	System.out.println("232 : "+equipment);
        return equipmentService.registEquipment(equipment);
    }

    // 장비 등록 간편 뷰
    @GetMapping("equipment/equipment_easy_add")
    public ModelAndView equipment_add_easy(ModelAndView mv)
    {
        mv.setViewName("equipment_information/equipment_add_easy");

        return mv;
    }

    @PostMapping("equipment/equipment_easy_add")
    @ResponseBody
    public int registEquipmentEasy(@ModelAttribute EquipmentEasyParam equipment){
        System.out.println(equipment);
        int result = -1;
        result = equipmentService.registEquipmentEasy(equipment);

        return result;
    }

    // 장비 등록 대량 뷰
    @GetMapping("equipment/equipment_many_add")
    public ModelAndView equipment_add_many(ModelAndView mv)
    {
        mv.setViewName("equipment_information/equipment_add_many");

        return mv;
    }
    
    // 장비 등록 대량
    @PostMapping("equipment/equipment_many_add")
    @ResponseBody
    public Map<String , Object> equipment_add_many(@RequestBody Equipment equipment)
    {
    	return equipmentService.registEquipmentMany(equipment);
    }

    // 장비 등록 템플릿 뷰
    @GetMapping("equipment/equipment_template_add")
    public ModelAndView machine_add_template(ModelAndView mv)
    {
        mv.setViewName("equipment_information/equipment_add_template");

        return mv;
    }
    
    // 장비 등록 템플릿
    @PostMapping("equipment/equipment_template_add")
    @ResponseBody
    public int equipment_add_template(Equipment equipment)
    {
    	return equipmentService.registEquipmentTemplate(equipment);
    }
    
    // 시약 수정
 	@PostMapping("equipment/equipment_modify")
 	@ResponseBody
 	public int equipment_modify(Equipment equipment) {

        System.out.println("equipment = " + equipment);

 		return equipmentService.equipmentModify(equipment);
 	}
    
    // 장비 템플릿 엑셀 리스트
	@PostMapping("api/equipment_excel_add")
	@ResponseBody
	public Map<String ,Object> equipment_excel_add(MultipartFile excelFile)
	{
		return excel.excel_list(excelFile); 
	}

    // 장비 삭제
    @PostMapping("equipment/delete/{equipment_id}")
    @ResponseBody
    public int equipment_delete(@PathVariable int equipment_id) throws IOException {

        return equipmentService.equipmentDelete(equipment_id) ;
    }
}
