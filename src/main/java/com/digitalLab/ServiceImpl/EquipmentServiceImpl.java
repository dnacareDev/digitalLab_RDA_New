package com.digitalLab.ServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import com.digitalLab.Entity.EquipmentEasyParam;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.Users;
import com.digitalLab.Service.FileService;
import com.digitalLab.Util.RegistUtil;
import com.digitalLab.Util.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalLab.Entity.Equipment;
import com.digitalLab.Mapper.EquipmentMapper;
import com.digitalLab.Service.EquipmentService;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.CodeUtil;
import com.digitalLab.Util.DataLogUtil;
import com.digitalLab.Util.JsonUtil;

@Service
public class EquipmentServiceImpl implements EquipmentService {

	// 장비 일련번호
	private static final String equipmentCode = "eq";
	// 업로드 경로
	private static final String type = "equipment";

	private static List<String> public_list;
	private static List<String> range_list;
	private static List<String> purpose_list;

	@Autowired
	private EquipmentMapper equipmentMapper;

	@Autowired
	private DataLogUtil logUtil;

	@Autowired
	private FileService fileService;

	public EquipmentServiceImpl() {
		initPublicList();
		initRangeList();
		initPurposeList();
	}

	private void initPublicList() {
		public_list = new ArrayList<>();
		public_list.add("기관내부활용");
		public_list.add("기관외부활용");
	}

	private void initRangeList() {
		range_list = new ArrayList<>();
		range_list.add("공동활용허용가능");
		range_list.add("단독활용만가능");
	}

	private void initPurposeList() {
		purpose_list = new ArrayList<>();
		purpose_list.add("계측");
		purpose_list.add("시험");
		purpose_list.add("분석");
		purpose_list.add("기타");
	}

	// 장비 간편 등록
	@Override
	public int registEquipmentEasy(EquipmentEasyParam equipment) {

		Users user = AuthUtil.sessionUser();

		int result = -1;

//		Equipment checkName = equipmentMapper.checkDuplicateName(equipment.getEquipment_name());
//
//		if (checkName != null) {
//			return -2;
//		}
		
		int seq_id = equipmentMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());

		if (equipment.getFile() == null) {
			return -23;
		}else if (fileService.extensionCheck(equipment.getFile())) {
			return -24;
		}
		
		String fileName = "";
		try {
			fileName = fileService.uploadAndGetFilePath(equipment.getFile(), type);
		} catch (IOException e) {
			System.out.println("IOException");
			return -3;
		}
		
		// upload File
		equipment.setATCH_FILE_NM(fileName);
		equipment.setORIG_FILE_NM(equipment.getFile().getOriginalFilename());
		equipment.setEquipment_code(code);
		equipment.setEquipment_id(seq_id);
		equipment.setUSEE_AT_CODE(1);
		equipment.setAccount(user.getAccount());

		result = equipmentMapper.registEquipmentEasy(equipment);

