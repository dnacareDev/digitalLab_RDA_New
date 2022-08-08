package com.digitalLab.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalLab.Entity.Each;

@Mapper
public interface EachMapper {
	
	List<Each> reagentEachList();

	List<Each> seedEachList();
	
	List<Each> methodEachList(int type);
}