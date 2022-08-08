package com.digitalLab.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.digitalLab.Entity.DataLog;
import com.digitalLab.Entity.DivisionChart;
import com.digitalLab.Entity.GeneticChart;
import com.digitalLab.Service.*;
import com.digitalLab.Small.Mapper.RdaUserMapper;
import com.digitalLab.Util.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.digitalLab.Entity.UserConnect;
import com.digitalLab.Entity.Users;

@Controller
public class UserController {
	@Autowired
	private RdaUserMapper userMapper;
	
	@Autowired
	private UserService userService;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private DivisionService divisionService;

	@Autowired
	private GeneticService geneticService;
	
	// 홈
	@GetMapping("/")
	public ModelAndView Index(ModelAndView mv)
	{
		mv.setViewName("redirect:/home");
		
		return mv;
	}
	
	// 홈
	@GetMapping("home")
	public ModelAndView Home(ModelAndView mv , HttpServletRequest request , HttpSession httpSession)
	{	
		Users user = AuthUtil.sessionUser();

		mv.addObject("user" , userService.getUser(user.getAccount()));
		mv.addObject("placeList", placeService.place_list());
		mv.setViewName("home/home");	
		
		return mv;
	}
	
	// 사용자 관리
	@GetMapping("user/user_list")
	public ModelAndView user_list(ModelAndView mv)
	{
		mv.setViewName("user/user_list");
		
		return mv;
	}
	
	// 이력 관리
	@GetMapping("dataLogList")
	public ModelAndView dataLogList(ModelAndView mv) {
		
		mv.setViewName("log/log_list");
		
		return mv;
	}
	
	@GetMapping("api/user/search_user")
	@ResponseBody
	public Map<String , Object> search_user()
	{	
		Users user = AuthUtil.sessionUser();
		System.out.println("///////////////////");
		System.out.println("///////////////////");
		
		System.out.println("GROUPCODE : "+user.getGroupCode());
		
		System.out.println("///////////////////");
		System.out.println("///////////////////");
		
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("result" , userService.userList(user));
		
		return map;
	}

	// 사용자 등록 뷰
	@GetMapping("user/user_add")
	public ModelAndView user_add(ModelAndView mv)
	{
		mv.setViewName("user/user_add");
		
		return mv;
	}

	// 사용자 상세보기
	@GetMapping("user/user_detail/{account}")
	public ModelAndView user_modify(ModelAndView mv , @PathVariable String account)
	{	
		mv.addObject("user" , userService.getUser(account));
		mv.setViewName("user/user_modify");

		return mv;
	}


	/**
	 * API
	 */
	
	@ResponseBody
	@PostMapping("api/user/modify")
	public int authorityModify(@RequestBody Users user) {
		return userService.authorityModify(user);
	}
	
	@ResponseBody
	@PostMapping("api/userHomeCheck")
	public int userHomeCheck(@RequestParam String type , @RequestParam int checkValue) {
		return userService.userHomeCheck(type, checkValue);
	}

	@ResponseBody
	@PostMapping("api/storage_json_data")
	public Map<String ,Object> storage_excel_data(@RequestParam("place_id") int place_id) {
		Map<String, Object> jsonData = storageService.getPlaceJsonDataByPlaceId(place_id);
		return jsonData;
	}

	@PostMapping("api/userLogin_list")
	@ResponseBody
	public List<UserConnect> userLoginList(@RequestParam("startDate") String startDate, @RequestParam(name = "endDate") String endDate){
		return userService.userLoginList(startDate, endDate);
	}
	
	@PostMapping("api/userAnalysisList")
	@ResponseBody
	public List<UserConnect> userAnalysisList(@RequestParam("startDate") String startDate, @RequestParam(name = "endDate") String endDate){
		return userService.userAnalysisStatus(startDate, endDate);
	}

	@PostMapping("api/division_list")
	@ResponseBody
	public List<DivisionChart> getDivisionChartData(@RequestParam("startDate") String startDate, @RequestParam(name = "endDate") String endDate){
		List<DivisionChart> divisionChartData = divisionService.getDivisionChartData(startDate, endDate);
		return divisionChartData;
	}

	@PostMapping("api/genetic_list")
	@ResponseBody
	public List<GeneticChart> getGeneticChartData(@RequestParam("startDate") String startDate, @RequestParam(name = "endDate") String endDate){
		List<GeneticChart> geneticChartData = geneticService.getGeneticChartData(startDate, endDate);
		return geneticChartData;
	}
	
	@GetMapping("api/log_list")
	@ResponseBody
	public List<DataLog> logList(){
		
		return userService.selectDataLogList();
	}
}
