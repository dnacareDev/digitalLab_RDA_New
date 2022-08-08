package com.digitalLab.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalLab.Entity.Instrument;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.Users;

@Mapper
public interface InstrumentMapper {
	
	public List<Instrument> instrumentList();
	public List<Instrument> selectInstrumentList(Users user);
	public Instrument selectInstrumentById(int instrument_id);
	
	public int registInstrument(Instrument instrument);
	
	public int modifyInstrument(Instrument instrument);
	
	public Instrument checkDuplicateName(String instrument_name);

	public int getSeqId();
	
	public String getCode(String group_code);

    int deleteInstrument(int instrument_id);
}
