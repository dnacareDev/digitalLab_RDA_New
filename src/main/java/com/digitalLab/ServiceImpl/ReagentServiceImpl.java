package com.digitalLab.ServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import com.digitalLab.Entity.ReagentEasyParam;
import com.digitalLab.Entity.Users;
import com.digitalLab.Service.FileService;
import com.digitalLab.Util.CodeUtil;
import com.digitalLab.Util.DataLogUtil;
import com.digitalLab.Util.JsonUtil;
import com.digitalLab.Util.RegistUtil;
import com.digitalLab.Util.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalLab.Entity.Each;
import com.digitalLab.Entity.Reagent;
import com.digitalLab.Mapper.EachMapper;
import com.digitalLab.Mapper.ReagentMapper;
import com.digitalLab.Service.ReagentService;
import com.digitalLab.Util.AuthUtil;

@Service
public class ReagentServiceImpl implements ReagentService {

	@Autowired
	private ReagentMapper reagentMapper;
	
	@Autowired
	private EachMapper eachMapper;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private DataLogUtil logUtil;
	
	private static final String rCode = "re";

	private static final String type = "reagent";
	
	// 시약 일반 등록
	@Override
	public int registReagent(Reagent reagent) {

		Users user = AuthUtil.sessionUser();
		
		Reagent checkName = reagentMapper.checkDuplicateName(reagent.getReagent_name());

		if (checkName != null) {
			return -2;
		}
		
		int seq_id = reagentMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());

		reagent.setReagent_code(code);
		reagent.setReagent_id(seq_id);
		reagent.setAccount(user.getAccount());

		System.out.println("check : " + reagent);

