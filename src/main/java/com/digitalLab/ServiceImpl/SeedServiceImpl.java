package com.digitalLab.ServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import com.digitalLab.Entity.SeedEasyParam;
import com.digitalLab.Entity.UserReport;
import com.digitalLab.Entity.Users;
import com.digitalLab.Mapper.EachMapper;
import com.digitalLab.Mapper.GeneticMapper;
import com.digitalLab.Mapper.ReportMapper;
import com.digitalLab.Mapper.SeedMapper;
import com.digitalLab.Service.FileService;
import com.digitalLab.Service.ReportService;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.CodeUtil;
import com.digitalLab.Util.DataLogUtil;
import com.digitalLab.Util.JsonUtil;
import com.digitalLab.Util.RegistUtil;
import com.digitalLab.Util.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalLab.Entity.Each;
import com.digitalLab.Entity.Genetic;
import com.digitalLab.Entity.Report;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.Seed;
import com.digitalLab.Service.SeedService;

@Service	
public class SeedServiceImpl implements SeedService {

	@Autowired
	private SeedMapper seedMapper;

	@Autowired
	private EachMapper eachMapper;
	
	@Autowired
	private ReportService reportService;

	@Autowired
	private ReportMapper reportMapper;

	@Autowired
	private GeneticMapper geneticMapper;

	@Autowired
	private FileService fileService;

	@Autowired
	private DataLogUtil logUtil;

	private static final String sCode = "si";

	private static final String type = "seed";
	
	private static final int DEPTH_1 = 1; 

	private static final int DEPTH_2 = 2; 
	
	private static List<Genetic> genetic_list;
	
	// 시료 일반 등록
	@Override
	public int registSeed(Seed seed) {

		Users user = AuthUtil.sessionUser();
		
		System.out.println(seed);
		if (!seed.isReport_chk()) {
			if (seed.getReport_id() == -1) {
				return -4;
			}
		}

		int result = 1;
		
		// 유저 리포트 생성
		int user_report_id = reportService.seedReportCheck(seed , 0);
		
		for (Seed item : seed.getSeedList()) {

			int seq_id = seedMapper.getSeqId();
			String code = codeCheck(user.getGroupCode());

			item.setUser_report_id(user_report_id);
			item.setSeed_id(seq_id);
			item.setSeed_code(code);
			
			if (item.getGenetic_parents() == -2) {

				int seq_g_id = geneticMapper.getSeqId();

				// 품종 신규등록
				Genetic genetic = new Genetic(seq_g_id, 0, 0 , item.getGenetic_top() , DEPTH_1);

				geneticMapper.insertGenetic(genetic);
				
				item.setGenetic_parents(seq_g_id);
			}

			if (item.getGenetic_id() == -2) {

				int seq_g_id = geneticMapper.getSeqId();

				// 품종 신규등록
				Genetic genetic = new Genetic(seq_g_id, item.getGenetic_parents(), item.getGenetic_type(),
						item.getGenetic() , DEPTH_2);

				geneticMapper.insertGenetic(genetic);
				item.setGenetic_id(seq_g_id);
			}

			result = seedMapper.registSeed(item);

			if (result == 0) {
				return -1;
			}

			logUtil.addDataLog(user.getAccount(), user_report_id, 4, code, type);
		}

		return result;
	}

	// 시료 간편 등록
	@Override
	public int registSeedEasy(SeedEasyParam seed) {

		Users user = AuthUtil.sessionUser();

		int result = -1;
		if (!seed.isReport_chk()) {
			if (seed.getReport_id() == -1) {
				return -4;
			}
		}

		// upload File
		if (seed.getFile() == null) {
			return -23;
		} else if (fileService.extensionCheck(seed.getFile())) {
			return -24;
		}

		String fileName = "";

		try {
			fileName = fileService.uploadAndGetFilePath(seed.getFile(), type);
		} catch (IOException e) {
			System.out.println("IOException");
			return -3;
		}
		
		Seed reportCheckParam = new Seed();
		reportCheckParam.setReport_id(seed.getReport_id());
		reportCheckParam.setReport_comment(seed.getReport_comment());
		reportCheckParam.setReport_chk(seed.isReport_chk());
		
		System.out.println("151 line : "+reportCheckParam);		
		// 유저 리포트 생성
		int user_report_id = reportService.seedReportCheck(reportCheckParam , 1);
		
		int seed_id = seedMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());

