package com.digitalLab.Service;

import java.util.List;
import java.util.Map;

import com.digitalLab.Entity.Instrument;
import com.digitalLab.Entity.SearchParam;

public interface InstrumentService {
	
	public List<Instrument> instrumentList();
	
	public int registInstrument(Instrument instrument);
	
	public int modifyInstrument(Instrument instrument);
	
	public List<Instrument> searchInstrumentList(SearchParam searchParam);

	public Map<String,Object> selectInstrumentbyId(int instrument_id);

    int instrumentDelete(int instrument_id);
}
