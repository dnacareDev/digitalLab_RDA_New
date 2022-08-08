package com.digitalLab.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalLab.Entity.DataLog;
import com.digitalLab.Entity.Users;

@Mapper
public interface DataLogMapper {
	
	public List<DataLog> selectLogList(DataLog dataLog);
	
	public int insertLogData(DataLog dataLog);
	
	public int getSeqId();
	
	public List<DataLog> selectDataLogList(Users user);
	
	public DataLog selectDataLog(int log_id);
}