		seed.setSeed_id(seed_id);
		seed.setSeed_code(code);
		seed.setUser_report_id(user_report_id);
		seed.setATCH_FILE_NM(fileName);
		seed.setORIG_FILE_NM(seed.getFile().getOriginalFilename());
		seed.setUSEE_AT_CODE(1);
		
		if (seed.getGenetic_parents() == -2) {

			int seq_g_id = geneticMapper.getSeqId();

			// 품종 신규등록
			Genetic genetic = new Genetic(seq_g_id, 0, 0 , seed.getGenetic_top() , DEPTH_1);

			geneticMapper.insertGenetic(genetic);
			
			seed.setGenetic_parents(seq_g_id);
		}

		if (seed.getGenetic_id() == -2) {

			int seq_g_id = geneticMapper.getSeqId();

			// 품종 신규등록
			Genetic genetic = new Genetic(seq_g_id, seed.getGenetic_parents(), seed.getGenetic_type(),
					seed.getGenetic() , DEPTH_2);

			geneticMapper.insertGenetic(genetic);
			seed.setGenetic_id(seq_g_id);
		}
		
		result = seedMapper.registSeedEasy(seed);
		if (result == 0) {
			return -1;
		}

		logUtil.addDataLog(user.getAccount(), user_report_id, 4, code, type);

