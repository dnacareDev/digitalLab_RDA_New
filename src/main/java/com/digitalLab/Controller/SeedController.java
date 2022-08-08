package com.digitalLab.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalLab.Entity.Genetic;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.SeedEasyParam;
import com.digitalLab.Entity.UserReport;
import com.digitalLab.Entity.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalLab.Entity.Seed;
import com.digitalLab.Service.GeneticService;
import com.digitalLab.Service.PlaceService;
import com.digitalLab.Service.ReportService;
import com.digitalLab.Service.SeedService;
import com.digitalLab.Service.UserService;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.ExcelUpload;

@Controller
public class SeedController {
	
	@Autowired
	private SeedService seedService;
	
	@Autowired
	private PlaceService placeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private GeneticService geneticService;
	
	@Autowired
	private ExcelUpload excel;
	
    // 시료 리스트
    @GetMapping("seed/seed_list")
    public ModelAndView seed_list(ModelAndView mv)
    {	
        mv.setViewName("seed/seed_list");

        return mv;
    }

    @GetMapping("seed/search-seed")
    @ResponseBody
    public Map<String, Object> searchSeedList(@ModelAttribute SearchParam searchParam){
        Map<String, Object> param = new HashMap<>();
        List<Seed> seedList = seedService.selectSeedList(searchParam);
        param.put("list",seedList);

        return param;
    }
    
    @GetMapping("api/seed/seed_list")
    @ResponseBody
    public Map<String, Object> seed_list(@ModelAttribute SearchParam searchParam){
        Map<String, Object> param = new HashMap<>();
        List<UserReport> list = seedService.selectReportSeedList(searchParam);
        param.put("list",list);

        return param;
    }

    // 시료 등록 뷰
    @GetMapping("seed/seed_add")
    public ModelAndView seed_add(ModelAndView mv)
    {
        Users user = AuthUtil.sessionUser();

    	mv.addObject("reportList", reportService.reportList("seed"));
    	mv.addObject("geneticList", geneticService.selectGeneticDepth(new Genetic()));
    	mv.addObject("userList", userService.userList(user));
    	mv.addObject("eachList", seedService.seedEachList());
    	mv.addObject("placeList", placeService.place_list());
        mv.setViewName("seed/seed_add");

        return mv;
    }

    @GetMapping("seed/seed-detail/{user_report_id}")
    public ModelAndView seedDetail(@PathVariable int user_report_id, ModelAndView mv){
    	
    	Users user = AuthUtil.sessionUser();
    	
        Map<String,Object> result = seedService.selectSeedByReportId(user_report_id);
        
    	mv.addObject("geneticList", geneticService.selectGeneticDepth(new Genetic()));
    	mv.addObject("userList", userService.userList(user));
        mv.addObject("eachList", seedService.seedEachList());
    	mv.addObject("placeList", placeService.place_list());
        mv.addObject("result",result);
        mv.setViewName(result.get("viewName").toString());
        //어느 위치로 가는지 확인
        System.out.println(result.get("viewName").toString());
        return mv;
    }
    
    // 시료 등록
    @PostMapping("seed/seed_add")
    @ResponseBody
    public int seed_add(@RequestBody Seed seed ){
        System.out.println("seed = " + seed);

        int result = -1;
        result = seedService.registSeed(seed);
        return result;
    }

    //고급 수정
    @PostMapping("seed/seed_update")
    @ResponseBody
    public int seed_update(@RequestBody Seed seed ){

        int result = 1;

        for (Seed s : seed.getSeedList()) {
            System.out.println("s = " + s);
        }


        result = seedService.modifyManySeed(seed);
        return result;
    }

    //간편 수정
    @PostMapping("seed/seed_easy_update")
    @ResponseBody
    public int seed_easy_update(@ModelAttribute SeedEasyParam seed){

        int result = 1;

        System.out.println("seed = " + seed);

        result = seedService.modifyEasySeed(seed);
        return result;
    }

    //템플릿 수정
    @PostMapping("seed/seed_template_update")
    @ResponseBody
    public int seed_template_update(@ModelAttribute Seed seed){

        int result = 1;

        System.out.println("seed = " + seed);

        result = seedService.modifyTemplateSeed(seed);
        return result;
    }



    // 시료 등록 간편 뷰
    @GetMapping("seed/seed_easy_add")
    public ModelAndView seed_add_easy(ModelAndView mv)
    {	
    	mv.addObject("geneticList", geneticService.selectGeneticDepth(new Genetic()));
    	mv.addObject("reportList", reportService.reportList("seed"));
        mv.setViewName("seed/seed_add_easy");

        return mv;
    }
    	
    // 시료 등록 간편
    @PostMapping("seed/seed_easy_add")
    @ResponseBody
    public int registSeedEasy(@ModelAttribute SeedEasyParam seed){
        int result = -1;
        System.out.println("seed = " + seed);
        result = seedService.registSeedEasy(seed);
        return result;
    }

    // 시료 등록 대량 뷰
    @GetMapping("seed/seed_many_add")
    public ModelAndView seed_add_many(ModelAndView mv)
    {	
    	Users user = AuthUtil.sessionUser();
    	
    	mv.addObject("reportList", reportService.reportList("seed"));
    	mv.addObject("userList", userService.userList(user));
    	mv.addObject("eachList", seedService.seedEachList());
    	mv.addObject("placeList", placeService.place_list());
        mv.setViewName("seed/seed_add_many");

        return mv;
    }
    
    // 시료 등록 대량
    @PostMapping("seed/seed_many_add")
    @ResponseBody
    public int seed_add_many(@RequestBody Seed seed){
    	
    	System.out.println("what is in seed : " +  seed);
    	
        int result = -1;
        result = seedService.registSeedMany(seed);
        return result;
    }
    
    // 시료 등록 템플릿 뷰
    @GetMapping("seed/seed_template_add")
    public ModelAndView seed_template_add(ModelAndView mv)
    {	
    	//작목 조회가 필요해서 추가
    	mv.addObject("geneticList", geneticService.selectGeneticDepth(new Genetic()));
    	
    	mv.addObject("reportList", reportService.reportList("seed"));
        mv.setViewName("seed/seed_add_template");

        return mv;
    }
    
    // 시료 등록 템플릿
    @PostMapping("seed/seed_template_add")
 	@ResponseBody
    public int seed_template_add(Seed seed)
    {
        System.out.println("seed = " + seed);
    	int result = -1;
        result = seedService.registSeedTemplate(seed);
        return result;
    }
    
    // 장비 템플릿 엑셀 리스트
 	@PostMapping("api/seed_excel_add")
 	@ResponseBody
 	public Map<String ,Object> equipment_excel_add(MultipartFile excelFile){
 		return excel.excel_list(excelFile); 
 	 }

    // 시드 삭제
    @PostMapping("seed/delete/{seed_id}")
    @ResponseBody
    public int seed_delete(@PathVariable int seed_id) throws IOException {
        return seedService.seedDelete(seed_id);
    }

    //시드 전체 삭제
    @PostMapping("seed/deleteAll/{user_report_id}")
    @ResponseBody
    public int seed_deleteAll(@PathVariable int user_report_id) throws IOException {

        return seedService.seedDeleteAll(user_report_id);
    }
}
