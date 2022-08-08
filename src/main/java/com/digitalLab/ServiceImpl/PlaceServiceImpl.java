package com.digitalLab.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalLab.Entity.Place;
import com.digitalLab.Entity.Users;
import com.digitalLab.Mapper.PlaceMapper;
import com.digitalLab.Service.PlaceService;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.CodeUtil;
import com.digitalLab.Util.DataLogUtil;
import com.digitalLab.Util.SessionUtil;

@Service
public class PlaceServiceImpl implements PlaceService {
	
	// 일련 번호
	private static final String placeCode = "pl";
	
	// log type
	private static final String type = "place";

	@Autowired
	private PlaceMapper placeMapper;
	
	@Autowired
	private DataLogUtil logUtil;
	
	public PlaceServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	// 장소 리스트
	@Override
	public List<Place> place_list() {
		
		Users user = AuthUtil.sessionUser();
		
		return placeMapper.getPlaceList(user);
	}
	
	// 장소 상세보기
	@Override
	public Map<String , Object> placeDetail(int place_id) {
		
		Map<String,Object> result = new HashMap<>();
		
		SessionUtil.setPkId(place_id , type);
		
		Place place = placeMapper.getPlace(place_id);
		
		if(place == null){
			result.put("state", -1);
			return result;
		}
		
		result.put("logList" , logUtil.logList(place_id, type));
		result.put("place",place);
		result.put("state", 1);
		return result;
	}
	
	// 장소 등록
	@Override
	public int insertPlace(Place place) {
		
		Users user = AuthUtil.sessionUser();
		String code = codeCheck(user.getGroupCode());
		int seq_id = placeMapper.getSeqId();
		
		place.setPlace_code(code);
		place.setPlace_id(seq_id);
		place.setAccount(user.getAccount());
		
		int result = placeMapper.insertPlace(place);

		if (result == 0) {
			// Err
			return -1;
		}
		
		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);
		
		return result;
	}
	
	// 장소 수정
	@Override
	public int modifyPlace(Place place) {
		
		Users user = AuthUtil.sessionUser();
		
		if(place.getPlace_id() != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		int result = placeMapper.modifyPlace(place);

		if (result == 0) {
			// Err
			return -1;
		}
		
		logUtil.addDataLog(user.getAccount(), place.getPlace_id(), 2, place.getPlace_code(), type);
		
		return result;
	}
	
	private String codeCheck(String groupCode) {
		
		String code = placeCode+"-"+groupCode;
		
		code = placeMapper.getCode(code);
		String fullCode = CodeUtil.getFullCode(code, placeCode , groupCode);
		return fullCode;
	}

	@Override
	public int placeDelete(int place_id) {
		
		if(place_id != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		return placeMapper.deletePlace(place_id);
	}
}
