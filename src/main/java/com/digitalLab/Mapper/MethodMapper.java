
package com.digitalLab.Mapper;

import com.digitalLab.Entity.Method;
import java.util.List;

import com.digitalLab.Entity.MethodEasyParam;
import com.digitalLab.Entity.SearchParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MethodMapper {

	List<Method> methodList(String words);
	
	Method methodDetail(int method_id);
	
	public int registMethod(Method method);
	
	public int registMethodEasy(Method method);
	
	public int getSeqId();

	public String getCode(String group_code);

	public List<Method> selectMethodList(SearchParam param);
	
	public Method selectMethodByDivision(int division_id);
	
	public Method selectMethodById(int method_id);
	
	public int deleteMethod(int method_id);
	
	public int updateMethod(Method method);
	
	public int updateMethodEasy(Method method);

}
