package com.digitalLab.Controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalLab.Aria.Cipher;
import com.digitalLab.Entity.DataLog;
import com.digitalLab.Entity.Plan;
import com.digitalLab.Entity.PlanApiDetailParam;
import com.digitalLab.Entity.PlanSearchParam;
import com.digitalLab.Entity.Users;
import com.digitalLab.Service.FileService;
import com.digitalLab.Service.LoginService;
import com.digitalLab.Service.PlanService;
import com.digitalLab.Service.RestApiService;
import com.digitalLab.Service.SampleService;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.Base64Encoding;
import com.digitalLab.Util.DataLogUtil;

@RestController
public class RestApiController {
	@Autowired
	private RestApiService service;

	@Autowired
	private FileService fileService;

	@Autowired
	private SampleService sampleService;

	@Autowired
	private PlanService planService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private DataLogUtil dataLogUtil;
	
	// 퀵 메뉴 이동
	@GetMapping("api/ariaEncoding")
	public String quickMenu()
	{	
		Users user = AuthUtil.sessionUser();
		
		String account = Cipher.ariaEncoding(user.getAccount());
		
		return account;
	}
		
	// 시약 목록 조회
	@GetMapping("api/reagent/list")
	public Map<String, Object> SelectReagentList(@RequestParam String id)
	{
		return service.SelectReagentList(id);
	}

	// 시약 상세 조회
	@GetMapping("api/reagent")
	public ModelAndView SelectReagentDetail(ModelAndView mv, @RequestParam String id,
			@RequestParam String code){
		Map<String , Object> map = service.SelectReagentDetail(id, code);
		
		if((Integer)map.get("result") == 1) {
			mv.setViewName((String)map.get("url"));
		}

		return mv;
	}

	// 장비 목록 조회
	@GetMapping("api/equipment/list")
	public Map<String, Object> SelectEquipmentList(@RequestParam String id)
	{
		return service.SelectEquipmentList(id);
	}

	// 장비 상세 조회
	@GetMapping("api/equipment")
	public ModelAndView SelectEquipmentDetail(ModelAndView mv, @RequestParam String id,
			@RequestParam String code) throws UnsupportedEncodingException {
		Map<String , Object> map = service.SelectEquipmentDetail(id, code);
		
		if((Integer)map.get("result") == 1) {
			mv.setViewName((String)map.get("url"));
		}

		return mv;
	}

	// 메소드 목록 조회
	@GetMapping("api/method/list")
	public Map<String, Object> SelectMethodList(@RequestParam(required = false) String word)
			throws UnsupportedEncodingException {
		return service.SelectMethodList(word);
	}

	// 메소드 상세 조회
	@GetMapping("api/method")
	public ModelAndView SelectMethodDetail(ModelAndView mv, @RequestParam String id,
			@RequestParam String code) throws UnsupportedEncodingException {
		Map<String , Object> map = service.SelectMethodDetail(id, code);

		if((Integer)map.get("result") == 1) {
			mv.setViewName((String)map.get("url"));
		}
		
		return mv;
	}

	@GetMapping("api/analysis-plan")
	public Map<String , Object> selectPlanByApi(@RequestParam String id){

		return service.selectPlanByApi(id);
	}

	@PostMapping("sendRepository")
	public String SendRepository(@RequestParam("plan_id") int plan_id, @RequestParam(required = false, value = "file") MultipartFile file) throws IOException
	{	
		Users user = AuthUtil.sessionUser();
		String report_code = service.SelectReportCode(plan_id);
		
		Date date = new Date();
		String date_name = (1900 + date.getYear()) + "" + (date.getMonth() + 1) + "" + date.getDate() + "" + date.getHours() + "" + date.getMinutes() + "" + date.getSeconds();

		String path = "upload/repository";
		
		Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
		File filePath = new File(fileLocation.toString());
		
		if(!filePath.exists())
		{
			if(filePath.mkdir()) 
			{
				System.out.println("mkdir success");
			}else{
				System.out.println("mkdir fail");
			};
		}
		
		String[] extension = file.getOriginalFilename().split("\\.");
		String file_name = date_name + "." + extension[1];
		
		Path targetLocation = fileLocation.resolve(file_name);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		 
		long file_size = file.getSize();
		String file_type = file.getContentType();

		int note_id = dataLogUtil.addDataLog(user.getAccount(), plan_id, 9, report_code, "repository",
				path+"/"+file_name, file.getOriginalFilename(), file_size, file_type);

		String client_id = "dlab";
		String client_secret = "idr";
		
		//client_id = Cipher.ariaEncoding(client_id);
		
		String result = "client_id=" + client_id + "&node_id=" + note_id + "&state=" + date_name;
		
		return result;
	}

	@GetMapping("metadataRepository")
	public Map<String, Object> MetadataRepository(@RequestParam("client_id") String client_id, @RequestParam("client_secret") String client_secret, @RequestParam("node_id") int node_id, @RequestParam("state") String state)
	{
		Map<String, Object> result = new HashMap<String, Object>();

		if(client_id.equals("idr") && client_secret.equals("idr"))
		{
			DataLog note = service.selectDataLog(node_id);

			String[] note_name = note.getNote_file().split("/");
			
			System.out.println("///////////////////");
			System.out.println("noteName : "+note.getNote_file());
			System.out.println("///////////////////");
			
			result.put("name", note_name[2]);
			result.put("size", note.getNote_file_size());
			result.put("lastModified", note.getLog_date());
			result.put("contentType", note.getNote_file_type());
		}

		return result;
	}