		int result = reagentMapper.registReagent(reagent);
		if (result == 0) {
			// Err
			return -1;
		}

		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);
		
		return result;
	}

	// 시약 간편 등록
	@Override
	public int registReagentEasy(ReagentEasyParam reagent) {
		
		Users user = AuthUtil.sessionUser();
		
		int result = -1;
		
		Reagent checkName = reagentMapper.checkDuplicateName(reagent.getReagent_name());

		if (checkName != null) {
			return -2;
		}
		
		int seq_id = reagentMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());
		
		reagent.setReagent_code(code);
		reagent.setReagent_id(seq_id);

		// upload File
		if (reagent.getFile() == null) {
			return -23;
		} else if (fileService.extensionCheck(reagent.getFile())) {
			return -24;
		}
		
		
		String fileName = "";
		try {
			fileName = fileService.uploadAndGetFilePath(reagent.getFile(), type);
		} catch (IOException e) {
			System.out.println("IOException");
			return -3;
		}

		reagent.setATCH_FILE_NM(fileName);
		reagent.setORIG_FILE_NM(reagent.getFile().getOriginalFilename());
		reagent.setUSEE_AT_CODE(1);
		reagent.setAccount(user.getAccount());

		result = reagentMapper.registReagentEasy(reagent);
		if (result == 0) {
			// Err
			return -1;
		}
		
		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);

		
		return result;
	}

	// 시약 대량 등록
	@Override
	public Map<String , Object> registReagentMany(Reagent reagent) {
		
		Users user = AuthUtil.sessionUser();
		
		Map<String , Object> map = new HashMap<String , Object>();
		List<Map<String,Object>> errorList = new ArrayList<>();
		int index = 0;
		
		for (Reagent rgent : reagent.getReagentList()) {
			
			// 해당 라인의 인덱스 번호
			index = index+1;
			
			Reagent checkName = reagentMapper.checkDuplicateName(rgent.getReagent_name());
			
			if (checkName != null) {
				Map<String,Object> checkMap = new HashMap<>();
				checkMap.put("index", index);
				checkMap.put("reagent", rgent);
				checkMap.put("errCode",-2);
				errorList.add(checkMap);
				
				continue;
			}
			
			int seq_id = reagentMapper.getSeqId();
			String code = codeCheck(user.getGroupCode());

			rgent.setReagent_code(code);
			rgent.setReagent_id(seq_id);
			rgent.setAccount(user.getAccount());

			int result = reagentMapper.registReagent(rgent);
			
			if (result == 0) {
				Map<String,Object> checkMap = new HashMap<>();
				checkMap.put("index", index);
				checkMap.put("reagent", rgent);
				checkMap.put("errCode",-1);
				errorList.add(checkMap);
				
				continue;
			}
			
			logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);
		}
		map.put("result" ,errorList);

		return map;
	}

	// 시약 템플릿 등록
	@Override
	public int registReagentTemplate(Reagent reagent) {

		Users user = AuthUtil.sessionUser();
		Reagent checkName = reagentMapper.checkDuplicateName(reagent.getReagent_name());
		
		if (checkName != null) {
			return -2;
		}
		
		int seq_id = reagentMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());
		
		reagent.setReagent_code(code);
		reagent.setReagent_id(seq_id);
		reagent.setAccount(user.getAccount());
		reagent.setUSEE_AT_CODE(2);
		
		try {
			JsonUtil.createJson(code, reagent.getTemplate_head(), reagent.getTemplate_body());
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			
			return -1;
		}
		
		int result = reagentMapper.registReagent(reagent);

		if (result == 0) {
			// Err
			return -1;
		}
		
		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);
		
		return result;
	}
	
	// 시약 수정
	@Override
	public int reagentModify(Reagent reagent) {
		
		Users user = AuthUtil.sessionUser();
		
		if(reagent.getReagent_id() != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		Reagent checkName = reagentMapper.checkDuplicateName(reagent.getReagent_name());
		
		if (checkName != null) {
			if(checkName.getReagent_id() != reagent.getReagent_id()) {
				return -2;
			}
		}
		
		int result = 0;
		
		// 일반
		if(reagent.getUSEE_AT_CODE() == 0) {
			result = reagentMapper.reagentModify(reagent);
			
		// 간편
		}else if(reagent.getUSEE_AT_CODE() == 1) {
			
			String fileName = "";

			if (reagent.getIsFileUpdate() != null && reagent.getIsFileUpdate() && reagent.getFile() != null) {
				
				if(fileService.extensionCheck(reagent.getFile())) {
					return -24;
				}
				
				try {
					fileName = fileService.uploadAndGetFilePath(reagent.getFile(), type);
				} catch (IOException e) {
					System.out.println("IOException");
					return -3;
				}

				reagent.setATCH_FILE_NM(fileName);
				reagent.setORIG_FILE_NM(reagent.getFile().getOriginalFilename());
			}
			
			result = reagentMapper.reagentModifyEasy(reagent);
		
		// 템플릿
		}else if(reagent.getUSEE_AT_CODE() == 2) {
			
			try {
				JsonUtil.createJson(reagent.getReagent_code(), reagent.getTemplate_head(), reagent.getTemplate_body());
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException");
			}
			
			result = reagentMapper.reagentModify(reagent);
		}

		if (result == 0) {
			// Err
			return -1;
		}
		
		logUtil.addDataLog(user.getAccount(), reagent.getReagent_id(), 2, reagent.getReagent_code(), type);

		return result;
	}

	@Override
	public int reagentDelete(int reagent_id) {
		
		if(reagent_id != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		return reagentMapper.deleteReagent(reagent_id);
	}

	// 시약 단위
	@Override
	public List<Each> reagentEachList() {
		return eachMapper.reagentEachList();
	}

	@Override
	public Map<String, Object> selectReagentById(int reagent_id) {
		Map<String, Object> map = new HashMap<>();
		
		int state = 1;
		
		Reagent reagent = reagentMapper.selectReagentById(reagent_id);
		SessionUtil.setPkId(reagent_id , type);
		
		// 템플릿 
		if(reagent.getUSEE_AT_CODE() == 2) {
			try {
				Map<String , Object> template = JsonUtil.readJson(reagent.getReagent_code());
				reagent.setTemplate_head((String)template.get("head"));
				reagent.setTemplate_body((String)template.get("body"));
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException");
			}
		}
		
		if(reagent == null){
			map.put("state",-1);
			return map;
		}
		
		String viewName = parseRegistTypeToView(reagent.getUSEE_AT_CODE());
		
		if(viewName.isEmpty()){
			map.put("state",-1);
			return map;
		}
		
		map.put("logList" , logUtil.logList(reagent_id, type));
		map.put("viewName",viewName);
		map.put("reagent",reagent);
		map.put("state", state);
		return map;
	}
	
	// 코드 체크
	private String codeCheck(String groupCode) {
		
		String code = rCode+"-"+groupCode;
		
		code = reagentMapper.getCode(code);
		String fullCode = CodeUtil.getFullCode(code, rCode , groupCode);
		return fullCode;
	}

	@Override
	public List<Reagent> selectReagentList() {
		
		Users user = AuthUtil.sessionUser();
		
		List<Reagent> list = reagentMapper.selectReagentList(user);

		list.forEach(e -> {
			String regist_type = RegistUtil.parseRegistType(e.getUSEE_AT_CODE());
			e.setRegist_type(regist_type);

			e.setReagent_use(e.getReagent_join_count() == 1 ? "N" : "Y");
		});
		return list;
	}

	private String parseRegistTypeToView(int USEE_AT_CODE){
		switch (USEE_AT_CODE){
			case 0: return "reagent_information/reagent_detail";
			case 1: return "reagent_information/reagent_easy_detail";
			case 2: return "reagent_information/reagent_template_detail";
			default: return "";
		}
	}

}
