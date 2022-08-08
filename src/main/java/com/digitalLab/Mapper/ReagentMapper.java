package com.digitalLab.Mapper;

import com.digitalLab.Entity.Reagent;

import java.util.List;

import com.digitalLab.Entity.ReagentEasyParam;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.Users;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReagentMapper {

	public List<Reagent> reagentList(String words);
	public List<Reagent> selectReagentList(Users user);
	
	public Reagent reagentDetail(int reagent_id);
	public Reagent selectReagentById(int reagent_id);

	public int registReagent(Reagent reagent);
	public int registReagentEasy(ReagentEasyParam reagent);

	public int reagentModify(Reagent reagent);
	public int reagentModifyEasy(Reagent reagent);
	public int reagentModifyTemplate(Reagent reagent);
	
	public Reagent checkDuplicateName(String reagent_name);
	
	public int getSeqId();
	
	public String getCode(String group_code);

    int deleteReagent(int reagent_id);
}