	@GetMapping("downloadRepository")
	public ResponseEntity<Object> NardaDownload(@RequestParam("client_id") String client_id, @RequestParam("client_secret") String client_secret, @RequestParam("node_id") int node_id, @RequestParam("state") String state)
	{
		if(client_id.equals("idr") && client_secret.equals("idr"))
		{
			DataLog note = service.selectDataLog(node_id);

			String path = note.getNote_file();

			try
			{
				Path filePath = Paths.get(path);
				Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기

				File file = new File(path);

				// 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
				HttpHeaders headers = new HttpHeaders();
				headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());

				return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
			}
			catch(IOException e)
			{
				System.out.println("IOException");

				return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
			}
		}
		else
		{
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("checkRepository")
	public int CheckRepository(@RequestParam("client_id") String client_id, @RequestParam("client_secret") String client_secret, @RequestParam("status") String status, @RequestParam("state") String state)
	{
		int result = 0;

		if (client_id.equals("idr") && client_secret.equals("idr"))
		{
			if (status.equals("success"))
			{
				result = 1;
			}
			else
			{
				result = 2;
			}
		}

		return result;
	}
	
	// 연구 노트
	@PostMapping("sendNote")
	public int SendNote(@RequestParam("plan_id") int plan_id, @RequestParam(required = false, value = "file") MultipartFile file) throws IOException
	{	
		Users user = AuthUtil.sessionUser();
		String account = Cipher.ariaEncoding(user.getAccount());

		int result = 0;
		
		Date date = new Date();
		String date_name = (1900 + date.getYear()) + "" + (date.getMonth() + 1) + "" + date.getDate() + "" + date.getHours() + "" + date.getMinutes() + "" + date.getSeconds();
		
		String[] extension = file.getOriginalFilename().split("\\.");
		
		String report_code = service.SelectReportCode(plan_id);
		
//		String url = "http://researchworks.argonet.co.kr:28180/api/elnupload";
		String url = "http://10.30.220.172/api/elnupload";
		
		ByteArrayResource fileResource = new ByteArrayResource(file.getBytes())
		{
			@Override
			public String getFilename()
			{
				return date_name + "_stady_note." + extension[1];
			}
		};
		
		System.out.println("///////////////////////");
		System.out.println("////"+report_code+"/////");
		System.out.println("///////////////////////");
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("projectCode", report_code);
		map.add("userId", account);
		map.add("file", fileResource);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(map, headers);
		
		RestTemplate template = new RestTemplate();
		ResponseEntity<Map> response = template.exchange(url, HttpMethod.POST, entity, Map.class);
		
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getBody());
		System.out.println(response.getBody().get("Status"));
		
		String file_name = fileService.uploadAndGetFilePath(file, "note");
		long file_size = file.getSize();
		String file_type = file.getContentType();

		int action_type = 0;
		if(response.getStatusCodeValue() != 200 || response.getBody().get("Status").equals("S"))
		{
			action_type = 7;
			result = 1;
		}
		else
		{
			action_type = 8;
		}
		
		dataLogUtil.addDataLog(user.getAccount(), plan_id, action_type, report_code, "note", 
				file_name, file.getOriginalFilename(), file_size, file_type);
		
		return result;
	}
	
	// 분석 리스트
	@GetMapping("api/analysis-list")
	public List<Plan> analysisList(@RequestParam String id , @RequestParam(defaultValue = "-1") int plan_status){
		PlanSearchParam param = new PlanSearchParam();
		param.setPlan_status(plan_status);

		return planService.selectPlanList(param);
	}

	@GetMapping("api/analysis-detail")
	public PlanApiDetailParam analysisDetai(@RequestParam int plan_id){
		
		PlanApiDetailParam result = planService.selectPlanByApiDetail(plan_id);
		return result;
	}
	
	// 전체시약수 전체장비수 전체메소드수
	@GetMapping("api/{type}/count")
	public int totalCount(@PathVariable String type){
		
		return service.totalCount(type);
	}

	@GetMapping("api/analysis-schedule")
	public Map<String, Object> selectAnalysisSchedule(@RequestParam String id){
		
		return service.selectAnalysisSchedule(id);
	}

	@GetMapping("api/analysis-data")
	public Map<String,Object> selectAnalysisData(@RequestParam String plan_code){
		
		plan_code = Cipher.ariaDecoding(plan_code);
		
		return sampleService.getSampleDataApi(plan_code);
	}
	
	@GetMapping("analysis/sample_analysis_modify")
	public ModelAndView selecetSampleAnalysis(ModelAndView mv , 
													@RequestParam int plan_id , 
													@RequestParam(defaultValue = "0") int regist_type,
			   										@RequestParam(defaultValue = "") String id){
		
		Users user = loginService.login(id);
		
		Map<String,Object> result = sampleService.sampleDetail(plan_id , regist_type);
		
		mv.addObject("result",result);
		mv.setViewName(result.get("viewName").toString());
		return mv;	
	}
	
	@PostMapping("api/batch/modify")
	public String modifyBatchApi(@RequestParam int sample_id, @RequestParam int step_id, @RequestParam String value){
		String result = "f";
	    result = service.modifySampleByApi(sample_id,step_id,value);

	    return result;
	 }
	
}