		return result;
	}

	// 시료 대량 등록
	@Override
	public int registSeedMany(Seed seed) {

		Users user = AuthUtil.sessionUser();

		if (!seed.isReport_chk()) {
			if (seed.getReport_id() == -1) {
				return -4;
			}
		}

		int result = 1;
		
		// 품종 depth1
		geneticDepthList();
		
		// 유저 리포트 생성
		int user_report_id = reportService.seedReportCheck(seed , 0);

		for (Seed item : seed.getSeedList()) {
			
			System.out.println("Seed [i] = item : " + item);

			int seq_id = seedMapper.getSeqId();					// 등록할때마다 id값 순차적으로 증가
			System.out.println("seq_id : " + seq_id);
			
			String code = codeCheck(user.getGroupCode());

			item.setUser_report_id(user_report_id);
			item.setSeed_id(seq_id);
			item.setSeed_code(code);

			
			parseSeedFromMany(item);
			
			result = seedMapper.registSeed(item);

			if (result == 0) {
				return -1;
			}

			logUtil.addDataLog(user.getAccount(), user_report_id, 4, code, type);
		}
		
		Report report = new Report();
		report.setReport_id(user_report_id);
		report.setReport_comment(seed.getReport_comment());
		//reportMapper.updateReportComment(report);

		return result;
	}

	// 시료 템플릿 등록
	@Override
	public int registSeedTemplate(Seed seed) {

		Users user = AuthUtil.sessionUser();

		if (!seed.isReport_chk()) {
			if (seed.getReport_id() == -1) {
				return -4;
			}
		}

		// 유저 리포트 생성
		int user_report_id = reportService.seedReportCheck(seed , 2);

		int seq_id = seedMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());
		
		seed.setUser_report_id(user_report_id);
		seed.setSeed_code(code);
		seed.setSeed_id(seq_id);
		seed.setUSEE_AT_CODE(2);

		try {
			JsonUtil.createJson(code, seed.getTemplate_head(), seed.getTemplate_body());
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");

			return -1;
		}

		int result = seedMapper.registSeed(seed);

		if (result == 0) {
			return -1;
		}

		logUtil.addDataLog(user.getAccount(), user_report_id, 4, code, type);

		return result;
	}

	// 시료 고급 수정
	@Override
	public int modifyManySeed(Seed seed) {
		
		if(seed.getUser_report_id() != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		Users user = AuthUtil.sessionUser();

		int result = 0;

		Report report = new Report();
		report.setReport_id(seed.getUser_report_id());
		report.setReport_comment(seed.getReport_comment());

		result = reportMapper.updateReportComment(report);
		if(result == 0){
			return -1;
		}
		
		System.out.println("seed.getSeedList() : "+seed.getSeedList());
		for (Seed item : seed.getSeedList()) {
			
			if (item.getGenetic_parents() == -2) {

				int seq_g_id = geneticMapper.getSeqId();

				// 품종 신규등록
				Genetic genetic = new Genetic(seq_g_id, 0, 0 , item.getGenetic_top() , DEPTH_1);

				geneticMapper.insertGenetic(genetic);
				
				item.setGenetic_parents(seq_g_id);
			}

			if (item.getGenetic_id() == -2) {

				int seq_g_id = geneticMapper.getSeqId();

				// 품종 신규등록
				Genetic genetic = new Genetic(seq_g_id, item.getGenetic_parents(), item.getGenetic_type(),
						item.getGenetic() , DEPTH_2);

				geneticMapper.insertGenetic(genetic);
				item.setGenetic_id(seq_g_id);
			}
			
			System.out.println("item.getSeed_id() : "+item.getSeed_id());
			
			if(item.getSeed_id() == 0) {
				
				int seq_id = seedMapper.getSeqId();
				String code = codeCheck(user.getGroupCode());

				item.setUser_report_id(seed.getUser_report_id());
				item.setSeed_id(seq_id);
				item.setSeed_code(code);
				
				result = seedMapper.registSeed(item);
				
				logUtil.addDataLog(user.getAccount(), seed.getUser_report_id(), 4, code, type);
			}else {
				result = seedMapper.updateSeed(item);
			}

			if (result == 0) {
				return -1;
			}

		}

		return result;
	}

	//간편등록 수정
	@Override
	public int modifyEasySeed(SeedEasyParam seed) {
		Users user = AuthUtil.sessionUser();
		
		if(seed.getUser_report_id() != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		int result = -1;

		String fileName = "";
		if (seed.getIsFileUpdate() != null && seed.getIsFileUpdate() && seed.getFile() != null) {
			
			if(fileService.extensionCheck(seed.getFile())) {
				return -24;
			}
			
			try {
				fileName = fileService.uploadAndGetFilePath(seed.getFile(), type);
			} catch (IOException e) {
				System.out.println("IOException");
				return -3;
			}
			seed.setATCH_FILE_NM(fileName);
			seed.setORIG_FILE_NM(seed.getFile().getOriginalFilename());
			seed.setUSEE_AT_CODE(1);
		}

		Report report = new Report();
		report.setReport_id(seed.getUser_report_id());
		report.setReport_comment(seed.getReport_comment());

		result = reportMapper.updateReportComment(report);
		if(result == 0){
			return -1;
		}

		if (seed.getGenetic_parents() == -2) {

			int seq_g_id = geneticMapper.getSeqId();

			// 품종 신규등록
			Genetic genetic = new Genetic(seq_g_id, 0, 0 , seed.getGenetic_top() , DEPTH_1);

			geneticMapper.insertGenetic(genetic);
			
			seed.setGenetic_parents(seq_g_id);
		}

		if (seed.getGenetic_id() == -2) {

			int seq_g_id = geneticMapper.getSeqId();

			// 품종 신규등록
			Genetic genetic = new Genetic(seq_g_id, seed.getGenetic_parents(), seed.getGenetic_type(),
					seed.getGenetic() , DEPTH_2);

			geneticMapper.insertGenetic(genetic);
			seed.setGenetic_id(seq_g_id);
		}

		result = seedMapper.updateEasySeed(seed);
		System.out.println("result : " + result);

		if (result == 0) {
			return -1;
		}

		logUtil.addDataLog(user.getAccount(), seed.getUser_report_id(), 2, seed.getSeed_code(), type);

		return result;
	}

	//템플릿등록 수정
	@Override
	public int modifyTemplateSeed(Seed seed) {
		Users user = AuthUtil.sessionUser();
		if(seed.getUser_report_id() != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		int result = -1;

		//report_id 이용해서 report_comment 수정
		Report report = new Report();
		report.setReport_id(seed.getUser_report_id());
		report.setReport_comment(seed.getReport_comment());

		result = reportMapper.updateReportComment(report);
		if(result == 0){
			return -1;
		}

		//템플릿 수정
		try {
			JsonUtil.createJson(seed.getSeed_code(), seed.getTemplate_head(), seed.getTemplate_body());
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");

			return -1;
		}

		logUtil.addDataLog(user.getAccount(), seed.getUser_report_id(), 2, seed.getSeed_code(), type);

		return 1;
	}


	@Override
	public int seedDelete(int seed_id) {

		Seed seed = seedMapper.selectSeedById(seed_id);

		int deleteSeedResult = seedMapper.deleteSeed(seed_id);
		if(deleteSeedResult == 0){
			return 0;
		}

		//시드 갯수 확인
		int reportCnt = reportMapper.getUserReportIdCount(seed_id);
		int deleteUserReportResult = 0;
		int deleteReportAssignmentsResult = 0;
		
		if(reportCnt == 0){
			//UserReport 삭제 (상태값 변경)
			deleteUserReportResult = reportMapper.deleteUserReport(seed_id);

			//과제 비연계 일경우 삭제
			deleteReportAssignmentsResult = reportMapper.deleteReportAssignments(seed_id);
		}

		Users user = AuthUtil.sessionUser();

		logUtil.addDataLog(user.getAccount(), seed.getUser_report_id(), 3, seed.getSeed_code(), type);

		return 1;
	}

	@Override
	public int seedDeleteAll(int user_report_id) {

		List<Integer> seedList = seedMapper.getSeedListByUserReportId(user_report_id);

		for (Integer seed_id : seedList) {
			int result = seedDelete(seed_id);
			if(result == 0){
				return 0;
			}
		}

		return 1;
	}

	@Override
	public List<Seed> selectSeedList(SearchParam param) {
		
		Users user = AuthUtil.sessionUser();
		
		System.out.println("user : " + user);

		param.setAccount(user.getAccount());
		List<Seed> seedList = null;
		
		seedList = seedMapper.selectSeedList(param , user);

		seedList.forEach(e -> {
			String regist_type = RegistUtil.parseRegistType(e.getUSEE_AT_CODE());
			e.setRegist_type(regist_type);
		});

		return seedList;
	}

	// 시료 과제 리스트
	@Override
	public List<UserReport> selectReportSeedList(SearchParam param) {
		
		Users user = AuthUtil.sessionUser();
		
		List<UserReport> list = seedMapper.selectReportSeedList(user);

		for (UserReport report : list) {

			List<Seed> arr = seedMapper.selectSeedByReportId(report.getUser_report_id(), report.getUSEE_AT_CODE());

			if (!arr.isEmpty()) {

				// 중복제거
//				Set<String> overlap1 = new TreeSet<String>();
//				Set<String> overlap2 = new TreeSet<String>();
				List<String> depth_1_list =  new ArrayList<>();
				List<String> depth_2_list = new ArrayList<>();

				for (Seed item : arr) {
					if(item.getGenetic_depth1() != null)
						depth_1_list.add(item.getGenetic_depth1());
					if(item.getGenetic_depth2() != null)
						depth_2_list.add(item.getGenetic_depth2());
				}

				Set<String> depth_1_set = new HashSet<String>(depth_1_list);
				Set<String> depth_2_set = new HashSet<String>(depth_2_list);

				List<String> arr1 = new ArrayList<String>(depth_1_set);
				List<String> arr2 = new ArrayList<String>(depth_2_set);

				String genetic_1 = "";
				String genetic_2 = "";

				genetic_1 = String.join(",",arr1);
				genetic_2 = String.join(",",arr2);

				report.setGenetic_depth1(genetic_1);
				report.setGenetic_depth2(genetic_2);
				report.setSeed_sender(arr.get(0).getSeed_sender());
				report.setPlace_name(arr.get(0).getPlace_name());
				report.setSeed_count(arr.size());
				report.setSend_date(arr.get(0).getSend_date());
				report.setReceive_date(arr.get(0).getReceive_date());
			}
			RegistUtil util = new RegistUtil();
			report.setRegist_type(util.parseRegistType(report.getUSEE_AT_CODE()));
		}

		return list;
	}

	// 시료 단위 리스트
	@Override
	public List<Each> seedEachList() {
		return eachMapper.seedEachList();
	}

	// 시료 품종
	@Override
	public Seed selectSeedByGenetic(int genetic_id) {
		Seed seed = seedMapper.selectSeedByGenetic(genetic_id);

		return seed;
	}

	// 시료 과제 정보
	@Override
	public Map<String, Object> selectSeedByReportId(int user_report_id) {
		Users user = AuthUtil.sessionUser();
		
		SessionUtil.setPkId(user_report_id, type);
		
		Map<String, Object> param = new HashMap<>();
		int state = -1;

		UserReport report = reportMapper.selectUserReportById(user_report_id);
		List<Seed> list = seedMapper.selectSeedByReportId(user_report_id, report.getUSEE_AT_CODE());
		
		if (list == null) {
			// Err
			param.put("state", state);
			return param;
		}

		// 템플릿
		if(list.get(0).getUSEE_AT_CODE() == 2) {
			try {
				Map<String , Object> template = JsonUtil.readJson(list.get(0).getSeed_code());
				list.get(0).setTemplate_head((String)template.get("head"));
				list.get(0).setTemplate_body((String)template.get("body"));
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException");
			}
		}

		String viewName = parseRegistTypeToView(list.get(0).getUSEE_AT_CODE());

		if (viewName == null) {
			// Err
			param.put("state", state);
			return param;
		}
		
		param.put("report" , report);
		param.put("logList", logUtil.logList(user_report_id, type));
		param.put("reportList", reportMapper.selectReportList(user , "detail"));
		param.put("viewName", viewName);
		param.put("seedList", list);

		return param;
	}

	private String codeCheck(String groupCode) {

		String code = sCode + "-" + groupCode;

		code = seedMapper.getCode(code);
		String fullCode = CodeUtil.getFullCode(code, sCode, groupCode);
		return fullCode;
	}

	private String parseRegistTypeToView(int USEE_AT_CODE) {
		switch (USEE_AT_CODE) {
		case 0:
			return "seed/seed_detail";
		case 1:
			return "seed/seed_easy_detail";
		case 2:
			return "seed/seed_template_detail";
		default:
			return "";
		}
	}
	
	// 공백제거
	private String stringGapRemove(String value) {
		
		value = value.trim();
		value = value.replaceAll(" " , "");
		value = value.replaceAll("\\p{Z}", "");

		return value;
	}
	
	private void geneticDepthList(){
		
		genetic_list = geneticMapper.selectGeneticDepth(new Genetic());
	}
	
	private void parseSeedFromMany(Seed seed) {
		parseGenetic(seed);
	}
	
	private String parseType(String type) {
		
		switch (type) {
			case "품종": type = "1";
				break;
			case "유전자원명": type = "2";
				break;
			case "기타": type = "3";
				break;
	
			default: type = "1";
				break;
		}
		
		return type;
	}
	
	private void parseGenetic(Seed seed){
		
		String genetic = stringGapRemove(seed.getGenetic());
		String [] geneticArr = genetic.split("/");
		String type = geneticArr[0];
		String genetic_1 = geneticArr[1];
		String genetic_2 = geneticArr[2];
		
		type = parseType(type);
		seed.setGenetic_type(Integer.parseInt(type));
		
		int genetic_depth1_id = 0;
		int genetic_depth2_id = 0;
		boolean depth1_check = true;
		boolean depth2_check = true;
		
		for(Genetic genetic_depth1 : genetic_list) {
			
			// depth_1
			if(stringGapRemove(genetic_depth1.getGenetic()).equals(genetic_1)) {
				
				genetic_depth1_id = genetic_depth1.getGenetic_id();
				
				seed.setGenetic_parents(genetic_depth1.getGenetic_id());
				depth1_check = false;
			}
		}	
		//insert
		if (depth1_check){
			genetic_depth1_id = geneticMapper.getSeqId();
			// 품종 신규등록
			geneticMapper.insertGenetic(new Genetic(genetic_depth1_id, 0, 0 , genetic_1 , DEPTH_1));
			seed.setGenetic_parents(genetic_depth1_id);
		}
		
		// depth_2 list
		List <Genetic> depth2_list = geneticMapper.selectGeneticDepth(new Genetic(0 , genetic_depth1_id , Integer.parseInt(type) , null ,  DEPTH_2));
			
		// depth_2
		for(Genetic genetic_depth2 : depth2_list) {
				
			if(stringGapRemove(genetic_depth2.getGenetic()).equals(genetic_2)) {
					
				genetic_depth2_id = genetic_depth2.getGenetic_id();
				
				seed.setGenetic_id(genetic_depth2_id);
				depth2_check = false;
			} 
		}
		//insert
		if(depth2_check) {
			genetic_depth2_id = geneticMapper.getSeqId();
			// 품종 신규등록
			geneticMapper.insertGenetic(new Genetic(genetic_depth2_id, genetic_depth1_id, Integer.parseInt(type) , genetic_2 , DEPTH_2));
			seed.setGenetic_id(genetic_depth2_id);
		}
	}

}
