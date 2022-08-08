package com.digitalLab.Entity;

import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Alias("Equipment")
public class Equipment {
	
	private int equipment_id;
	private String account;
	private String equipment_code;
	private String equipment_name;
	private String equipment_model;
	private int equipment_price;
	private String equipment_range;
	private String equipment_public;
	private String equipment_purpose;
	private String equipment_place;
	private String equipment_regist;
	private String equipment_manage;
	private int equipment_method;
	private int equipment_status;
	private int USEE_AT_CODE;
	private String ATCH_FILE_NM;
	private String ORIG_FILE_NM;
	private String equipment_date;
	private String create_date;
	private String modify_date;
	
	// 추가 
	private List<Equipment> equipmentList;
	private String template_head;
	private String template_body;
	
	//등록 방법 변환
	private String regist_type;
	
	private MultipartFile file;
	
	public Equipment(){};

	//view
	private int equipment_join_count;

	//update
	private Boolean isFileUpdate;
}
