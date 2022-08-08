package com.digitalLab.ServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalLab.Entity.CommonDivision;
import com.digitalLab.Entity.Division;
import com.digitalLab.Entity.DivisionSearchParam;
import com.digitalLab.Entity.Each;
import com.digitalLab.Entity.Input;
import com.digitalLab.Entity.Method;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.Step;
import com.digitalLab.Entity.Users;
import com.digitalLab.Mapper.EachMapper;
import com.digitalLab.Mapper.InputMapper;
import com.digitalLab.Mapper.MethodMapper;
import com.digitalLab.Mapper.StepMapper;
import com.digitalLab.Service.DivisionService;
import com.digitalLab.Service.FileService;
import com.digitalLab.Service.MethodService;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.CodeUtil;
import com.digitalLab.Util.DataLogUtil;
import com.digitalLab.Util.JsonUtil;
import com.digitalLab.Util.RegistUtil;
import com.digitalLab.Util.SessionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class MethodServiceImpl implements MethodService {

	@Autowired
	private MethodMapper methodMapper;
	
	@Autowired
	private StepMapper stepMapper;
	
	@Autowired
	private InputMapper inputMapper;
	
	@Autowired
	private EachMapper eachMapper;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private DivisionService divisionService;
	
	@Autowired
	private DataLogUtil logUtil;
	
	private static final String mCode = "mi";
	private static final String type = "method";

	// 메서드 일반 등록
	@Override
	public int registMethod(Method method){

		Users user = AuthUtil.sessionUser();
		
		// division 등록
		int result = divisionCheck(method);
		
		if(result == -5) {
			
			return result;
		}
		method.setDivision_id(result);
		
		int seq_id = methodMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());
		
		method.setMethod_id(seq_id);
		method.setMethod_code(code);
		method.setAccount(user.getAccount());
		
		result = methodMapper.registMethod(method);
		
		if (result == 0) {
			return -1;
		}
		
		jsonStepList(method.getStep_data(), seq_id);
		
		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);
		
		return 1;
		//return result;
	}
	
	// 메서드 간편 등록
	@Override
	public int registMethodEasy(Method method) {
		
		Users user = AuthUtil.sessionUser();
		
		// 분석항목 등록
		int result = divisionCheck(method);
		if(result == -5) {
					
			return result;
		}
		method.setDivision_id(result);
		////////
		
		// upload File
		if (method.getFile() == null) {
			return -23;
		} else if (fileService.extensionCheck(method.getFile())) {
			return -24;
		}
		
		String fileName = "";
		try {
			fileName = fileService.uploadAndGetFilePath(method.getFile(), type);
		} catch (IOException e) {
			System.out.println("IOException");
			return -3;
		}
		
		int seq_id = methodMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());
		
		method.setMethod_id(seq_id);
		method.setMethod_code(code);
		method.setATCH_FILE_NM(fileName);
		method.setORIG_FILE_NM(method.getFile().getOriginalFilename());
		method.setUSEE_AT_CODE(1);
		method.setAccount(user.getAccount());

		result = methodMapper.registMethodEasy(method);
		
		if (result == 0) {
			return -1;
		}

		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);

		return result;
	}

	// 메서드 템플릿 등록
	@Override
	public int registMethodTemplate(Method method) {
		
		Users user = AuthUtil.sessionUser();
		
		// 분석항목 등록
		int result = divisionCheck(method);
		
		if(result == -5) {
							
			return result;
		}
		method.setDivision_id(result);
		////////
		
		int seq_id = methodMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());
		
		method.setMethod_id(seq_id);
		method.setMethod_code(code);
		method.setUSEE_AT_CODE(2);
		method.setAccount(user.getAccount());

		result = methodMapper.registMethod(method);

		if (result == 0) {
			return -1;
		}
		
		try {
			JsonUtil.createJson(code, method.getTemplate_head(), method.getTemplate_body());
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}
		
		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);
		
		return result;
	}
	
	@Override
	public int updateMethod(Method method) {

		System.out.println("method : " + method);

		Users user = AuthUtil.sessionUser();
		method.setMethod_id(SessionUtil.getPkId(type));

		int result = 0;

		if(method.getUSEE_AT_CODE() == 0) {
			
			result = methodMapper.updateMethod(method);
			
			if(method.getStep_data() != null) {
				
				stepMapper.deleteStep(method.getMethod_id());
				jsonStepList(method.getStep_data(), method.getMethod_id());
			}
			
		} else if(method.getUSEE_AT_CODE() == 1){
			String fileName = "";

			if(method.getIsFileUpdate() && method.getFile() != null) {
				
				if (fileService.extensionCheck(method.getFile())) {
					return -24;
				}
				
				try {
					fileName = fileService.uploadAndGetFilePath(method.getFile(), type);
				}catch (IOException e){
					System.out.println("IOException");
					return -3;
				}

				method.setATCH_FILE_NM(fileName);
				method.setORIG_FILE_NM(method.getFile().getOriginalFilename());
			}

			result = methodMapper.updateMethodEasy(method);

		} else if(method.getUSEE_AT_CODE() == 2){

			try {
				JsonUtil.createJson(method.getMethod_code(), method.getTemplate_head(), method.getTemplate_body());
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException");
			}

			result = methodMapper.updateMethod(method);
		}

		if (result == 0) {
			// Err
			return -1;
		}

		logUtil.addDataLog(user.getAccount(), method.getMethod_id(), 2, method.getMethod_code(), type);

		return result;
	}

	@Override
	public List<Method> selectMethodList(SearchParam param) {
		
		Users user = AuthUtil.sessionUser();
		
		param.setAuthority(user.getAuthority());
		param.setAccount(user.getAccount());
		param.setUser_code(mCode+'-'+user.getGroupCode());
		
		List<Method> list = methodMapper.selectMethodList(param);
		
		list.forEach(e -> {
			String regist_type = RegistUtil.parseRegistType(e.getUSEE_AT_CODE());
			e.setRegist_type(regist_type);
			e.setStatus_type(parseMethodStatus(e.getMethod_status()));
			//e.setStepNo(stepMapper.stepTotalCount(e.getMethod_id()));
		});
		
		return list;
	}

	@Override
	public Map<String, Object> selectMethodByDivision(int division_id) {

		Map<String, Object> map = new HashMap<>();

		Method method = methodMapper.selectMethodByDivision(division_id);
		method.setStepList(methodStepList(method.getMethod_id()));
		CommonDivision methodDivision = divisionService.selectDivisionByMethod(method.getMethod_id());

		map.put("method", method);
		map.put("division_detail", methodDivision);

		return map;
	}

	// 메서드 상세보기
	@Override
	public Map<String, Object> selectMethodById(int method_id) {

		Map<String, Object> map = new HashMap<>();
		SessionUtil.setPkId(method_id , type);

		Method method = methodMapper.selectMethodById(method_id);
		method.setStepList(methodStepList(method_id));
		
		int state = 1;
		
		if (method == null) {
			map.put("state", -1);
			return map;
		}
		String viewName = parseRegistTypeToView(method.getUSEE_AT_CODE());
		if (viewName.isEmpty()) {
			map.put("state", -1);
			return map;
		}
		
		if(method.getUSEE_AT_CODE() == 0) {
			
			method.setMethod_protocol("");
		}
		
		if(method.getUSEE_AT_CODE() == 2) {
			try {
				Map <String , Object> template = JsonUtil.readJson(method.getMethod_code());
				method.setTemplate_head((String)template.get("head"));
				method.setTemplate_body((String)template.get("body"));
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException");
			}
		}
		
		DivisionSearchParam param = new DivisionSearchParam();
		
		Users user = AuthUtil.sessionUser();
		param.setAccount(user.getAccount());
		param.setAuthority(user.getAuthority());
		param.setUser_code("mi-"+user.getGroupCode());
		
		Map<String, Object> division = divisionService.selectDivisionListByParents(param);

		CommonDivision methodDivision = divisionService.selectDivisionByMethod(method_id);
		
		map.put("logList" , logUtil.logList(method_id, type));
		map.put("division_detail", methodDivision);
		map.put("division", division);
		map.put("viewName", viewName);
		map.put("state", state);
		map.put("method", method);
		return map;
	}
	
	// 실험방법 삭제
	@Override
	public int deleteMethod(int method_id) {
		// TODO Auto-generated method stub
		
		if(method_id != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		return methodMapper.deleteMethod(method_id);
	}
	
	// 스텝 리스트
	@Override
	public List<Step> methodStepList(int method_id) {
		
		List<Step> list = stepMapper.methodStepList(method_id);
		
		for (Step step : list) {
			for(Step step2 : step.getSub_step_list()) {
				
				step2.setInputList(inputMapper.selectStepInput(step2.getStep_id()));
			}
		}
		
		return list;
	}
	
	// 분석항목 신규등록 체크
	private int divisionCheck(Method method) {
		
		int divisionResult = 0;
		int depth = 0;
		
		if(method.getDivision_depth_id_1() == -2) {
			depth = 1;
			
			Division division = new Division(method.getDivision_1(), depth, 0);
			
			divisionResult = divisionService.registDivision(division);
			
			if (divisionResult < 0) { // Err
				return -5;
			}
		}else {
			divisionResult = method.getDivision_depth_id_1();
		}
		
		if(method.getDivision_depth_id_2() == -2) {
			depth = 2;
			
			Division division = new Division(method.getDivision_2(), depth, divisionResult);
			
			divisionResult = divisionService.registDivision(division);
			
			if (divisionResult < 0) { // Err
				return -5;
			}
		}else {
			divisionResult = method.getDivision_depth_id_2();
		}
		
		if(method.getDivision_parents() == -2) {
			depth = 3;
			
			Division division = new Division(method.getDivision_3(), depth, divisionResult);
			
			divisionResult = divisionService.registDivision(division);
			
			if (divisionResult < 0) { // Err
				return -5;
			}
		}else {
			divisionResult = method.getDivision_parents();
		}
		
		if(method.getDivision_id() == -2) {
			depth = 4;

			Division division = new Division(method.getDivision(), depth, divisionResult);

			divisionResult = divisionService.registDivision(division);

			if (divisionResult < 0) { // Err
				return -5;
			}
		}
		
		return divisionResult;	
	}
	
	// 스탭 추가
	private void jsonStepList(String step_data , int method_id){
		
		Users user = AuthUtil.sessionUser();
		
		String arr = "["+step_data+"]";
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Step[] array = gson.fromJson(arr, Step[].class);
		List<Step> list = Arrays.asList(array);
		
		// 메인 스탭
		for(Step step : list) {
			
			int seq_id = stepMapper.getSeqId();
			
			step.setMethod_id(method_id);
			step.setStep_id(seq_id);
			step.setAccount(user.getAccount());
			
			List<Input> inputList = new ArrayList<Input>();
			
			int[] step_each = step.getStep_each();
			String[] step_input = step.getStep_input();
			
			stepMapper.insertStep(step);
			
			if(step.getStep_type() == 4) {
				
				for(int i = 0; i<step_input.length; i++) {
					
					if(!step_input[i].isEmpty()) {
						Input input = new Input();
						input.setStep_id(seq_id);
						input.setInput_id(inputMapper.getSeqId());
						input.setInput(step_input[i]);
						
						inputList.add(input);
					}
				}
				
			}else {
				
				for(int i = 0; i<step_each.length; i++) {
					
					if(step_each[i] != -1) {
						if(step_input[i] != null) {
							Input input = new Input();
							input.setStep_id(seq_id);
							input.setInput_id(inputMapper.getSeqId());
							input.setEach_id(step_each[i]);
							input.setInput(step_input[i]);
							
							inputList.add(input);
						}
					}
				}
				
			}
			
			
			if(inputList.size() > 0) {
				inputMapper.insertInput(inputList);
			}
			
			if(step.getSub_step_list() != null) {
				// 서브 스탭
				for(Step sub : step.getSub_step_list()) {
					
					int sub_id = stepMapper.getSeqId();
					
					sub.setStep_parent(seq_id);
					sub.setMethod_id(method_id);
					sub.setStep_id(sub_id);
					sub.setAccount(user.getAccount());
					
					inputList = new ArrayList<Input>();
					
					step_each = sub.getStep_each();
					step_input = sub.getStep_input();
					
					stepMapper.insertStep(sub);
					
					if(step.getStep_type() == 4) {
						
						for(int i = 0; i<step_input.length; i++) {
							
							if(!step_input[i].isEmpty()) {
								Input input = new Input();
								input.setStep_id(sub_id);
								input.setInput_id(inputMapper.getSeqId());
								input.setInput(step_input[i]);
								
								inputList.add(input);
							}
						}
						
					}else {
						
						for(int i = 0; i<step_each.length; i++) {
							
							if(step_each[i] != -1) {
								if(step_input[i] != null) {
									Input input = new Input();
									input.setStep_id(sub_id);
									input.setInput_id(inputMapper.getSeqId());
									input.setEach_id(step_each[i]);
									input.setInput(step_input[i]);
									
									inputList.add(input);
								}
							}
						}
						
					}
					
					if(inputList.size() > 0) {
						inputMapper.insertInput(inputList);
					}
				}
				
			}
		}
	}
	
	// 스탭 단위 리스트
	@Override
	public List<Each> selectMethodEach(int type) {
		
		return eachMapper.methodEachList(type);
	}

	private String parseRegistTypeToView(int USEE_AT_CODE) {
		switch (USEE_AT_CODE) {
		case 0:
			return "method/method_detail";
		case 1:
			return "method/method_easy_detail";
		case 2:
			return "method/method_template_detail";
		default:
			return "";
		}
	}

	private String parseMethodStatus(int method_status) {
		switch (method_status) {
		case 0:
			return "승인완료";
		case 1:
			return "수정승인완료";
		default:
			return "";
		}
	}

	public String codeCheck(String groupCode) {
		
		String code = mCode+"-"+groupCode;
		
		code = methodMapper.getCode(code);
		String fullCode = CodeUtil.getFullCode(code, mCode , groupCode);
		return fullCode;
	}

}
