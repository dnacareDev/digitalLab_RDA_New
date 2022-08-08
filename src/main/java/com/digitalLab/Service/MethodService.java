package com.digitalLab.Service;

import com.digitalLab.Entity.Each;
import com.digitalLab.Entity.Method;
import com.digitalLab.Entity.MethodEasyParam;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.Step;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface MethodService {
	
	public int registMethod(Method method) throws JsonMappingException, JsonProcessingException;
	
	public int registMethodEasy(Method method);

	public List<Method> selectMethodList(SearchParam param);
	
	public int registMethodTemplate(Method method);

	public Map<String , Object> selectMethodByDivision(int division_id);
	
	public Map<String , Object>selectMethodById(int method_id);
	
	public List<Step> methodStepList(int method_id);
	
	public List<Each> selectMethodEach(int type);
	
	public int deleteMethod(int method_id);
	
	public int updateMethod(Method method);
	
}

