package com.digitalLab.Service;

import com.digitalLab.Entity.Each;
import com.digitalLab.Entity.Reagent;
import com.digitalLab.Entity.ReagentEasyParam;
import com.digitalLab.Entity.SearchParam;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface ReagentService {
	
	public int registReagent(Reagent reagent);
	
	public int registReagentEasy(ReagentEasyParam reagent);

	public Map<String , Object> registReagentMany(Reagent reagent);

	public int registReagentTemplate(Reagent reagent);
	
	public List<Reagent> selectReagentList();
	
	public List<Each> reagentEachList();

	public Map<String,Object> selectReagentById(int reagent_id);
	
	public int reagentModify(Reagent reagent);

    int reagentDelete(int reagent_id);
}
