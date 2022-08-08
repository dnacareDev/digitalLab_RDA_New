package com.digitalLab.ServiceImpl;

import com.digitalLab.Entity.*;
import com.digitalLab.Enum.RegistType;
import com.digitalLab.Mapper.GeneticMapper;
import com.digitalLab.Mapper.PlanMapper;
import com.digitalLab.Service.*;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.CodeUtil;
import com.digitalLab.Util.DataLogUtil;
import com.digitalLab.Util.JsonUtil;
import com.digitalLab.Util.RegistUtil;
import com.digitalLab.Util.SessionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class PlanServiceImpl implements PlanService {
	private static final String rCode = "ai";

	private static final String path = "plan";

	private static final String type = "analysis";

	// 계획 등록
	public static final int PLAN_TYPE_REGIST = 1;

	// 계획 설정
	public static final int PLAN_TYPE_SET = 2;

	// 시료 분석
	public static final int PLAN_TYPE_SEED = 3;

	// 결과 등록
	public static final int PLAN_TYPE_RESULT = 4;

	// 데이터 검증
	public static final int PLAN_TYPE_VERIFICATION = 5;

	@Autowired
	private DivisionService divisionService;

	@Autowired
	private PlanMapper planMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Autowired
	private GeneticService geneticService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private BatchService batchService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private PlateService plateService;

	@Autowired
	private DataLogUtil logUtil;

	@Autowired
	private GeneticMapper geneticMapper;

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Override
	public int registPlanEasy(PlanEasyParam plan) {

		Users user = AuthUtil.sessionUser();

		int result = -1;

		plan.setPlan_code(codeCheck(user.getGroupCode()));
		int seq = planMapper.getSeqId();
		plan.setPlan_id(seq);

		// upload File
		if (plan.getFile() == null) {
			return -23;
		}else if(fileService.extensionCheck(plan.getFile())) {
			return -24;
		}

		if (plan.getGenetic_id() == -1) {
			// err
			return -1;
		}
		if (plan.getDivision_id() == -1) {
			// errr
			return -1;
		}

		// Seed seed = seedService.selectSeedByGenetic(plan.getGenetic_id());
//        if(seed == null){
//            //Err
//            plan.setPlan_seed(3);
//        }
//        else{
//            plan.setPlan_seed(seed.getSeed_id());
//        }

		String fileName = "";
		try {
			fileName = fileService.uploadAndGetFilePath(plan.getFile(), path);
		} catch (IOException e) {
			System.out.println("IOException");
			return -3;
		}

		plan.setATCH_FILE_NM(fileName);
		plan.setORIG_FILE_NM(plan.getFile().getOriginalFilename());
		plan.setUSEE_AT_CODE(RegistType.EASY);
		plan.setAccount(user.getAccount());

		result = planMapper.registPlanEasy(plan);
		if (result == 0) {
			// Err
			return -1;
		}

		logUtil.addDataLog(user.getAccount(), plan.getPlan_id(), DataLogUtil.ACTION_REGIST, plan.getPlan_code(), type);

		return result;
	}

	@Override
	public Map<String, Object> registPlan(Plan plan) {

		Users user = userService.getLoginUser();

		Map<String, Object> result = new HashMap<>();
		int state = -1;
		plan.setPlan_code(codeCheck(user.getGroupCode()));
		int plan_id = planMapper.getSeqId();
		plan.setPlan_id(plan_id);
		plan.setUSEE_AT_CODE(RegistType.GENERAL);
		plan.setAccount(user.getAccount());
		state = planMapper.registPlan(plan);
		if (state != 1) {
			state = -1;
			result.put("state", state);
			return result;
		}

		Type listType = new TypeToken<ArrayList<PlanMethod>>() {
		}.getType();
		List<PlanMethod> planMethodList = gson.fromJson(plan.getStrPlanMethod(), listType);

		if (planMethodList.isEmpty()) {
			state = -6;
			result.put("state", state);
			return result;
		}

		PlanMethod planMethod = planMethodList.get(0);
		planMethod.setPlan_id(plan_id);
		state = registPlanMethod(planMethod);
		if (state != 1) {
			state = -1;
			result.put("state", state);
			return result;
		}

		for (int i = 0; i < plan.getSeed_pk_list().size(); i++) {
			state = planMapper.registPlanSeed(plan.getSeed_pk_list().get(i), plan_id);
			if (state != 1) {
				state = -1;
				result.put("state", state);
				return result;
			}
		}

		logUtil.addDataLog(user.getAccount(), plan.getPlan_id(), DataLogUtil.ACTION_REGIST, plan.getPlan_code(), type);
		result.put("status", 1);
		return result;
	}

	@Override
	public int modifyPlan(Plan plan) {

		if(plan.getPlan_id() != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		int result = -1;
		int count = planMapper.deletePlanMethodByPlan(plan.getPlan_id());
		count = planMapper.deletePlanSeedByPlan(plan.getPlan_id());

		Users user = userService.getLoginUser();

		result = planMapper.modifyPlan(plan);
		if (result != 1) {
			result = -1;
		}

		Type listType = new TypeToken<ArrayList<PlanMethod>>() {
		}.getType();
		
		System.out.println("plan.getStrPlanMethod(), : "+plan.getStrPlanMethod());
		List<PlanMethod> planMethodList = gson.fromJson(plan.getStrPlanMethod(), listType);

		if (planMethodList.isEmpty()) {
			result = -6;
			return result;
		}

		PlanMethod planMethod = planMethodList.get(0);
		planMethod.setPlan_id(plan.getPlan_id());
		result = registPlanMethod(planMethod);
		if (result != 1) {
			result = -1;
			return result;
		}

		count = planMapper.registPlanSeedList(plan.getSeed_pk_list(), plan.getPlan_id());
		if (count != plan.getSeed_pk_list().size()) {
			result = -1;
			return result;
		}

		Plan logPlan = planMapper.selectPlanById(plan.getPlan_id());
		logUtil.addDataLog(user.getAccount(), logPlan.getPlan_id(), DataLogUtil.ACTION_MODIFY, logPlan.getPlan_code(), type);

		plateService.deletePlan(plan.getPlan_id());
		batchService.deleteBatchByPlan(plan.getPlan_id());
		scheduleService.deleteSCheduleByPlan(plan.getPlan_id());

		result = 1;

		return result;
	}

	@Override
	public int registPlanMethod(PlanMethod planMethod) {
		int result = -1;
		result = planMapper.registPlanMethod(planMethod);
		return result;
	}

	@Override
	public int modifyPlanEasy(PlanEasyParam plan) {
		
		int result = -1;
		Users user = userService.getLoginUser();
		
		//System.out.println("plan_id : " + plan.getPlan_id());
		
		
		/* 같으면 통과해야하는데 오히려 리턴하는 이유를 모르겠다. != 로 바꿔보고 정상작동하는지 확인
		if(plan.getPlan_id() == SessionUtil.getPkId(type)) {
			return -20;
		}
		*/
		if(plan.getPlan_id() != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		if (plan.getIsFileUpdate() != null && plan.getIsFileUpdate() && plan.getFile() != null) {
			String fileName = "";
			
			if(fileService.extensionCheck(plan.getFile())) {
				return -24;
			}
			
			try {
				fileName = fileService.uploadAndGetFilePath(plan.getFile(), path);
			} catch (IOException e) {
				System.out.println("IOException");
				return -3;
			}
			
			plan.setATCH_FILE_NM(fileName);
			plan.setORIG_FILE_NM(plan.getFile().getOriginalFilename());
		}
		
		//System.out.println("aaa" + plan.getATCH_FILE_NM());
		//System.out.println("bbb" + plan.getORIG_FILE_NM());
		
		if (plan.getGenetic_id() == -2) {

			int seq_g_id = geneticMapper.getSeqId();
			
			// 품종 신규등록
			Genetic genetic = new Genetic(seq_g_id, plan.getGenetic_depth_2_id(), plan.getGenetic_type(), plan.getGenetic_depth_2() , 2);

			geneticMapper.insertGenetic(genetic);
			plan.setGenetic_id(seq_g_id);
		}
		
		Plan modifyedPlan = planMapper.selectPlanById(plan.getPlan_id());
		
		result = planMapper.modifyPlanEasy(plan);
		
		logUtil.addDataLog(user.getAccount(), modifyedPlan.getPlan_id(), DataLogUtil.ACTION_MODIFY,
				modifyedPlan.getPlan_code(), type);

		return result;
	}

	@Override
	@Transactional
	public int setAnalistPlan(PlanSetParam plan) {
		int result = -1;

		Users user = userService.getLoginUser();

		Map<Integer, Plate> plateMap = new HashMap<>();

		result = planMapper.modifyPlanSet(plan);
		if (result != 1) {
			return -1;
		}

		plateService.deletePlan(plan.getPlan_id());

		for (int i = 1; i <= plan.getPlan_batch(); i++) {
			Plate plate = new Plate();
			plate.setPlan_id(plan.getPlan_id());
			plate.setPlate_index(i);
			plateService.registPlate(plate);
			plateMap.put(plate.getPlate_index(), plate);
		}

		Type listType = new TypeToken<ArrayList<Batch>>() {
		}.getType();
		System.out.println(plan.getBatchList());
		List<Batch> batchList = null;
		try {
			batchList = gson.fromJson(plan.getBatchList(), listType);
		} catch (JsonSyntaxException ex) {
			System.out.println("batchParsingErro");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return result;
		}

		batchList.forEach(batch -> {
			Plate plate = plateMap.get(batch.getPlate_index());
			batch.setPlate_id(plate.getPlate_id());
		});

		batchService.registBatchList(batchList, plan.getPlan_id());
		
		Type ScheduleType = new TypeToken<ArrayList<Schedule>>() {
		}.getType();
		Type schType = TypeToken.getParameterized(schedule_manager.class, Schedule.class).getType();

		List<Schedule> scheduleList = null;
		try {
			scheduleList = gson.fromJson(plan.getScheduleList(), ScheduleType);
		} catch (JsonSyntaxException ex) {
			System.out.println("schedule Json Error");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return result;
		}
		scheduleList.forEach(schedule -> {
			Plate plate = plateMap.get(schedule.getPlate_index());
			schedule.setPlate_id(plate.getPlate_id());
			schedule.setPlan_supervisor(user.getAccount());
			System.out.println(schedule);
		});
		scheduleService.registScheduleByPlan(scheduleList, plan.getPlan_id());
		scheduleService.deleteScheduleList(plan.getDeleteScheduleList());

		Plan modifiedPlan = planMapper.selectPlanById(plan.getPlan_id());
		
		logUtil.addDataLog(user.getAccount(), plan.getPlan_id(), DataLogUtil.ACTION_SET, modifiedPlan.getPlan_code(),type);
		return result;
	}

	@Override
	public int setAnalysisPlanEasy(PlanSetEasyParam plan) {
		int result = planMapper.updatePlanSetEasy(plan);

		scheduleService.deleteSCheduleByPlan(plan.getPlan_id());

		List<Schedule> scheduleList = new ArrayList<>();
		Schedule schedule = new Schedule();

		schedule.setSch_type(5);
		schedule.setStart_date(plan.getStart_date());
		schedule.setEnd_date(plan.getEnd_date());
		schedule.setPlan_id(plan.getPlan_id());
		schedule.setPlan_type(RegistType.EASY);
		schedule.setSch_contents(plan.getSch_comment());

		List<schedule_manager> mgr_list = new ArrayList<>();
		schedule_manager supervisor = new schedule_manager();
		supervisor.setAccount(plan.getManager_id());
		supervisor.setMgr_type(1);

		mgr_list.add(supervisor);
		String[] mangers = plan.getSub_manager_id().split(",");
		for(int i = 0; i < mangers.length; i++){
			schedule_manager manager = new schedule_manager();
			manager.setMgr_type(2);
			manager.setAccount(mangers[i]);
			mgr_list.add(manager);
		}

		schedule.setManagerList(mgr_list);
		scheduleList.add(schedule);

		modifyPlanState(plan.getPlan_id(), PLAN_TYPE_SET);
		scheduleService.registScheduleByPlan(scheduleList, plan.getPlan_id());

		Users user = userService.getLoginUser();
		logUtil.addDataLog(user.getAccount(), plan.getPlan_id(), DataLogUtil.ACTION_SET, plan.getPlan_code(), type);

		return 0;
	}

	@Override
	public List<Plan> selectPlanList(PlanSearchParam param) {
		
		Users user = AuthUtil.sessionUser();
		
		List<Plan> list = planMapper.selectPlanList(param , user);
		list.forEach(e -> {
			String regist_type = RegistUtil.parseRegistType(e.getUSEE_AT_CODE());
			e.setRegist_type(regist_type);
		});
		return list;
	}

	@Override
	public Map<String, Object> selectPlanById(int plan_id, int USEE_AT_CODE) {
		Map<String, Object> result = getPlanById(plan_id, PLAN_TYPE_SET, USEE_AT_CODE);
		List<Map<String, Object>> geneticList = getGeneticList(plan_id);
		result.put("geneticData", geneticList);
		return result;
	}

	@Override
	public Map<String, Object> selectRegistPlanById(int plan_id, int USEE_AT_CODE) {
		Map<String, Object> result = getPlanById(plan_id, PLAN_TYPE_REGIST, USEE_AT_CODE);
		return result;
	}

	@Override
	public Map<String, Object> getPlanById(int plan_id, int plan_type, int USEE_AT_CODE) {

		Map<String, Object> result = new HashMap<>();
		
		SessionUtil.setPkId(plan_id, type);

		List<DataLog> logList = logUtil.logList(plan_id, type);
		System.out.println(logList);

		result.put("logList", logList);

		Plan plan = null;
		if (USEE_AT_CODE == RegistType.EASY ) {
			plan = planMapper.selectPlanEasyById(plan_id);
			System.out.println("type 1");
		} else if (USEE_AT_CODE == RegistType.GENERAL) {
			plan = planMapper.selectPlanById(plan_id);
		} else if (USEE_AT_CODE == RegistType.TEMPLATE) {
			
			System.out.println("type 3");
			System.out.println("type 3");
			
			Map<String , Object> map = new HashMap<String ,Object>();
			plan = planMapper.selectPlanEasyById(plan_id);
			
			try {
				map = JsonUtil.readJson(plan.getPlan_code());
				plan.setTemplate_head((String)map.get("head"));
				plan.setTemplate_body((String)map.get("body"));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("FileNotFoundException");
			}
		}
		
		if (plan == null) {
			result.put("state", -1);
			return result;
		}
		parsePlanData(plan, plan_type);
		
		String viewName = getViewName(plan.getUSEE_AT_CODE(), plan_type);
		if (viewName.isEmpty()) {
			result.put("state", -1);
			return result;
		}

		if (plan.getUSEE_AT_CODE() == RegistType.EASY) {
			DivisionSearchParam param = new DivisionSearchParam();
			Users user = AuthUtil.sessionUser();
			param.setAccount(user.getAccount());
			param.setAuthority(user.getAuthority());
			param.setUser_code("mi-"+user.getGroupCode());
			
			Map<String, Object> division = divisionService.selectDivisionListByParents(param);
			
			Genetic genetic = new Genetic();
			List<Genetic> geneticList = geneticService.selectGeneticDepth(genetic);
			result.put("division_detail", divisionService.selectDivisionByAnalysis(plan_id));
			result.put("division", division);
			result.put("genetic", geneticList);
			result.put("reportList", reportService.reportList("seed"));
		}
		if (plan.getUSEE_AT_CODE() == RegistType.GENERAL && plan_type == PLAN_TYPE_SET) {
			List<Batch> batchList = batchService.selectBatchByPlan(plan.getPlan_id());
			result.put("batch", batchList);

			List<Schedule> sch_list = scheduleService.selectScheduleByPlan(plan.getPlan_id());
			result.put("schedule", sch_list);
		}
		else if(plan_type == PLAN_TYPE_SET){
			List<Schedule> sch_list = scheduleService.selectScheduleByPlan(plan.getPlan_id());
			result.put("schedule", sch_list);
		}
		
		result.put("logList", logUtil.logList(plan_id, type));
		result.put("plan", plan);
		result.put("viewName", viewName);
		result.put("state", 1);
		return result;
	}

	public Map<String, Object> selectTemplate(int plan_id) {

		Map<String, Object> result = new HashMap<String, Object>();

		List<DataLog> logList = logUtil.logList(plan_id, type);
		result.put("logList",logList);
		
		Plan plan =  planMapper.selectPlanTemplateById(plan_id);

		try {
			Map<String , Object> template = JsonUtil.readJson(plan.getPlan_code());
			plan.setTemplate_head((String)template.get("head"));
			plan.setTemplate_body((String)template.get("body"));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}
		
		DivisionSearchParam param = new DivisionSearchParam();
		Users user = AuthUtil.sessionUser();
		param.setAccount(user.getAccount());
		param.setAuthority(user.getAuthority());
		param.setUser_code("mi-"+user.getGroupCode());
		
		result.put("plan", plan);
		result.put("division_detail", divisionService.selectDivisionByAnalysis(plan_id));
		result.put("division", divisionService.selectDivisionListByParents(param));
		result.put("geneticList", geneticService.selectGeneticDepth(new Genetic()));
		result.put("reportList", reportService.reportList("report"));
		result.put("viewName", "analysis_detail/plan_template_detail");

		return result;
	}

	@Override
	public List<PlanApiParam> selectPlanByApi(String account) {
		List<PlanApiParam> list = planMapper.selectPlanByApi(account);

		return list;
	}

	@Override
	public PlanApiDetailParam selectPlanByApiDetail(int plan_id) {
		PlanApiDetailParam result = planMapper.selectPlanByApiDetail(plan_id);

		return result;
	}

	@Override
	public Map<String, Object> selectPlanByMethod(int method_id) {
		Map<String, Object> result = new HashMap<>();
		List<PlanResult> planList = planMapper.selectPlanByMethod(method_id);

		result.put("state", 1);
		result.put("list", planList);

		return result;
	}

	@Override
	public int modifyPlanState(int plan_id, int plan_sate) {
		
		if(plan_id != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		int result = -1;
		result = planMapper.modifyPlanState(plan_id, plan_sate);
		return result;
	}

	private String codeCheck(String groupCode) {

		String code = rCode + "-" + groupCode;

		code = planMapper.getCode(code);
		String fullCode = CodeUtil.getFullCode(code, rCode, groupCode);
		return fullCode;
	}

	private static String getViewName(int USEE_AT_CODE, int plan_type) {
		if (plan_type == PLAN_TYPE_SET) {
			return parseRegistTypeToView(USEE_AT_CODE);
		} else if (plan_type == PLAN_TYPE_REGIST) {
			return parseRegistToView(USEE_AT_CODE);
		}
		return "";
	}

	// 분석 계획 설정 view
	private static String parseRegistTypeToView(int USEE_AT_CODE) {

		switch (USEE_AT_CODE) {
		case RegistType.GENERAL:
			return "analysis_plan/analysis_plan_modify";
		case RegistType.EASY:
			return "analysis_plan/analysis_plan_easy_detail";
		case RegistType.TEMPLATE:
			return "analysis_plan/analysis_plan_template_detail";
		default:
			return "";
		}
	}

	private static String parseRegistToView(int USEE_AT_CODE) {
		switch (USEE_AT_CODE) {
		case RegistType.GENERAL:
			return "analysis_detail/plan_detail";
		case RegistType.EASY:
			return "analysis_detail/plan_easy_detail";
		case RegistType.TEMPLATE:
			return "analysis_detail/plan_template_detail";
		default:
			return "";
		}
	}

	private void parsePlanData(Plan plan, int plan_type) {
		switch (plan.getUSEE_AT_CODE()) {
		case RegistType.GENERAL: {
			parsePlanGenralType(plan);
			break;
		}
		case RegistType.EASY: {
			break;
		}
		case RegistType.TEMPLATE: {
			break;
		}

		}
	}

	@Override
	public void parsePlanGenralType(Plan plan) {
		List<PlanMethod> planMethodList = planMapper.selectPlanMethod(plan.getPlan_id());
		plan.setPlanMethodList(planMethodList);

		List<Integer> seed_pk_list = planMapper.selectPlanSeedPk(plan.getPlan_id());
		plan.setSeed_pk_list(seed_pk_list);
		plan.setSeed_ammount(seed_pk_list.size());
	}

	@Override
	public List<Map<String, Object>> getGeneticList(int plan_id) {
		List<Genetic> list = geneticService.selectGeneticIdByPlan(plan_id);
		List<Map<String, Object>> resultList = new ArrayList<>();

		list.forEach(e -> {
			String parentsName = e.getParents_name();

			Map<String, Object> geneticData = null;
			for (Map<String, Object> result : resultList) {
				Object parent_name = result.get("parent_name");
				if (parent_name.equals(parentsName)) {
					geneticData = result;
				}
			}
			if (geneticData == null) {
				geneticData = new HashMap<>();
				geneticData.put("parent_name", parentsName);
				List<Genetic> genetics = new ArrayList<>();
				geneticData.put("geneticList", genetics);
				resultList.add(geneticData);
			}
			Object obj = geneticData.get("geneticList");
			List<Genetic> genetics = (ArrayList<Genetic>) obj;
			genetics.add(e);
		});

		return resultList;
	}

	// 분석 템플릿 추가
	@Override
	public int registPlanTemplate(Plan plan) {
		List<DataLog> logList = logUtil.logList(plan.getPlan_id(), type);
		System.out.println(logList);

		Users user = userService.getLoginUser();

		int seq_id = planMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());

		plan.setPlan_code(code);
		plan.setPlan_id(seq_id);
		plan.setAccount(user.getAccount());

		System.out.println("plan = " + plan);
		if (plan.getGenetic_id() == -2) {

			int seq_g_id = geneticMapper.getSeqId();

			// 품종 신규등록
			Genetic genetic = new Genetic(seq_g_id, plan.getGenetic_depth_2_id(), plan.getGenetic_type(), plan.getGenetic_depth_2() , 2);

			geneticMapper.insertGenetic(genetic);
			plan.setGenetic_id(seq_g_id);
		}

		try {
			JsonUtil.createJson(code, plan.getTemplate_head(), plan.getTemplate_body());
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}

		plan.setAccount(user.getAccount());
		int result = planMapper.registPlanTemplate(plan);

		logUtil.addDataLog(user.getAccount(), seq_id, DataLogUtil.ACTION_REGIST, code, type);

		return result;
	}

	@Override
	public int updatePlanTemplate(Plan plan) {
		
		Users user = userService.getLoginUser();
		
		if(plan.getPlan_id() != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		if (plan.getGenetic_id() == -2) {

			int seq_g_id = geneticMapper.getSeqId();

			// 품종 신규등록
			Genetic genetic = new Genetic(seq_g_id, plan.getGenetic_depth_2_id(), plan.getGenetic_type(), plan.getGenetic_depth_2() , 2);

			geneticMapper.insertGenetic(genetic);
			plan.setGenetic_id(seq_g_id);
		}

		try {
			JsonUtil.createJson(plan.getPlan_code(), plan.getTemplate_head(), plan.getTemplate_body());
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}

		int result = planMapper.updatePlanTemplate(plan);

		logUtil.addDataLog(user.getAccount(), plan.getPlan_id(), DataLogUtil.ACTION_MODIFY, plan.getPlan_code(), type);

		return result;
	}

	@Override
	public int updateLatestPlanData(int plate_id, int step_id, int plan_id) {
		int result = -1;
		result = planMapper.updateLatestPlanData(plate_id,step_id, plan_id);
		return result;
	}

	@Override
	public int modifyAnalysisPlanTemplate(PlanSetEasyParam plan) {

		int result = planMapper.updatePlanSetTemplate(plan);
		
		if(plan.getPlan_id() != SessionUtil.getPkId(type)) {
			return -20;
		}

		scheduleService.deleteSCheduleByPlan(plan.getPlan_id());

		List<Schedule> scheduleList = new ArrayList<>();
		Schedule schedule = new Schedule();

		schedule.setSch_type(5);
		schedule.setStart_date(plan.getStart_date());
		schedule.setEnd_date(plan.getEnd_date());
		schedule.setPlan_id(plan.getPlan_id());
		schedule.setPlan_type(RegistType.TEMPLATE);
		schedule.setSch_contents(plan.getSch_comment());

		List<schedule_manager> mgr_list = new ArrayList<>();
		schedule_manager supervisor = new schedule_manager();
		supervisor.setAccount(plan.getManager_id());
		supervisor.setMgr_type(1);

		mgr_list.add(supervisor);
		String[] mangers = plan.getSub_manager_id().split(",");
		for(int i = 0; i < mangers.length; i++){
			schedule_manager manager = new schedule_manager();
			manager.setMgr_type(2);
			manager.setAccount(mangers[i]);
			mgr_list.add(manager);
		}

		schedule.setManagerList(mgr_list);
		scheduleList.add(schedule);

		modifyPlanState(plan.getPlan_id(), PLAN_TYPE_SET);
		scheduleService.registScheduleByPlan(scheduleList, plan.getPlan_id());

		Users user = userService.getLoginUser();
		logUtil.addDataLog(user.getAccount(), plan.getPlan_id(), DataLogUtil.ACTION_SET, plan.getPlan_code(), type);

		return result;
	}

	@Override
	public PlanBatchResult selectPlanByBatch(int batch_id) {
		PlanBatchResult plan = planMapper.selectPlanByBatch(batch_id);

		return plan;
	}


}
