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
@Alias("Method")
public class Method
{
	private int method_id;
	private String account;
	private String method_code;
	private int method_type;
	private String method_contents;
	private String method_theory;
	private String method_calculation;
	private String method_reference;
	private int method_stage;
	private int method_result;
	private int method_status;
	private int method_share;
	private int USEE_AT_CODE;
	private String ATCH_FILE_NM;
	private String ORIG_FILE_NM;
	private String create_date;
	private String modify_date;
	private String template_head;
	private String template_body;
	
	private MultipartFile file;
	
	//divsion input param
	private int division_depth_id_1;
	private int division_depth_id_2;
	private int division_parents;
	private int division_id;

	private String division_1;
	private String division_2;
	private String division_3;
    private String division;
    
    private List<Step> stepList;
    private String method_protocol;

	private String user_name;
	private String step_index;
	private String step_type;
	private String step_data;
	
	// detail 
	private String regist_type;
	private String status_type;
	private int method_join_count;
	private int stepNo;

	//update
	private Boolean isFileUpdate;

}