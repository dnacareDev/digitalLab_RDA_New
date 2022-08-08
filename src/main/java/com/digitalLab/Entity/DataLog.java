package com.digitalLab.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Alias("DataLog")
public class DataLog {
	
	private int log_id;
	private String account;
	private int action_type;
	private String data_code;
	private String log_date;
	private String data_type;
	private int data_id;
	private String note_file;
	private String note_origin_file;
	private long note_file_size;
	private String note_file_type;
	
	private String action;
	
	public DataLog() {
		
	}
	
	public DataLog(int log_id ,String account, int data_id , int action_type , String data_code, String data_type) {
		this.log_id = log_id;
		this.account = account;
		this.data_id = data_id;
		this.action_type = action_type;
		this.data_code = data_code;
		this.data_type = data_type;
	}
	
	public DataLog(int log_id ,String account, int data_id , int action_type , String data_code, String data_type ,
				String note_file , String note_origin_file , long note_file_size , String note_file_type) {
		this.log_id = log_id;
		this.account = account;
		this.data_id = data_id;
		this.action_type = action_type;
		this.data_code = data_code;
		this.data_type = data_type;
		this.note_file = note_file;
		this.note_origin_file = note_origin_file;
		this.note_file_size = note_file_size;
		this.note_file_type = note_file_type;
	}
}
