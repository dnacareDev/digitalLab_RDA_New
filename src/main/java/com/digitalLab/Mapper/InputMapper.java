package com.digitalLab.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalLab.Entity.Input;

@Mapper
public interface InputMapper {

	public int insertInput(List<Input> input);
	
	public List<Input> selectStepInput(int step_id);
	
	public int getSeqId();
}
