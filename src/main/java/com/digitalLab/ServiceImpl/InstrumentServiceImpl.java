package com.digitalLab.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digitalLab.Entity.Instrument;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.Users;
import com.digitalLab.Mapper.InstrumentMapper;
import com.digitalLab.Mapper.UserMapper;
import com.digitalLab.Service.InstrumentService;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.CodeUtil;
import com.digitalLab.Util.DataLogUtil;
import com.digitalLab.Util.SessionUtil;

@Service
public class InstrumentServiceImpl implements InstrumentService {
	
	@Autowired
	private InstrumentMapper instrumentMapper;
	
	@Autowired
	private DataLogUtil logUtil;
	
	// 장비 일련번호
	private static final String instrumentCode = "ms";
	
	private static final String type = "instrument";
	
	public InstrumentServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Instrument> instrumentList() {
		return instrumentMapper.instrumentList();
	}

	@Override
	public int registInstrument(Instrument instrument) {
		
		Users user = AuthUtil.sessionUser();
		
		Instrument checkName = instrumentMapper.checkDuplicateName(instrument.getInstrument_name());

		if (checkName != null) {
			return -2;
		}
		
		int seq_id = instrumentMapper.getSeqId();
		String code = codeCheck(user.getGroupCode());
		
		instrument.setInstrument_code(code);
		instrument.setInstrument_id(seq_id);
		instrument.setAccount(user.getAccount());

		int result = instrumentMapper.registInstrument(instrument);

		if (result == 0) {
			// Err
			return -1;
		}
		
		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);
		
		return result;
	}
	
	// 기구 수정
	@Override
	public int modifyInstrument(Instrument instrument) {
		
		Users user = AuthUtil.sessionUser();
		
		if(instrument.getInstrument_id() != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		Instrument checkName = instrumentMapper.checkDuplicateName(instrument.getInstrument_name());
		
		if (checkName != null) {
			if(checkName.getInstrument_id() != instrument.getInstrument_id()) {
				return -2;
			}
		}
		
		int result = instrumentMapper.modifyInstrument(instrument);
		
		if (result == 0) {
			// Err
			return -1;
		}
		System.out.println(instrument);
		logUtil.addDataLog(user.getAccount(), instrument.getInstrument_id(), 2, instrument.getInstrument_code(), type);

		return result;
	}
	
	@Override
	public List<Instrument> searchInstrumentList(SearchParam searchParam) {
		
		Users user = AuthUtil.sessionUser();
		
		List<Instrument> list = instrumentMapper.selectInstrumentList(user);
		list.forEach(e -> {
			int status = e.getInstrument_status();
			e.setUse_type(parseUseStatus(status));
		});
		return list;
	}

	@Override
	public Map<String,Object> selectInstrumentbyId(int instrument_id) {
		Map<String,Object> result = new HashMap<>();
		
		SessionUtil.setPkId(instrument_id , type);
		
		Instrument instrument = instrumentMapper.selectInstrumentById(instrument_id);
		
		if(instrument == null){
			result.put("state", -1);
			return result;
		}
		
		result.put("logList" , logUtil.logList(instrument_id, type));
		result.put("instrument",instrument);
		result.put("state", 1);
		return result;
	}

	@Override
	public int instrumentDelete(int instrument_id) {
		
		if(instrument_id != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		return instrumentMapper.deleteInstrument(instrument_id);
	}

	private String codeCheck(String groupCode) {
		
		String code = instrumentCode+"-"+groupCode;
		code = instrumentMapper.getCode(code);
		String fullCode = CodeUtil.getFullCode(code, instrumentCode , groupCode);
		return fullCode;
	}

	private String parseUseStatus(int instrument_status){
		if(instrument_status == 1){
			return "사용";
		}
		return "미사용";
	}

}