		if (result == 0) {
			// Err
			return -1;
		}
		
		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);
		
		return result;
	}

	// 장비 등록
	@Override
	public int registEquipment(Equipment equipment) {

		Users user = AuthUtil.sessionUser();

//		Equipment checkName = equipmentMapper.checkDuplicateName(equipment.getEquipment_name());
//
//		if (checkName != null) {
//
//			return -2;
//		}

		int seq_id = equipmentMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());

		equipment.setEquipment_code(code);
		equipment.setEquipment_id(seq_id);
		equipment.setAccount(user.getAccount());

		int result = equipmentMapper.registEquipment(equipment);

		if (result == 0) {
			// Err
			return -1;
		}

		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);

		return result;
	}

	// 장비 대량 등록
	@Override
	public Map<String , Object> registEquipmentMany(Equipment equipment) {

		Users user = AuthUtil.sessionUser();
		
		Map<String , Object> map = new HashMap<String , Object>();
		List<Map<String, Object>> errorList = new ArrayList<>();
		int index = 0;
		
		for (Equipment equip : equipment.getEquipmentList()) {
			
			// 해당 라인의 인덱스 번호
			index = index+1;
			
			Equipment checkName = equipmentMapper.checkDuplicateName(equip.getEquipment_name());
			
			if (checkName != null) {
				Map<String, Object> checkMap = new HashMap<>();
				checkMap.put("index", index);
				checkMap.put("equipment", equip);
				checkMap.put("errCode", -2);
				errorList.add(checkMap);
				
				continue;
			}
			
			int seq_id = equipmentMapper.getSeqId();
			String code = codeCheck(user.getGroupCode());

			equip.setEquipment_code(code);
			equip.setEquipment_id(seq_id);
			equip.setAccount(user.getAccount());
			parseEquipmenFromMany(equip);
			
			int result = equipmentMapper.registEquipment(equip);

			if (result == 0) {
				Map<String, Object> checkMap = new HashMap<>();
				checkMap.put("index", index);
				checkMap.put("equipment", equip);
				checkMap.put("errCode", -1);
				errorList.add(checkMap);
				
				continue;
			}
			
			logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);
		}
		map.put("result" ,errorList);
		
		return map;
	}

	// 장비 템플릿 등록
	@Override
	public int registEquipmentTemplate(Equipment equipment) {

		Users user = AuthUtil.sessionUser();

		Equipment checkName = equipmentMapper.checkDuplicateName(equipment.getEquipment_name());

		if (checkName != null) {

			return -2;
		}

		int seq_id = equipmentMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());
		
		equipment.setEquipment_code(code);
		equipment.setEquipment_id(seq_id);
		equipment.setAccount(user.getAccount());
		equipment.setUSEE_AT_CODE(2);

		int result = equipmentMapper.registEquipment(equipment);

		if (result == 0) {
			return -1;
		}
		
		try {
			JsonUtil.createJson(code, equipment.getTemplate_head(), equipment.getTemplate_body());
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");		
		}
		
		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);
		
		return result;
	}

	@Override
	public int equipmentModify(Equipment equipment) {

		Users user = AuthUtil.sessionUser();
		
		int checkSeq_id = SessionUtil.getPkId(type);
		
		if(equipment.getEquipment_id() != checkSeq_id) {
			return -20;
		}
		
		// 수정 중
		int authCheck = equipmentMapper.checkAuth(user, checkSeq_id);
		
		System.out.println("authCheck : "+authCheck);
		
		if(authCheck <= 0) {
			return -20;
		}

		Equipment checkName = equipmentMapper.checkDuplicateName(equipment.getEquipment_name());

		if (checkName != null) {

			if (checkName.getEquipment_id() != equipment.getEquipment_id()) {
				return -2;
			}
		}

		int result = 0;

		// 일반
		if (equipment.getUSEE_AT_CODE() == 0) {
			result = equipmentMapper.equipmentModify(equipment);

			// 간편
		} else if (equipment.getUSEE_AT_CODE() == 1) {

			String fileName = "";
			if (equipment.getIsFileUpdate() != null && equipment.getIsFileUpdate() && equipment.getFile() != null) {
				
				if (fileService.extensionCheck(equipment.getFile())) {
					return -24;
				}
				
				try {
					fileName = fileService.uploadAndGetFilePath(equipment.getFile(), type);
				} catch (IOException e) {
					System.out.println("IOException");
					return -3;
				}
				
				equipment.setATCH_FILE_NM(fileName);
				equipment.setORIG_FILE_NM(equipment.getFile().getOriginalFilename());
			}

			result = equipmentMapper.equipmentModifyEasy(equipment);

			// 템플릿
		} else if (equipment.getUSEE_AT_CODE() == 2) {
			
			try {
				JsonUtil.createJson(equipment.getEquipment_code(), equipment.getTemplate_head(), equipment.getTemplate_body());
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException");
			}
			
			result = equipmentMapper.equipmentModify(equipment);
		}

		if (result == 0) {
			// Err
			return -1;
		}
		
		logUtil.addDataLog(user.getAccount(), equipment.getEquipment_id(), 2, equipment.getEquipment_code(), type);

		return result;
	}

	@Override
	public int equipmentDelete(int equipment_id) {
		
		if(equipment_id != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		return equipmentMapper.deleteEquipment(equipment_id);
	}

	// 장비 검색
	@Override
	public List<Equipment> searchEquipment(SearchParam search) {
		
		Users user = AuthUtil.sessionUser();
		
		search.setAuthority(user.getAuthority());
		search.setAccount(user.getAccount());
		search.setUser_code(user.getGroupCode());
		
		List<Equipment> equipmentList = equipmentMapper.searchEquipment(search);
		equipmentList.forEach(e -> {
			String regist_type = RegistUtil.parseRegistType(e.getUSEE_AT_CODE());
			e.setRegist_type(regist_type);
			parseEquipmentData(e);
		});

		return equipmentList;
	}

	public Map<String, Object> selectEquipmentById(int equipment_id) {
		Map<String, Object> map = new HashMap<>();
		Equipment equipment = equipmentMapper.selectEquipmentById(equipment_id);
		SessionUtil.setPkId(equipment_id , type);

		int state = 1;
		if (equipment == null) {
			map.put("state", -1);
			return map;
		}

		String viewName = parseRegistTypeToView(equipment.getUSEE_AT_CODE());
		if (viewName.isEmpty()) {
			map.put("state", -1);
			return map;
		}
		
		if(equipment.getUSEE_AT_CODE() == 2) {
			try {
				Map<String , Object> teamplate = JsonUtil.readJson(equipment.getEquipment_code());
				equipment.setTemplate_head((String)teamplate.get("head"));
				equipment.setTemplate_body((String)teamplate.get("body"));
				
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException");
			}
			
		}

		map.put("logList", logUtil.logList(equipment_id, type));
		map.put("viewName", viewName);
		map.put("state", state);
		map.put("equipment", equipment);
		return map;
	}

	private String codeCheck(String group_code) {

		String code = equipmentCode + "-" + group_code;
		code = equipmentMapper.getCode(code);
		String fullCode = CodeUtil.getFullCode(code, equipmentCode, group_code);
		return fullCode;
	}

	private String parseRegistTypeToView(int USEE_AT_CODE) {
		switch (USEE_AT_CODE) {
		case 0:
			return "equipment_information/equipment_detail";
		case 1:
			return "equipment_information/equipment_easy_detail";
		case 2:
			return "equipment_information/equipment_template_detail";
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

	private void parseRange(Equipment equipment) {
		
		if(equipment.getEquipment_range() != null) {
			for (int i = 0; i < range_list.size(); i++) {
				
				if (stringGapRemove(equipment.getEquipment_range()).equals(range_list.get(i))) {
					equipment.setEquipment_range(Integer.toString(i+1));
					
					return;
				}
			}
			
			equipment.setEquipment_range(null);
		} 
	}

	private void parsePublic(Equipment equipment) {
		
		if(equipment.getEquipment_public() != null) {
			for (int i = 0; i < public_list.size(); i++) {
				if (stringGapRemove(equipment.getEquipment_public()).equals(public_list.get(i))) {
					equipment.setEquipment_public(Integer.toString(i+1));
					
					return;
				}
			}
			
			equipment.setEquipment_public(null);
		}
	}

	private void parsePurpose(Equipment equipment) {
		
		if(equipment.getEquipment_purpose() != null) {
			for (int i = 0; i < purpose_list.size(); i++) {
				if (stringGapRemove(equipment.getEquipment_purpose()).equals(purpose_list.get(i))) {
					equipment.setEquipment_purpose(Integer.toString(i+1));
					
					return;
				}
			}
			
			equipment.setEquipment_purpose(null);
		}
	}

	private void parseEquipmenFromMany(Equipment equipment) {
		parsePublic(equipment);
		parseRange(equipment);
		parsePurpose(equipment);
	}

	private void parseEquipmentData(Equipment equipment) {
		parseEquipmentPublic(equipment);
		parseEquipmentRange(equipment);
		parseEquipmentPurpose(equipment);
	}

	private void parseEquipmentPublic(Equipment equipment) {
		if (equipment.getEquipment_public() == null)
			return;
		if (!equipment.getEquipment_public().chars().allMatch(Character::isDigit)) {
			return;
		}
		int equipment_public = Integer.parseInt(equipment.getEquipment_public());
		// System.out.println(equipment_public);
		if (equipment_public < public_list.size()) {
			String publicData = public_list.get(equipment_public);
			equipment.setEquipment_public(publicData);
		}
	}

	private void parseEquipmentRange(Equipment equipment) {
		if (equipment.getEquipment_range() == null)
			return;
		if (!equipment.getEquipment_range().chars().allMatch(Character::isDigit)) {
			return;
		}

		int range = Integer.parseInt(equipment.getEquipment_range());
		// System.out.println(range);
		if (range < range_list.size()) {
			String equipment_range = range_list.get(range);
			equipment.setEquipment_range(equipment_range);
		}
	}

	private void parseEquipmentPurpose(Equipment equipment) {
		if (equipment.getEquipment_purpose() == null)
			return;
		if (!equipment.getEquipment_purpose().chars().allMatch(Character::isDigit)) {
			return;
		}

		int purpose = Integer.parseInt(equipment.getEquipment_purpose());
		// System.out.println(purpose);
		if (purpose < purpose_list.size()) {
			String equipment_purpose = purpose_list.get(purpose);
			equipment.setEquipment_purpose(equipment_purpose);
		}
	}

}
