package com.digitalLab.Util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalLab.Entity.DataLog;
import com.digitalLab.Mapper.DataLogMapper;

@Service
public class DataLogUtil {
	public static final int ACTION_REGIST = 1;

	public static final int ACTION_MODIFY = 2;

	public static final int ACTION_DELETE = 3;

	public static final int ACTION_SET = 4;

	public static final int ACTION_ANALYSIS = 5;

	public static final int ACTION_RESULT = 6;

	public static final int ACTION_NOTE_SUCCESS = 7;
	
	public static final int ACTION_NOTE_FAIL = 8;
	
	public static final int ACTION_REPOSITORY_SUECCESS = 9;
	
	public static final int ACTION_REPOSITORY_FAIL = 10;
	
	@Autowired
	private DataLogMapper dataLogMapper;
	
	public int addDataLog(String user_id, int data_id , int action_type , String data_code, String data_type) {
		
		int seq_id = dataLogMapper.getSeqId();
		
		DataLog log = new DataLog(seq_id , user_id , data_id , action_type , data_code , data_type);
		
		return dataLogMapper.insertLogData(log);
	}
	
	public int addDataLog(String user_id, int data_id , int action_type , String data_code, String data_type,
			String note_file , String note_origin_file , long note_file_size , String note_file_type) {
		
		int seq_id = dataLogMapper.getSeqId();
		
		DataLog log = new DataLog(seq_id , user_id , data_id , action_type , data_code , data_type , note_file ,note_origin_file , note_file_size ,note_file_type);
		dataLogMapper.insertLogData(log);
		
		return seq_id;
	}
	
	public List<DataLog> logList(int data_id , String data_type){
		
		DataLog log = new DataLog();
		log.setData_id(data_id);
		log.setData_type(data_type);
		
		return dataLogMapper.selectLogList(log);
	}
}
